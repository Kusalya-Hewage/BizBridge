package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessProfileEditFragment:
   Fragment(R.layout.fragment_business_profile_edit){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val etBusinessName =
            view.findViewById<EditText>(
                R.id.etProfileBusinessName
            )

        val etCategory =
            view.findViewById<EditText>(
                R.id.etProfileCategory
            )

        val etAddress =
            view.findViewById<EditText>(
                R.id.etProfileAddress
            )

        val etPhone =
            view.findViewById<EditText>(
                R.id.etProfilePhone
            )

        val etOpeningHours =
            view.findViewById<EditText>(
                R.id.etProfileOpeningHours
            )

        val etDescription =
            view.findViewById<EditText>(
                R.id.etProfileDescription
            )

        val btnSave =
            view.findViewById<Button>(
                R.id.btnSaveProfileChanges
            )

        val btnCancel =
            view.findViewById<Button>(
                R.id.btnCancelProfileEdit
            )

        val btnChangeCover =
            view.findViewById<TextView>(
                R.id.btnChangeCover
            )

        val btnChangeLogo =
            view.findViewById<TextView>(
                R.id.btnChangeLogo
            )

        val btnClose =
            view.findViewById<TextView>(
                R.id.tvCloseProfileEdit
            )

        // Sample values from the reference design
        etBusinessName.setText("ABC Salon")
        etCategory.setText("Salon · Beauty")
        etAddress.setText("42 Galle Road, Colombo 03")
        etPhone.setText("077 456 7890")
        etOpeningHours.setText(
            "Mon–Sat 9 AM–7 PM; Sun 10 AM–4 PM"
        )
        etDescription.setText(
            "Your neighborhood salon for fresh cuts, " +
                    "thoughtful care and everyday confidence."
        )

        btnChangeCover.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect cover image picker here",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnChangeLogo.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect business logo picker here",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnSave.setOnClickListener {
            val businessName =
                etBusinessName.text.toString().trim()

            val category =
                etCategory.text.toString().trim()

            val address =
                etAddress.text.toString().trim()

            val phone =
                etPhone.text.toString().trim()

            val openingHours =
                etOpeningHours.text.toString().trim()

            val description =
                etDescription.text.toString().trim()

            if (
                businessName.isEmpty() ||
                category.isEmpty() ||
                address.isEmpty() ||
                phone.isEmpty() ||
                openingHours.isEmpty() ||
                description.isEmpty()
            ) {
                Toast.makeText(
                    requireContext(),
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Demo only: connect this to your database/API.
            Toast.makeText(
                requireContext(),
                "Business profile details validated",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}