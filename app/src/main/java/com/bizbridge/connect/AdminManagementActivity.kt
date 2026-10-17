package com.example.bizbridge.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bizbridge.connect.R
import com.example.localcommunity.AdminBusinessesActivity
import com.example.localcommunity.AdminReportsActivity

class AdminManagementActivity : AppCompatActivity() {

    private lateinit var btnLoadMore: Button
    private lateinit var tabBusinesses: TextView
    private lateinit var tabReports: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_management)

        // Initialize views
        btnLoadMore = findViewById(R.id.btnLoadMore)
        tabBusinesses = findViewById(R.id.tabBusinesses)
        tabReports = findViewById(R.id.tabReports)

        // Load More button action
        btnLoadMore.setOnClickListener {
            Toast.makeText(this, "Loading more users...", Toast.LENGTH_SHORT).show()
            // Add your pagination / load more logic here
        }

        // Businesses Tab click listener to open AdminBusinessesActivity
        tabBusinesses.setOnClickListener {
            val intent = Intent(this, AdminBusinessesActivity::class.java)
            startActivity(intent)
        }

        // Reports Tab click listener to open AdminReportsActivity
        tabReports.setOnClickListener {
            val intent = Intent(this, AdminReportsActivity::class.java)
            startActivity(intent)
        }
    }
}