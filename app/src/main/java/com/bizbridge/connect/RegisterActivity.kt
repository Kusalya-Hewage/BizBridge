package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            finish()
        }

        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            val name = findViewById<EditText>(
                R.id.etFullName
            ).text.toString().trim()

            val email = findViewById<EditText>(
                R.id.etRegisterEmail
            ).text.toString().trim()

            val phone = findViewById<EditText>(
                R.id.etPhone
            ).text.toString().trim()

            val password = findViewById<EditText>(
                R.id.etRegisterPassword
            ).text.toString()

            val confirmPassword = findViewById<EditText>(
                R.id.etConfirmPassword
            ).text.toString()

            val termsAccepted = findViewById<CheckBox>(
                R.id.cbTerms
            ).isChecked

            val accountType = when (
                findViewById<RadioGroup>(R.id.rgAccountType)
                    .checkedRadioButtonId
            ) {
                R.id.rbBusinessOwner -> "BUSINESS_OWNER"
                else -> "CUSTOMER"
            }

            when {
                name.isBlank() ->
                    showMessage("Enter your full name")

                email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(email).matches() ->
                    showMessage("Enter a valid email address")

                phone.isBlank() ->
                    showMessage("Enter your phone number")

                password.length < 6 ->
                    showMessage("Password must have at least 6 characters")

                password != confirmPassword ->
                    showMessage("Passwords do not match")

                !termsAccepted ->
                    showMessage("Please accept the terms")

                else -> {
                    showMessage(
                        "$accountType details validated. Firebase registration is next."
                    )
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
