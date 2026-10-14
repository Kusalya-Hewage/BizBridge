package com.bizbridge.connect

import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvUploadImage: TextView
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

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

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize views
        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvUploadImage = findViewById(R.id.tvUploadImage)
        btnRegister = findViewById(R.id.btnRegister)

        // Open gallery
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

        // Login link
        findViewById<TextView>(R.id.tvGoToLogin).setOnClickListener {
            finish()
        }

        // Register button
        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {

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

        // Validate inputs
        when {
            name.isBlank() -> {
                showMessage("Enter your full name")
                return
            }

            email.isBlank() ||
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showMessage("Enter a valid email address")
                return
            }

            phone.isBlank() -> {
                showMessage("Enter your phone number")
                return
            }

            password.length < 6 -> {
                showMessage(
                    "Password must have at least 6 characters"
                )
                return
            }

            password != confirmPassword -> {
                showMessage("Passwords do not match")
                return
            }

            !termsAccepted -> {
                showMessage("Please accept the terms")
                return
            }
        }

        // Prevent multiple registration requests
        btnRegister.isEnabled = false
        btnRegister.text = "Creating account..."

        // Create Firebase Authentication account
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val firebaseUser = auth.currentUser

                    if (firebaseUser == null) {
                        btnRegister.isEnabled = true
                        btnRegister.text = "Create Account"
                        showMessage(
                            "Account created, but user details could not be loaded."
                        )
                        return@addOnCompleteListener
                    }

                    val uid = firebaseUser.uid

                    // Prepare user details for Firestore
                    val userData = hashMapOf(
                        "uid" to uid,
                        "fullName" to name,
                        "email" to email,
                        "phone" to phone,
                        "accountType" to accountType,
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    // Save user details in users collection
                    db.collection("users")
                        .document(uid)
                        .set(userData)
                        .addOnSuccessListener {

                            btnRegister.isEnabled = true
                            btnRegister.text = "Create Account"

                            showMessage(
                                "Account created successfully!"
                            )
                        }
                        .addOnFailureListener { exception ->

                            btnRegister.isEnabled = true
                            btnRegister.text = "Create Account"

                            showMessage(
                                "Account created, but profile saving failed. " +
                                        "Please check your connection and Firestore rules."
                            )
                        }

                } else {

                    btnRegister.isEnabled = true
                    btnRegister.text = "Create Account"

                    val errorMessage = task.exception?.localizedMessage
                        ?: "Registration failed. Please try again."

                    showMessage(errorMessage)
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
            Toast.LENGTH_LONG
        ).show()
    }
}
