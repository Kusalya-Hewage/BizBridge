package com.bizbridge.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class BusinessServicesFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_business_services,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val search = view.findViewById<EditText>(
            R.id.etSearchServices
        )

        search.addTextChangedListener(
            object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Search: ${s.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) {}
            }
        )

        view.findViewById<Button>(R.id.btnEditHaircut)
            .setOnClickListener {
                showMessage("Edit Haircut")
            }

        view.findViewById<Button>(R.id.btnDeleteHaircut)
            .setOnClickListener {
                confirmDelete("Haircut")
            }

        view.findViewById<Button>(R.id.btnEditHairWash)
            .setOnClickListener {
                showMessage("Edit Hair wash & blow dry")
            }

        view.findViewById<Button>(R.id.btnDeleteHairWash)
            .setOnClickListener {
                confirmDelete("Hair wash & blow dry")
            }

        view.findViewById<Button>(R.id.btnEditHairTreatment)
            .setOnClickListener {
                showMessage("Edit Hair treatment")
            }

        view.findViewById<Button>(R.id.btnDeleteHairTreatment)
            .setOnClickListener {
                confirmDelete("Hair treatment")
            }

        view.findViewById<Button>(R.id.btnAddService)
            .setOnClickListener {
                showMessage("Add Service screen")
            }

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
    }

    private fun confirmDelete(serviceName: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Service")
            .setMessage("Do you want to delete $serviceName?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                showMessage("$serviceName selected for deletion")
            }
            .show()
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
