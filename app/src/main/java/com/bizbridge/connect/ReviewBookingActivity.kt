
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
import java.text.SimpleDateFormat
import java.util.Locale

class ReviewBookingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etCustomerName: EditText
    private lateinit var etCustomerPhone: EditText
    private lateinit var btnConfirm: Button

    private var businessName = ""
    private var businessAddress = ""
    private var serviceName = ""
    private var servicePrice = 0
    private var serviceDuration = 0
    private var appointmentDate = ""
    private var appointmentTime = ""
    private var bookingNotes = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_booking)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in first.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        businessName = intent.getStringExtra("businessName").orEmpty()
        businessAddress = intent.getStringExtra("businessAddress").orEmpty()
        serviceName = intent.getStringExtra("serviceName").orEmpty()
        servicePrice = intent.getIntExtra("servicePrice", 0)
        serviceDuration = intent.getIntExtra("serviceDuration", 0)
        appointmentDate = intent.getStringExtra("appointmentDate").orEmpty()
        appointmentTime = intent.getStringExtra("appointmentTime").orEmpty()
        bookingNotes = intent.getStringExtra("notes").orEmpty()

        if (businessName.isBlank() ||
            serviceName.isBlank() ||
            appointmentDate.isBlank() ||
            appointmentTime.isBlank()
        ) {
            Toast.makeText(
                this,
                "Booking details are missing. Please select them again.",
                Toast.LENGTH_LONG
            ).show()
            finish()
            return
        }

        etCustomerName = findViewById(R.id.etReviewCustomerName)
        etCustomerPhone = findViewById(R.id.etReviewCustomerPhone)
        btnConfirm = findViewById(R.id.btnConfirmReviewBooking)

        findViewById<TextView>(R.id.tvReviewBusinessName).text = businessName
        findViewById<TextView>(R.id.tvReviewServiceName).text = serviceName
        findViewById<TextView>(R.id.tvReviewDate).text =
            "Date: ${formatDate(appointmentDate)}"
        findViewById<TextView>(R.id.tvReviewTime).text =
            "Time: $appointmentTime"
        findViewById<TextView>(R.id.tvReviewDuration).text =
            "Duration: $serviceDuration min"
        findViewById<TextView>(R.id.tvReviewPrice).text =
            "Price: ${formatPrice(servicePrice)}"
        findViewById<TextView>(R.id.tvReviewAddress).text =
            "Address: ${businessAddress.ifBlank { "Not provided" }}"

        findViewById<TextView>(R.id.btnReviewBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnEditReviewBooking).setOnClickListener {
            finish()
        }

        loadCustomerDetails()

        btnConfirm.setOnClickListener {
            saveBooking()
        }
    }

    private fun loadCustomerDetails() {
        val user = auth.currentUser ?: return

        db.collection("users")
            .document(user.uid)
            .get()
            .addOnSuccessListener { document ->
                if (isFinishing || isDestroyed) return@addOnSuccessListener

                etCustomerName.setText(
                    document.getString("fullName").orEmpty()
                )
                etCustomerPhone.setText(
                    document.getString("phone").orEmpty()
                )
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Could not load your profile. Please enter your details.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun saveBooking() {
        val user = auth.currentUser ?: return

        val customerName = etCustomerName.text.toString().trim()
        val customerPhone = etCustomerPhone.text.toString().trim()

        if (customerName.isBlank()) {
            etCustomerName.error = "Enter your name"
            etCustomerName.requestFocus()
            return
        }

        if (customerPhone.isBlank()) {
            etCustomerPhone.error = "Enter your phone number"
            etCustomerPhone.requestFocus()
            return
        }

        if (customerPhone.length < 9) {
            etCustomerPhone.error = "Enter a valid phone number"
            etCustomerPhone.requestFocus()
            return
        }

        btnConfirm.isEnabled = false
        btnConfirm.text = "Booking..."

        val booking = hashMapOf(
            "customerId" to user.uid,
            "customerEmail" to (user.email ?: ""),
            "customerName" to customerName,
            "customerPhone" to customerPhone,
            "businessName" to businessName,
            "businessAddress" to businessAddress,
            "serviceName" to serviceName,
            "servicePrice" to servicePrice,
            "serviceDuration" to serviceDuration,
            "appointmentDate" to appointmentDate,
            "appointmentTime" to appointmentTime,
            "notes" to bookingNotes,
            "status" to "PENDING",
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("bookings")
            .add(booking)
            .addOnSuccessListener { document ->
                val next = Intent(
                    this,
                    BookingConfirmationActivity::class.java
                ).apply {
                    putExtra("bookingId", document.id)
                    putExtra("businessName", businessName)
                    putExtra("serviceName", serviceName)
                    putExtra("appointmentDate", appointmentDate)
                    putExtra("appointmentTime", appointmentTime)
                    putExtra("servicePrice", servicePrice)
                }

                startActivity(next)
                finish()
            }
            .addOnFailureListener { exception ->
                btnConfirm.isEnabled = true
                btnConfirm.text = "Confirm Booking"

                Toast.makeText(
                    this,
                    "Booking failed: ${exception.localizedMessage ?: "Please try again"}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun formatDate(date: String): String {
        return try {
            val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val output = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            output.format(input.parse(date)!!)
        } catch (_: Exception) {
            date
        }
    }

    private fun formatPrice(price: Int): String {
        return "Rs. ${String.format(Locale.US, "%,d", price)}"
    }
}
