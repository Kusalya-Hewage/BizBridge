package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_dashboard)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val user = auth.currentUser

        // Check whether the customer is logged in
        if (user == null) {
            openLogin()
            return
        }

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val etSearch = findViewById<EditText>(R.id.etSearch)

        // Load customer name from Firestore
        db.collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {
                    val name = document.getString("fullName")
                        ?.trim()
                        ?.split("\\s+".toRegex())
                        ?.firstOrNull()

                    if (!name.isNullOrBlank()) {
                        tvGreeting.text = "Hello, $name 👋"
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Could not load customer details.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // Search field
        etSearch.setOnClickListener {
            openBusinessSearch()
        }

        etSearch.setOnEditorActionListener { _, _, _ ->
            openBusinessSearch()
            true
        }

        // Category selections
        val categoryIds = listOf(
            R.id.catSalon,
            R.id.catBeauty,
            R.id.catFitness,
            R.id.catRepair,
            R.id.catEducation,
            R.id.catOther
        )

        categoryIds.forEach { id ->
            findViewById<TextView>(id).setOnClickListener { view ->

                val category = (view as TextView).text.toString()

                val intent = Intent(
                    this,
                    BusinessSearchActivity::class.java
                )

                intent.putExtra("selectedCategory", category)
                startActivity(intent)
            }
        }

        // Demo business buttons
        findViewById<TextView>(R.id.btnViewSalon)
            .setOnClickListener {
                openBusinessSearch("Salon")
            }

        findViewById<TextView>(R.id.btnViewFitness)
            .setOnClickListener {
                openBusinessSearch("Fitness")
            }

        // Popular service booking button
        findViewById<TextView>(R.id.btnBookService)
            .setOnClickListener {
                openBusinessSearch()
                Toast.makeText(
                    this,
                    "Choose a business to continue.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // See all businesses
        findViewById<TextView>(R.id.tvSeeAllBusinesses)
            .setOnClickListener {
                openBusinessSearch()
            }

        // Bottom navigation
        findViewById<TextView>(R.id.navHome)
            .setOnClickListener {
                // Already on Customer Dashboard
            }

        findViewById<TextView>(R.id.navSearch)
            .setOnClickListener {
                openBusinessSearch()
            }

        findViewById<TextView>(R.id.navBookings)
            .setOnClickListener {
                showComingSoon("My Bookings")
            }

        findViewById<TextView>(R.id.navNotifications)
            .setOnClickListener {
                showComingSoon("Notifications")
            }

        findViewById<TextView>(R.id.navProfile)
            .setOnClickListener {
                showComingSoon("My Profile")
            }

        // Logout
        findViewById<TextView>(R.id.btnLogout)
            .setOnClickListener {
                auth.signOut()
                openLogin()
            }
    }

    private fun openBusinessSearch(category: String? = null) {

        val intent = Intent(
            this,
            BusinessSearchActivity::class.java
        )

        if (!category.isNullOrBlank()) {
            intent.putExtra("selectedCategory", category)
        }

        startActivity(intent)
    }

    private fun showComingSoon(feature: String) {
        Toast.makeText(
            this,
            "$feature feature is not connected yet.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun openLogin() {
        val intent = Intent(
            this,
            LoginActivity::class.java
        )

        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)
        finish()
    }
}
