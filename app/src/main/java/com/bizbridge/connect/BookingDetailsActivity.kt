package com.bizbridge.connect

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class BookingDetailsActivity : AppCompatActivity() {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    private lateinit var bookingId: String
    private var businessPhone = ""
    private var loaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)

        bookingId = intent.getStringExtra("bookingId").orEmpty()

        if (auth.currentUser == null || bookingId.isBlank()) {
            finish()
            return
        }

        findViewById<TextView>(R.id.btnDetailsBack).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btnShowAppointmentQr).setOnClickListener {
            if (loaded) {
                startActivity(Intent(this, AppointmentQrActivity::class.java)
                    .putExtra("bookingId", bookingId))
            }
        }

        findViewById<Button>(R.id.btnCancelDetailsBooking).setOnClickListener {
            cancelBooking()
        }

        findViewById<Button>(R.id.btnCallBusiness).setOnClickListener {
            if (businessPhone.isBlank()) {
                Toast.makeText(this, "Business phone number is unavailable.", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$businessPhone")))
            }
        }

        loadBooking()
    }

    private fun loadBooking() {
        val uid = auth.currentUser?.uid ?: return

        db.collection("bookings").document(bookingId).get()
            .addOnSuccessListener { doc ->
                if (!doc.exists() || doc.getString("customerId") != uid) {
                    Toast.makeText(this, "Booking not found.", Toast.LENGTH_LONG).show()
                    finish()
                    return@addOnSuccessListener
                }

                val business = doc.getString("businessName").orEmpty()
                val service = doc.getString("serviceName").orEmpty()
                val date = doc.getString("appointmentDate").orEmpty()
                val time = doc.getString("appointmentTime").orEmpty()
                val address = doc.getString("businessAddress").orEmpty()
                val status = doc.getString("status") ?: "PENDING"
                val price = doc.getLong("servicePrice") ?: 0L
                val duration = doc.getLong("serviceDuration") ?: 0L
                businessPhone = doc.getString("businessPhone").orEmpty()

                findViewById<TextView>(R.id.tvDetailsBookingId).text =
                    "Booking ID: ${bookingId.take(8).uppercase(Locale.ROOT)}"
                findViewById<TextView>(R.id.tvDetailsBusiness).text = business
                findViewById<TextView>(R.id.tvDetailsService).text = service
                findViewById<TextView>(R.id.tvDetailsStatus).text = status
                findViewById<TextView>(R.id.tvDetailsDate).text = "Date: ${formatDate(date)}"
                findViewById<TextView>(R.id.tvDetailsTime).text = "Time: $time"
                findViewById<TextView>(R.id.tvDetailsDuration).text = "Duration: $duration min"
                findViewById<TextView>(R.id.tvDetailsPrice).text =
                    "Price: Rs. ${String.format(Locale.US, "%,d", price)}"
                findViewById<TextView>(R.id.tvDetailsAddress).text = "Address: $address"

                val canCancel = status in listOf("PENDING", "CONFIRMED")
                findViewById<Button>(R.id.btnCancelDetailsBooking).isEnabled = canCancel
                findViewById<Button>(R.id.btnShowAppointmentQr).isEnabled =
                    status != "CANCELLED"

                loaded = true
            }
            .addOnFailureListener {
                Toast.makeText(this, "Could not load booking.", Toast.LENGTH_LONG).show()
            }
    }

    private fun cancelBooking() {
        AlertDialog.Builder(this)
            .setTitle("Cancel booking?")
            .setMessage("Are you sure you want to cancel this appointment?")
            .setNegativeButton("Keep booking", null)
            .setPositiveButton("Cancel booking") { _, _ ->
                val uid = auth.currentUser?.uid ?: return@setPositiveButton

                db.collection("bookings").document(bookingId).get()
                    .addOnSuccessListener { doc ->
                        if (doc.getString("customerId") != uid) {
                            Toast.makeText(this, "Not authorised.", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        val status = doc.getString("status") ?: ""
                        if (status !in listOf("PENDING", "CONFIRMED")) {
                            Toast.makeText(this, "This booking cannot be cancelled.", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }

                        db.collection("bookings").document(bookingId)
                            .update("status", "CANCELLED")
                            .addOnSuccessListener {
                                Toast.makeText(this, "Booking cancelled.", Toast.LENGTH_SHORT).show()
                                loadBooking()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Cancellation failed.", Toast.LENGTH_LONG).show()
                            }
                    }
            }
            .show()
    }

    private fun formatDate(date: String): String = try {
        val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val output = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        output.format(input.parse(date)!!)
    } catch (_: Exception) {
        date
    }
}
