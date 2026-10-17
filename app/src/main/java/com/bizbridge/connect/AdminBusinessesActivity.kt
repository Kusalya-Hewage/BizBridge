package com.example.localcommunity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bizbridge.connect.R
import com.example.bizbridge.admin.AdminManagementActivity

class AdminBusinessesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_businesses)

        val btnClose = findViewById<ImageView>(R.id.btnClose)
        val tabUsers = findViewById<TextView>(R.id.tabUsers)
        val tabReports = findViewById<TextView>(R.id.tabReports)

        btnClose.setOnClickListener {
            finish()
        }

        tabUsers.setOnClickListener {
            // Switch back to Users tab (AdminManagementActivity)
            val intent = Intent(this, AdminManagementActivity::class.java)
            startActivity(intent)
            finish()
        }

        tabReports.setOnClickListener {
            // Switch to Reports tab (AdminReportsActivity)
            val intent = Intent(this, AdminReportsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}