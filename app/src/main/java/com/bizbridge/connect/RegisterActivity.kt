package com.bizbridge.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvUploadImage: TextView

    private val imagePicker = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        if (uri != null) {
            selectedImageUri = uri

            ivProfileImage.setPadding(0, 0, 0, 0)
            ivProfileImage.setImageURI(uri)
            ivProfileImage.scaleType = ImageView.ScaleType.CENTER_CROP

            tvUploadImage.text = "Profile image selected ✓"
            showMessage("Profile image selected successfully")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvUploadImage = findViewById(R.id.tvUploadImage)

        // Open gallery when the profile image is clicked
        ivProfileImage.setOnClickListener {
            openImagePicker()
        }

        tvUploadImage.setOnClickListener {
            openImagePicker()
        }

        // Back button
        findViewById<TextView>(R.id.tvBack).setOnClickListener {
            finish()
        }

        // Navigate back to Login screen
        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener {
            finish()
        }

        // Register button
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
                findViewById<RadioGroup>(
                    R.id.rgAccountType
                ).checkedRadioButtonId
            ) {
                R.id.rbBusinessOwner -> "BUSINESS_OWNER"
                else -> "CUSTOMER"
            }

            when {
                name.isBlank() -> {
                    showMessage("Enter your full name")
                }

                email.isBlank() ||
                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email).matches() -> {
                    showMessage("Enter a valid email address")
                }

                phone.isBlank() -> {
                    showMessage("Enter your phone number")
                }

                password.length < 6 -> {
                    showMessage(
                        "Password must have at least 6 characters"
                    )
                }

                password != confirmPassword -> {
                    showMessage("Passwords do not match")
                }

                !termsAccepted -> {
                    showMessage("Please accept the terms")
                }

                else -> {
                    showMessage(
                        "$accountType details validated. " +
                                "Firebase registration is next."
                    )
                }
            }
        }
    }

    private fun openImagePicker() {
        imagePicker.launch("image/*")
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
