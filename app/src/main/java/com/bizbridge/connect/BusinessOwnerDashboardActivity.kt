package com.bizbridge.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessDashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_business_dashboard,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom navigation
        view.findViewById<TextView>(R.id.navDashboard)
            .setOnClickListener {
                showMessage("Dashboard")
            }

        view.findViewById<TextView>(R.id.navBookings)
            .setOnClickListener {
                showMessage("Bookings")
            }

        view.findViewById<TextView>(R.id.navServices)
            .setOnClickListener {
                showMessage("Services")
            }

        view.findViewById<TextView>(R.id.navPromotions)
            .setOnClickListener {
                showMessage("Promotions")
            }

        view.findViewById<TextView>(R.id.navProfile)
            .setOnClickListener {
                showMessage("Business Profile")
            }

        // Notifications
        view.findViewById<TextView>(R.id.btnNotifications)
            .setOnClickListener {
                showMessage("Notifications")
            }

        // Today's appointments
        view.findViewById<TextView>(R.id.btnSeeAllAppointments)
            .setOnClickListener {
                showMessage("All Appointments")
            }

        view.findViewById<TextView>(R.id.btnViewAppointment1)
            .setOnClickListener {
                showMessage("Dinithi Fernando - Appointment")
            }

        view.findViewById<TextView>(R.id.btnViewAppointment2)
            .setOnClickListener {
                showMessage("Kavindu Perera - Appointment")
            }

        // Recent bookings
        view.findViewById<TextView>(R.id.btnViewAllBookings)
            .setOnClickListener {
                showMessage("All Bookings")
            }

        view.findViewById<TextView>(R.id.btnViewRecentBooking)
            .setOnClickListener {
                showMessage("Recent Booking Details")
            }

        // Promotions
        view.findViewById<TextView>(R.id.btnManagePromotions)
            .setOnClickListener {
                showMessage("Manage Promotions")
            }

        view.findViewById<TextView>(R.id.btnExploreOffer)
            .setOnClickListener {
                showMessage("Explore Offer")
            }
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}