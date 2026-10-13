package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btnCreateAccount)
            .setOnClickListener {
                startActivity(
                    Intent(this, RegisterActivity::class.java)
                )
            }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            Toast.makeText(
                this,
                "Firebase login will be connected next",
                Toast.LENGTH_SHORT
            ).show()
        }

        findViewById<android.widget.TextView>(
            R.id.tvForgotPassword
        ).setOnClickListener {
            Toast.makeText(
                this,
                "Password reset will be connected next",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
