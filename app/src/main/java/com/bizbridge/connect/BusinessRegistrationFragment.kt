package com.bizbridge.connect

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class BusinessRegistrationFragment :
   Fragment(R.layout.fragment_business_registration) {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val etOwnerName =
            view.findViewById<EditText>(R.id.etOwnerName)

        val etBusinessName =
            view.findViewById<EditText>(R.id.etBusinessName)

        val spCategory =
            view.findViewById<Spinner>(R.id.spBusinessCategory)

        val etEmail =
            view.findViewById<EditText>(R.id.etBusinessEmail)

        val etPhone =
            view.findViewById<EditText>(R.id.etBusinessPhone)

        val etAddress =
            view.findViewById<EditText>(R.id.etBusinessAddress)

        val etPassword =
            view.findViewById<EditText>(R.id.etBusinessPassword)

        val etConfirmPassword =
            view.findViewById<EditText>(
                R.id.etConfirmBusinessPassword
            )

        val accountType =
            view.findViewById<RadioGroup>(R.id.rgAccountType)

        val uploadImage =
            view.findViewById<TextView>(
                R.id.tvUploadBusinessImage
            )

        val btnRegister =
            view.findViewById<Button>(R.id.btnRegisterBusiness)

        // Business categories
        val categories = listOf(
            "Select category",
            "Beauty",
            "Education",
            "Food",
            "Fitness",
            "Repair",
            "Health",
            "Other"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spCategory.adapter = adapter

        // Image upload placeholder
        uploadImage.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Connect an image picker to upload a business image",
                Toast.LENGTH_SHORT
            ).show()

            // TODO: Add Android image picker.
        }

        // Register business
        btnRegister.setOnClickListener {

            val ownerName =
                etOwnerName.text.toString().trim()

            val businessName =
                etBusinessName.text.toString().trim()

            val email =
                etEmail.text.toString().trim()

            val phone =
                etPhone.text.toString().trim()

            val address =
                etAddress.text.toString().trim()

            val password =
                etPassword.text.toString()

            val confirmPassword =
                etConfirmPassword.text.toString()

            val category =
                spCategory.selectedItem.toString()

            // Owner name validation
            if (ownerName.isEmpty()) {
                etOwnerName.error = "Enter owner name"
                etOwnerName.requestFocus()
                return@setOnClickListener
            }

            // Business name validation
            if (businessName.isEmpty()) {
                etBusinessName.error = "Enter business name"
                etBusinessName.requestFocus()
                return@setOnClickListener
            }

            // Category validation
            if (category == "Select category") {
                Toast.makeText(
                    requireContext(),
                    "Please select a business category",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Email validation
            if (email.isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            ) {
                etEmail.error = "Enter a valid email address"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            // Phone validation
            if (phone.isEmpty() || phone.length < 9) {
                etPhone.error = "Enter a valid phone number"
                etPhone.requestFocus()
                return@setOnClickListener
            }

            // Address validation
            if (address.isEmpty()) {
                etAddress.error = "Enter business address"
                etAddress.requestFocus()
                return@setOnClickListener
            }

            // Password validation
            if (password.length < 6) {
                etPassword.error =
                    "Password must contain at least 6 characters"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword != password) {
                etConfirmPassword.error =
                    "Passwords do not match"
                etConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            // Account type
            val selectedAccount = when (
                accountType.checkedRadioButtonId
            ) {
                R.id.rbCustomer -> "Customer"
                else -> "Business Owner"
            }

            // Demo only: registration is validated, not saved.
            Toast.makeText(
                requireContext(),
                "Registration details validated. " +
                        "Account: $selectedAccount\n" +
                        "Business: $businessName\n" +
                        "Category: $category\n" +
                        "Administrator approval is required.",
                Toast.LENGTH_LONG
            ).show()

            // TODO: Send registration data to your database/API.
            // Never store a plain-text password in the database.
            // TODO: Show the registration success screen after saving.
        }
    }

}