package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        btnLogin = findViewById(R.id.btnLogin)

        findViewById<Button>(R.id.btnCreateAccount)
            .setOnClickListener {
                startActivity(
                    Intent(this, RegisterActivity::class.java)
                )
            }

        btnLogin.setOnClickListener {
            loginUser()
        }

        findViewById<TextView>(R.id.tvForgotPassword)
            .setOnClickListener {
                Toast.makeText(
                    this,
                    "Password reset will be connected next",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun loginUser() {
        val email = findViewById<EditText>(
            R.id.etEmail
        ).text.toString().trim()

        val password = findViewById<EditText>(
            R.id.etPassword
        ).text.toString()

        if (email.isBlank()) {
            showMessage("Please enter your email")
            return
        }

        if (password.isBlank()) {
            showMessage("Please enter your password")
            return
        }

        btnLogin.isEnabled = false
        btnLogin.text = "Logging in..."

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {
                    val user = auth.currentUser

                    if (user == null) {
                        resetLoginButton()
                        showMessage("Unable to load user account")
                        return@addOnCompleteListener
                    }

                    db.collection("users")
                        .document(user.uid)
                        .get()
                        .addOnSuccessListener { document ->

                            if (!document.exists()) {
                                auth.signOut()
                                resetLoginButton()
                                showMessage(
                                    "User profile not found. Please register again."
                                )
                                return@addOnSuccessListener
                            }

                            val accountType =
                                document.getString("accountType")

                            when (accountType) {
                                "CUSTOMER" -> {
                                    val intent = Intent(
                                        this,
                                        CustomerDashboardActivity::class.java
                                    )
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }

                                "BUSINESS_OWNER" -> {
                                    auth.signOut()
                                    resetLoginButton()
                                    showMessage(
                                        "Business Owner dashboard is not ready yet"
                                    )
                                }

                                else -> {
                                    auth.signOut()
                                    resetLoginButton()
                                    showMessage(
                                        "Invalid account type. Please contact support."
                                    )
                                }
                            }
                        }
                        .addOnFailureListener {
                            auth.signOut()
                            resetLoginButton()
                            showMessage(
                                "Could not load profile. Check your connection and Firestore rules."
                            )
                        }

                } else {
                    resetLoginButton()
                    showMessage(
                        task.exception?.localizedMessage
                            ?: "Login failed. Please try again."
                    )
                }
            }
    }

    private fun resetLoginButton() {
        btnLogin.isEnabled = true
        btnLogin.text = "Login"
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}
