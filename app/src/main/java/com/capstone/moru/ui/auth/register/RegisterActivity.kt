package com.capstone.moru.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityRegisterBinding
import com.capstone.moru.ui.auth.login.LoginActivity
import com.capstone.moru.ui.fill.FillProfileActivity

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            val name = binding.edPassConfirm
        }

        binding.tvLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }

        binding.btnRegister.setOnClickListener {
            val intentToFillProfile = Intent(this, FillProfileActivity::class.java)
            startActivity(intentToFillProfile)
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}