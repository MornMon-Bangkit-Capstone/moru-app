package com.capstone.moru.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityRegisterBinding
import com.capstone.moru.ui.auth.login.LoginActivity
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.fill.FillProfileActivity

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(p0!!).matches() && p0.isEmpty()) {
                    binding.edEmail.error = getString(R.string.wrong_email_format)
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.btnRegister.isEnabled = true
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
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.btnRegister.isEnabled = true
                }
            }
        })

        binding.btnRegister.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPass.text.toString()
            val passwordConfirm = binding.edPassConfirm.text.toString()

            if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                val msg = getString(R.string.fill_field)
                displayToast(msg)
            } else {
                registerViewModel.userRegister(email, password, passwordConfirm)
                registerViewModel.error.observe(this) { error ->
                    if (!error) {
                        registerViewModel.user.observe(this) { user ->
                            if (user == "User created") {
                                val msg = getString(R.string.register_success)
                                displayToast(msg)

                                val intentLogin =
                                    Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intentLogin)
                                finish()
                            }
                        }
                    } else {
                        registerViewModel.message.observe(this) { message ->
                            val msg = getString(R.string.register_failed)
                            displayToast(msg)
                        }
                    }
                }
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.tvLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}