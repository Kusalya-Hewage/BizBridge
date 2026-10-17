package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.localcommunity.AdminDashboardActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText

    private lateinit var btnLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        tilEmail = findViewById(R.id.tilEmail)
        tilPassword = findViewById(R.id.tilPassword)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            loginAdmin()
        }
    }

    private fun loginAdmin() {

        val email = etEmail.text?.toString()?.trim().orEmpty()
        val password = etPassword.text?.toString().orEmpty()

        // Clear previous errors
        tilEmail.error = null
        tilPassword.error = null

        // Validate email
        if (email.isEmpty()) {
            tilEmail.error = "Enter your email or username"
            etEmail.requestFocus()
            return
        }

        // Validate password
        if (password.isEmpty()) {
            tilPassword.error = "Enter your password"
            etPassword.requestFocus()
            return
        }

        // Demo credentials
        val correctEmail = "admin@bizbridge.lk"
        val correctPassword = "Admin@123"

        if (
            email.equals(correctEmail, ignoreCase = true) &&
            password == correctPassword
        ) {
            Toast.makeText(
                this,
                "Admin login successful!",
                Toast.LENGTH_SHORT
            ).show()

            // Navigate to Admin Dashboard
            val intent = Intent(
                this,
                AdminDashboardActivity::class.java
            )

            startActivity(intent)
            finish()

        } else {
            tilPassword.error = "Invalid email or password"

            Toast.makeText(
                this,
                "Login failed. Check your credentials.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
