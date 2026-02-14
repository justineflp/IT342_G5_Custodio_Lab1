package com.it342.custodio.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.it342.custodio.auth.api.ErrorParser
import com.it342.custodio.auth.api.RetrofitClient
import com.it342.custodio.auth.api.model.RegisterRequest
import com.it342.custodio.auth.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(applicationContext)

        binding.btnRegister.setOnClickListener { doRegister() }
        binding.linkLogin.setOnClickListener {
            finish()
        }
    }

    private fun doRegister() {
        val email = binding.email.text.toString().trim()
        val fullName = binding.fullName.text.toString().trim()
        val password = binding.password.text.toString()
        if (email.isBlank() || fullName.isBlank() || password.isBlank()) {
            binding.errorMessage.text = "All fields are required"
            binding.errorMessage.visibility = View.VISIBLE
            return
        }
        if (password.length < 6) {
            binding.errorMessage.text = "Password must be at least 6 characters"
            binding.errorMessage.visibility = View.VISIBLE
            return
        }
        binding.errorMessage.visibility = View.GONE
        binding.btnRegister.isEnabled = false
        binding.btnRegister.text = getString(R.string.creating_account)
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.createAuthApi(session)
                val res = api.register(RegisterRequest(email, fullName, password))
                withContext(Dispatchers.Main) {
                    binding.btnRegister.isEnabled = true
                    binding.btnRegister.text = getString(R.string.register)
                    if (res.isSuccessful) {
                        val body = res.body()!!
                        session.token = body.token
                        startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java).apply {
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
                    binding.btnRegister.isEnabled = true
                    binding.btnRegister.text = getString(R.string.register)
                    binding.errorMessage.text = "Network error: ${e.message}"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
    }
}
