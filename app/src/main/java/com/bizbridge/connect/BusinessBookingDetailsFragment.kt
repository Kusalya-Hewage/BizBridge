package com.bizbridge.connect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessBookingDetailsFragment :
  Fragment(R.layout.fragment_booking_details){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack =
            view.findViewById<TextView>(R.id.btnBookingBack)

        val btnScanQr =
            view.findViewById<Button>(R.id.btnScanBookingQr)

        val btnCompleted =
            view.findViewById<Button>(
                R.id.btnMarkBookingCompleted
            )

        val btnCall =
            view.findViewById<Button>(
                R.id.btnCallBookingCustomer
            )

        val tvStatus =
            view.findViewById<TextView>(R.id.tvBookingStatus)

        val tvPhone =
            view.findViewById<TextView>(
                R.id.tvBookingCustomerPhone
            )

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnScanQr.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect your QR scanner here",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnCompleted.setOnClickListener {
            if (tvStatus.text.toString() == "COMPLETED") {
                Toast.makeText(
                    requireContext(),
                    "Booking is already completed",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                tvStatus.text = "COMPLETED"

                Toast.makeText(
                    requireContext(),
                    "Booking marked as completed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnCall.setOnClickListener {
            val phone = tvPhone.text.toString()
                .trim()
                .replace(" ", "")

            if (phone.isNotEmpty()) {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$phone")
                )

                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Customer phone number unavailable",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
