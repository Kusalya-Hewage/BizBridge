
package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MyBookingsActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    private lateinit var adapter: BookingAdapter
    private lateinit var progress: ProgressBar
    private lateinit var emptyMessage: TextView
    private lateinit var countText: TextView

    private lateinit var upcomingTab: TextView
    private lateinit var completedTab: TextView
    private lateinit var cancelledTab: TextView

    private val allBookings = mutableListOf<Booking>()
    private var selectedFilter = "UPCOMING"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)

        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in first.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        progress = findViewById(R.id.progressBookings)
        emptyMessage = findViewById(R.id.tvNoBookings)
        countText = findViewById(R.id.tvBookingsCount)

        upcomingTab = findViewById(R.id.btnFilterUpcoming)
        completedTab = findViewById(R.id.btnFilterCompleted)
        cancelledTab = findViewById(R.id.btnFilterCancelled)

        val recycler = findViewById<RecyclerView>(R.id.rvBookings)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = BookingAdapter(emptyList()) { booking ->
            startActivity(
                Intent(this, BookingDetailsActivity::class.java)
                    .putExtra("bookingId", booking.id)
            )
        }
        recycler.adapter = adapter

        upcomingTab.setOnClickListener { selectFilter("UPCOMING") }
        completedTab.setOnClickListener { selectFilter("COMPLETED") }
        cancelledTab.setOnClickListener { selectFilter("CANCELLED") }

        setupNavigation()
        loadBookings()
    }

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized && auth.currentUser != null) {
            loadBookings()
        }
    }

    private fun loadBookings() {
        val uid = auth.currentUser?.uid ?: return
        progress.visibility = View.VISIBLE
        emptyMessage.visibility = View.GONE

        db.collection("bookings")
            .whereEqualTo("customerId", uid)
            .get()
            .addOnSuccessListener { result ->
                allBookings.clear()

                for (document in result.documents) {
                    val booking = Booking(
                        id = document.id,
                        businessName = document.getString("businessName").orEmpty(),
                        serviceName = document.getString("serviceName").orEmpty(),
                        appointmentDate = document.getString("appointmentDate").orEmpty(),
                        appointmentTime = document.getString("appointmentTime").orEmpty(),
                        servicePrice = document.getLong("servicePrice") ?: 0L,
                        status = document.getString("status") ?: "PENDING",
                        serviceDuration = document.getLong("serviceDuration") ?: 0L,
                        businessAddress = document.getString("businessAddress").orEmpty()
                    )
                    allBookings.add(booking)
                }

                allBookings.sortWith(
                    compareBy<Booking> { it.appointmentDate }
                        .thenBy { it.appointmentTime }
                )

                progress.visibility = View.GONE
                showFilteredBookings()
            }
            .addOnFailureListener { exception ->
                progress.visibility = View.GONE
                emptyMessage.visibility = View.VISIBLE
                emptyMessage.text = "Could not load bookings. Please try again."
                Toast.makeText(
                    this,
                    exception.localizedMessage ?: "Failed to load bookings.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun selectFilter(filter: String) {
        selectedFilter = filter
        updateTabStyles()
        showFilteredBookings()
    }

    private fun updateTabStyles() {
        val tabs = listOf(
            "UPCOMING" to upcomingTab,
            "COMPLETED" to completedTab,
            "CANCELLED" to cancelledTab
        )

        tabs.forEach { (filter, tab) ->
            if (filter == selectedFilter) {
                tab.setBackgroundResource(R.drawable.bg_dashboard_card)
                tab.setTextColor(getColor(R.color.primary))
                tab.setTypeface(null, android.graphics.Typeface.BOLD)
            } else {
                tab.setBackgroundResource(android.R.color.transparent)
                tab.setTextColor(getColor(R.color.text_secondary))
                tab.setTypeface(null, android.graphics.Typeface.NORMAL)
            }
        }
    }

    private fun showFilteredBookings() {
        val today = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        ).format(Date())

        val filtered = when (selectedFilter) {
            "COMPLETED" -> allBookings.filter {
                it.status.equals("COMPLETED", ignoreCase = true)
            }

            "CANCELLED" -> allBookings.filter {
                it.status.equals("CANCELLED", ignoreCase = true)
            }

            else -> allBookings.filter {
                it.status.uppercase(Locale.ROOT) in listOf("PENDING", "CONFIRMED") &&
                        it.appointmentDate >= today
            }
        }

        adapter.updateData(filtered)

        countText.text = when (selectedFilter) {
            "UPCOMING" -> "${filtered.size} upcoming appointments"
            "COMPLETED" -> "${filtered.size} completed appointments"
            else -> "${filtered.size} cancelled appointments"
        }

        emptyMessage.visibility =
            if (filtered.isEmpty()) View.VISIBLE else View.GONE

        emptyMessage.text = when (selectedFilter) {
            "UPCOMING" -> "No upcoming appointments yet."
            "COMPLETED" -> "No completed appointments yet."
            else -> "No cancelled appointments."
        }
    }

    private fun setupNavigation() {

        findViewById<View>(R.id.navHome).setOnClickListener {
            startActivity(
                Intent(this, CustomerDashboardActivity::class.java)
            )
            finish()
        }

        findViewById<View>(R.id.navSearch).setOnClickListener {
            startActivity(
                Intent(this, BusinessSearchActivity::class.java)
            )
            finish()
        }

        findViewById<View>(R.id.navBookings).setOnClickListener {
            // Already on My Bookings page
        }
    }
}
