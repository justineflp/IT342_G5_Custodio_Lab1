package com.it342.custodio.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.it342.custodio.auth.api.ErrorParser
import com.it342.custodio.auth.api.RetrofitClient
import com.it342.custodio.auth.databinding.ActivityDashboardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Protected screen: only reachable when logged in.
 * If token is missing or invalid, redirect to Login.
 */
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        session = SessionManager(applicationContext)

        if (!session.isLoggedIn) {
            goToLogin()
            return
        }

        binding.btnLogout.setOnClickListener { doLogout() }
        binding.loadingMessage.visibility = View.VISIBLE
        binding.profileBlock.visibility = View.GONE
        binding.note.visibility = View.GONE
        loadProfile()
    }

    private fun loadProfile() {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.createAuthApi(session)
                val res = api.getMe()
                withContext(Dispatchers.Main) {
                    binding.loadingMessage.visibility = View.GONE
                    if (res.isSuccessful) {
                        val user = res.body()!!
                        binding.id.text = user.id.toString()
                        binding.fullName.text = user.fullName
                        binding.email.text = user.email
                        binding.profileBlock.visibility = View.VISIBLE
                        binding.note.visibility = View.VISIBLE
                    } else {
                        if (res.code() == 401) {
                            session.clear()
                            goToLogin()
                        } else {
                            binding.errorMessage.text = ErrorParser.parseError(res)
                            binding.errorMessage.visibility = View.VISIBLE
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.loadingMessage.visibility = View.GONE
                    binding.errorMessage.text = "Network error: ${e.message}"
                    binding.errorMessage.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun doLogout() {
        lifecycleScope.launch {
            try {
                val api = RetrofitClient.createAuthApi(session)
                api.logout()
            } catch (_: Exception) { /* best effort */ }
            withContext(Dispatchers.Main) {
                session.clear()
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }
}
