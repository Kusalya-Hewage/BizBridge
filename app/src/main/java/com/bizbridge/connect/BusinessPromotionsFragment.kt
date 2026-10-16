package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class BusinessPromotionsFragment :

    Fragment(R.layout.fragment_business_promotions){

    private var isActiveTab = true

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val tabActive =
            view.findViewById<TextView>(
                R.id.tabActivePromotions
            )

        val tabExpired =
            view.findViewById<TextView>(
                R.id.tabExpiredPromotions
            )

        val summary =
            view.findViewById<TextView>(
                R.id.tvPromotionSummary
            )

        val card =
            view.findViewById<View>(
                R.id.promotionCard
            )

        val status =
            view.findViewById<TextView>(
                R.id.tvPromotionStatus
            )

        val title =
            view.findViewById<TextView>(
                R.id.tvPromotionTitle
            )

        val business =
            view.findViewById<TextView>(
                R.id.tvPromotionBusiness
            )

        val description =
            view.findViewById<TextView>(
                R.id.tvPromotionDescription
            )

        val schedule =
            view.findViewById<TextView>(
                R.id.tvPromotionSchedule
            )

        val views =
            view.findViewById<TextView>(
                R.id.tvOfferViews
            )

        val saved =
            view.findViewById<TextView>(
                R.id.tvOfferSaved
            )

        val btnEdit =
            view.findViewById<Button>(
                R.id.btnEditPromotion
            )

        val btnDelete =
            view.findViewById<Button>(
                R.id.btnDeletePromotion
            )

        val btnCreate =
            view.findViewById<Button>(
                R.id.btnCreatePromotion
            )

        fun showActiveTab(active: Boolean) {
            isActiveTab = active

            if (active) {
                tabActive.setBackgroundResource(
                    R.drawable.bg_promotion_tab_selected
                )
                tabExpired.background = null

                tabActive.setTextColor(
                    android.graphics.Color.rgb(25, 118, 201)
                )
                tabExpired.setTextColor(
                    android.graphics.Color.rgb(113, 129, 148)
                )

                summary.text =
                    "1 active promotion · 36 views this week"

                card.visibility = View.VISIBLE
                title.text = "20% OFF Haircut"
                business.text = "ABC Salon · Valid until 20 Oct"
                description.text =
                    "A fresh look for less. Book your next haircut and save."
                status.text = "ACTIVE"
                status.setTextColor(
                    android.graphics.Color.rgb(39, 131, 111)
                )
                schedule.text =
                    "Offer runs from 9–20 October 2026. Available for Haircut appointments only."

                views.text = "36"
                saved.text = "08"

            } else {
                tabExpired.setBackgroundResource(
                    R.drawable.bg_promotion_tab_selected
                )
                tabActive.background = null

                tabExpired.setTextColor(
                    android.graphics.Color.rgb(25, 118, 201)
                )
                tabActive.setTextColor(
                    android.graphics.Color.rgb(113, 129, 148)
                )

                summary.text = "Expired promotions"

                card.visibility = View.GONE

                schedule.text =
                    "Your expired promotions will appear here."

                views.text = "0"
                saved.text = "0"
            }
        }

        tabActive.setOnClickListener {
            showActiveTab(true)
        }

        tabExpired.setOnClickListener {
            showActiveTab(false)
        }

        btnEdit.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect the Edit Promotion screen here",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete promotion?")
                .setMessage(
                    "Are you sure you want to delete this promotion?"
                )
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete") { _, _ ->
                    card.visibility = View.GONE

                    summary.text =
                        "No active promotions"

                    Toast.makeText(
                        requireContext(),
                        "Demo promotion removed from screen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
        }

        btnCreate.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect the Create Promotion screen here",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Bottom navigation demo handlers
        view.findViewById<TextView>(
            R.id.navDashboard
        ).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect Dashboard screen",
                Toast.LENGTH_SHORT
            ).show()
        }

        view.findViewById<TextView>(
            R.id.navBookings
        ).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect Bookings screen",
                Toast.LENGTH_SHORT
            ).show()
        }

        view.findViewById<TextView>(
            R.id.navServices
        ).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect Services screen",
                Toast.LENGTH_SHORT
            ).show()
        }

        view.findViewById<TextView>(
            R.id.navProfile
        ).setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect Profile screen",
                Toast.LENGTH_SHORT
            ).show()
        }

        showActiveTab(true)
    }
}
