package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessBookingsFragment :
       Fragment(R.layout.fragment_business_bookings){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cards = listOf(
            view.findViewById<View>(R.id.bookingCard1),
            view.findViewById<View>(R.id.bookingCard2),
            view.findViewById<View>(R.id.bookingCard3)
        )

        val tabs = listOf(
            view.findViewById<TextView>(R.id.tabPending),
            view.findViewById<TextView>(R.id.tabConfirmed),
            view.findViewById<TextView>(R.id.tabCompleted),
            view.findViewById<TextView>(R.id.tabCancelled)
        )

        val summary =
            view.findViewById<TextView>(R.id.tvBookingSummary)

        val statuses = listOf(
            "PENDING",
            "PENDING",
            "PENDING"
        )

        val bookingStatuses = mutableListOf(
            "Pending",
            "Pending",
            "Pending"
        )

        fun showMessage(message: String) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        fun selectTab(selected: Int) {
            tabs.forEachIndexed { index, tab ->
                tab.setBackgroundResource(
                    if (index == selected) {
                        R.drawable.bg_booking_tab_selected
                    } else {
                        android.R.color.transparent
                    }
                )

                tab.setTextColor(
                    android.graphics.Color.parseColor(
                        if (index == selected) "#1976C9"
                        else "#65788B"
                    )
                )

                tab.setTypeface(
                    null,
                    if (index == selected) android.graphics.Typeface.BOLD
                    else android.graphics.Typeface.NORMAL
                )
            }

            val selectedStatus = tabs[selected].text.toString()

            var visibleCount = 0

            cards.forEachIndexed { index, card ->
                val matches = bookingStatuses[index] == selectedStatus

                card.visibility =
                    if (matches) View.VISIBLE else View.GONE

                if (matches) visibleCount++
            }

            summary.text = when (selectedStatus) {
                "Pending" ->
                    "$visibleCount requests waiting for your response"
                "Confirmed" ->
                    "$visibleCount confirmed bookings"
                "Completed" ->
                    "$visibleCount completed bookings"
                else ->
                    "$visibleCount cancelled bookings"
            }
        }

        tabs.forEachIndexed { index, tab ->
            tab.setOnClickListener {
                selectTab(index)
            }
        }

        val viewButtonIds = listOf(
            R.id.btnViewBooking1,
            R.id.btnViewBooking2,
            R.id.btnViewBooking3
        )

        val customerNames = listOf(
            "Chathuni",
            "Dinithi Fernando",
            "Kavindu Perera"
        )

        val bookingIds = listOf(
            "BB-00141",
            "BB-00142",
            "BB-00143"
        )

        viewButtonIds.forEachIndexed { index, id ->
            view.findViewById<TextView>(id).setOnClickListener {
                showMessage(
                    "${customerNames[index]} · ${bookingIds[index]}"
                )

                // TODO: Open booking details screen.
                // Accept / Reject actions can be added there.
            }
        }

        view.findViewById<LinearLayout>(R.id.navDashboard)
            .setOnClickListener {
                showMessage("Dashboard selected")
                // TODO: Navigate to BusinessDashboardFragment
            }

        view.findViewById<LinearLayout>(R.id.navBookings)
            .setOnClickListener {
                showMessage("Bookings selected")
            }

        view.findViewById<LinearLayout>(R.id.navServices)
            .setOnClickListener {
                showMessage("Services selected")
                // TODO: Navigate to BusinessServicesFragment
            }

        view.findViewById<LinearLayout>(R.id.navPromotions)
            .setOnClickListener {
                showMessage("Promotions selected")
            }

        view.findViewById<LinearLayout>(R.id.navProfile)
            .setOnClickListener {
                showMessage("Profile selected")
            }

        selectTab(0)
    }
}
