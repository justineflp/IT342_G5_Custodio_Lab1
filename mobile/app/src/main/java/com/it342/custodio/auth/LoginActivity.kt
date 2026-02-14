package com.it342.custodio.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.it342.custodio.auth.api.ErrorParser
import com.it342.custodio.auth.api.RetrofitClient
import com.it342.custodio.auth.api.model.LoginRequest
import com.it342.custodio.auth.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(applicationContext)

        binding.btnLogin.setOnClickListener { doLogin() }
        binding.linkRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun doLogin() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString()
        if (email.isBlank() || password.isBlank()) {
            binding.errorMessage.text = "Email and password are required"
            binding.errorMessage.visibility = View.VISIBLE
            return
        }
        binding.errorMessage.visibility = View.GONE
        binding.btnLogin.isEnabled = false
        binding.btnLogin.text = getString(R.string.signing_in)
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.createAuthApi(session)
                val res = api.login(LoginRequest(email, password))
                withContext(Dispatchers.Main) {
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = getString(R.string.login)
                    if (res.isSuccessful) {
                        val body = res.body()!!
                        session.token = body.token
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                        finish()
                    } else {
                        binding.errorMessage.text = ErrorParser.parseError(res)
                        binding.errorMessage.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.btnLogin.isEnabled = true
                    binding.btnLogin.text = getString(R.string.login)
                    binding.errorMessage.text = "Network error: ${e.message}"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
    }
}
