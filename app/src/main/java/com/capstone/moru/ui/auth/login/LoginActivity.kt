package com.capstone.moru.ui.auth.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityLoginBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.auth.register.RegisterActivity
import com.capstone.moru.ui.factory.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(p0!!).matches()) {
                    binding.edEmail.error = getString(R.string.wrong_email_format)
                    binding.btnLogin.isEnabled = false
                } else {
                    binding.btnLogin.isEnabled = true

                }
            }

        })

        binding.edPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length < 8) {
                    binding.edPass.error = getString(R.string.wrong_password_format)
                    binding.btnLogin.isEnabled = false
                } else {
                    binding.btnLogin.isEnabled = true
                }
            }
        })


        binding.tvRegister.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.fill_field)
                displayToast(msg)
            } else {
                loginViewModel.userLogin(email, password)
                loginViewModel.error.observe(this) { error ->
                    if (!error) {
                        loginViewModel.user.observe(this) { user ->
                            if (!user?.token.isNullOrEmpty()) {
                                val intentToMain = Intent(this, MainActivity::class.java)
                                startActivity(intentToMain)
                                loginViewModel.saveUserFillProfileStatus(2)
                                finish()
                            }
                        }
                    } else {
                        loginViewModel.message.observe(this) { message ->
                            val msg = getString(R.string.wrong_credential)
                            displayToast(msg)
                        }
                    }
                }
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.backButton.setOnClickListener {
            finish()
        }


    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}