package com.bizbridge.connect

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Firebase connection test
        try {
            val firebaseApp = FirebaseApp.getInstance()
            val projectId = firebaseApp.options.projectId

            Log.d(
                "BizBridgeFirebase",
                "Firebase Project ID: $projectId"
            )

            Toast.makeText(
                this,
                "Firebase initialized successfully!",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Log.e(
                "BizBridgeFirebase",
                "Firebase initialization failed",
                e
            )

            Toast.makeText(
                this,
                "Firebase initialization failed. Check Logcat.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
