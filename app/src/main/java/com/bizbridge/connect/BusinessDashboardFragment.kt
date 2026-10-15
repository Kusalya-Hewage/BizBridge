package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessDashboardFragment : Fragment(R.layout.fragment_business_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Notifications
        view.findViewById<TextView>(R.id.btnNotifications).setOnClickListener {
            showMessage("Notifications clicked")
        }

        // Today's appointments
        view.findViewById<TextView>(R.id.btnSeeAllAppointments).setOnClickListener {
            showMessage("All appointments clicked")
        }

        view.findViewById<TextView>(R.id.btnViewAppointment1).setOnClickListener {
            showMessage("Appointment 1 clicked")
        }

        view.findViewById<TextView>(R.id.btnViewAppointment2).setOnClickListener {
            showMessage("Appointment 2 clicked")
        }

        // Recent bookings
        view.findViewById<TextView>(R.id.btnViewAllBookings).setOnClickListener {
            showMessage("All bookings clicked")
        }

        view.findViewById<TextView>(R.id.btnViewRecentBooking).setOnClickListener {
            showMessage("Recent booking clicked")
        }

        // Promotions
        view.findViewById<TextView>(R.id.btnManagePromotions).setOnClickListener {
            showMessage("Manage promotions clicked")
        }

        view.findViewById<TextView>(R.id.btnExploreOffer).setOnClickListener {
            showMessage("Explore offer clicked")
        }

        // Bottom navigation
        view.findViewById<TextView>(R.id.navDashboard).setOnClickListener {
            showMessage("Dashboard")
        }

        view.findViewById<TextView>(R.id.navBookings).setOnClickListener {
            showMessage("Bookings clicked")
            // TODO: Navigate to BusinessBookingsFragment
        }

        view.findViewById<TextView>(R.id.navServices).setOnClickListener {
            showMessage("Services clicked")
            // TODO: Navigate to BusinessServicesFragment
        }

        view.findViewById<TextView>(R.id.navPromotions).setOnClickListener {
            showMessage("Promotions clicked")
            // TODO: Navigate to promotions screen
        }

        view.findViewById<TextView>(R.id.navProfile).setOnClickListener {
            showMessage("Profile clicked")
            // TODO: Navigate to profile screen
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}