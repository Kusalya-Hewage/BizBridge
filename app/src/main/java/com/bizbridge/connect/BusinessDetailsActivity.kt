package com.bizbridge.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class BusinessDetailsActivity : AppCompatActivity() {

    private var businessName = "ABC Salon"
    private var businessCategory = "Salon · Beauty"
    private var businessRating = "4.8"
    private var reviewCount = "124"
    private var businessAddress = "42 Galle Road, Colombo 03"
    private var businessPhone = "0774567890"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_details)

        // Receive business information from the Search screen
        businessName = intent.getStringExtra("businessName")
            ?: "ABC Salon"

        businessCategory = intent.getStringExtra("businessCategory")
            ?: "Salon · Beauty"

        businessRating = intent.getStringExtra("businessRating")
            ?: "4.8"

        reviewCount = intent.getStringExtra("reviewCount")
            ?: "124"

        businessAddress = intent.getStringExtra("businessAddress")
            ?: "42 Galle Road, Colombo 03"

        businessPhone = intent.getStringExtra("businessPhone")
            ?: "0774567890"

        // Business information
        findViewById<TextView>(R.id.tvBusinessName).text =
            businessName

        findViewById<TextView>(R.id.tvBusinessCategory).text =
            businessCategory

        findViewById<TextView>(R.id.tvBusinessRating).text =
            "★ $businessRating · $reviewCount reviews"

        findViewById<TextView>(R.id.tvBusinessAddress).text =
            "$businessAddress · 0.8 km"

        findViewById<TextView>(R.id.tvBusinessStatus).text =
            "OPEN"

        findViewById<TextView>(R.id.tvBusinessDescription).text =
            "Your neighborhood business for quality services. " +
                    "Our experienced team is ready to help you."

        // Set service count
        findViewById<TextView>(R.id.tvServicesCount).text =
            "2 services"

        // Back button
        findViewById<TextView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Call business
        findViewById<Button>(R.id.btnCall).setOnClickListener {
            callBusiness()
        }

        // Get directions
        findViewById<Button>(R.id.btnDirections).setOnClickListener {
            openDirections()
        }

        // Book service 1
        findViewById<Button>(R.id.btnBookService1).setOnClickListener {
            openBooking(
                serviceName = "Haircut",
                price = 1500,
                duration = 30
            )
        }

        // Book service 2
        findViewById<Button>(R.id.btnBookService2).setOnClickListener {
            openBooking(
                serviceName = "Hair wash & blow dry",
                price = 2500,
                duration = 45
            )
        }

        // Promotion
        findViewById<TextView>(R.id.tvExplorePromotion)
            .setOnClickListener {
                Toast.makeText(
                    this,
                    "Promotion details will be connected next.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // Reviews
        findViewById<TextView>(R.id.tvSeeAllReviews)
            .setOnClickListener {
                Toast.makeText(
                    this,
                    "Reviews screen will be connected next.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun callBusiness() {
        val phoneUri = Uri.parse("tel:$businessPhone")

        val intent = Intent(Intent.ACTION_DIAL, phoneUri)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Unable to open the phone app.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openDirections() {
        val mapUri = Uri.parse(
            "geo:0,0?q=${Uri.encode(businessAddress)}"
        )

        val intent = Intent(Intent.ACTION_VIEW, mapUri)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No map application is available.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openBooking(
        serviceName: String,
        price: Int,
        duration: Int
    ) {
        val intent = Intent(
            this,
            AppointmentBookingActivity::class.java
        )

        intent.putExtra("businessName", businessName)
        intent.putExtra("businessAddress", businessAddress)
        intent.putExtra("serviceName", serviceName)
        intent.putExtra("servicePrice", price)
        intent.putExtra("serviceDuration", duration)

        startActivity(intent)
    }
}