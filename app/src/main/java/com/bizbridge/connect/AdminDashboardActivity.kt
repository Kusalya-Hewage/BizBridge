package com.example.localcommunity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.bizbridge.connect.R
import com.example.bizbridge.admin.AdminManagementActivity

class AdminDashboardActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_admin_dashboard)

            findViewById<ImageButton>(R.id.btnNotification)
                .setOnClickListener {
                    showMessage("No new notifications")
                }

            findViewById<TextView>(R.id.btnViewAll)
                .setOnClickListener {
                    showMessage("Opening all pending businesses")
                }

            findViewById<Button>(R.id.btnViewABC)
                .setOnClickListener {
                    showMessage("Viewing ABC Salon details")
                }

            findViewById<Button>(R.id.btnApproveABC)
                .setOnClickListener {
                    showMessage("ABC Salon approval selected")
                }

            findViewById<Button>(R.id.btnRejectABC)
                .setOnClickListener {
                    showMessage("ABC Salon rejection selected")
                }

            findViewById<Button>(R.id.btnViewAcademy)
                .setOnClickListener {
                    showMessage("Viewing Bright Minds Academy")
                }

            findViewById<Button>(R.id.btnApproveAcademy)
                .setOnClickListener {
                    showMessage("Academy approval selected")
                }

            findViewById<Button>(R.id.btnRejectAcademy)
                .setOnClickListener {
                    showMessage("Academy rejection selected")
                }

            findViewById<Button>(R.id.btnViewBooking)
                .setOnClickListener {
                    showMessage("Viewing booking BB-00125")
                }

            // Updated to open AdminManagementActivity
            findViewById<Button>(R.id.btnOpenManagement)
                .setOnClickListener {
                    val intent = Intent(this, AdminManagementActivity::class.java)
                    startActivity(intent)
                }

        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Dashboard error: ${e.message}",
                Toast.LENGTH_LONG
            ).show()

            android.util.Log.e(
                "AdminDashboard",
                "Dashboard failed to load",
                e
            )
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}