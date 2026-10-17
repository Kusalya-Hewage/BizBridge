package com.example.localcommunity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bizbridge.connect.R
import com.example.bizbridge.admin.AdminManagementActivity

class AdminReportsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_reports_management)

        val btnClose = findViewById<ImageView>(R.id.btnClose)
        val tabUsers = findViewById<TextView>(R.id.tabUsers)
        val tabBusinesses = findViewById<TextView>(R.id.tabBusinesses)

        btnClose.setOnClickListener {
            finish()
        }

        tabUsers.setOnClickListener {
            val intent = Intent(this, AdminManagementActivity::class.java)
            startActivity(intent)
            finish()
        }

        tabBusinesses.setOnClickListener {
            val intent = Intent(this, AdminBusinessesActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}