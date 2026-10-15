package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class BusinessServicesFragment : Fragment(R.layout.fragment_business_services){

    private data class ServiceItem(
        val cardId: Int,
        val statusId: Int,
        val name: String,
        val editId: Int,
        val deleteId: Int,
        var available: Boolean
    )

    private val services = listOf(
        ServiceItem(
            R.id.cardHaircut,
            R.id.statusHaircut,
            "Haircut",
            R.id.btnEditHaircut,
            R.id.btnDeleteHaircut,
            true
        ),
        ServiceItem(
            R.id.cardHairWash,
            R.id.statusHairWash,
            "Hair wash & blow dry",
            R.id.btnEditHairWash,
            R.id.btnDeleteHairWash,
            true
        ),
        ServiceItem(
            R.id.cardHairTreatment,
            R.id.statusHairTreatment,
            "Hair treatment",
            R.id.btnEditHairTreatment,
            R.id.btnDeleteHairTreatment,
            false
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val search = view.findViewById<EditText>(
            R.id.etSearchServices
        )

        val count = view.findViewById<TextView>(
            R.id.tvServiceCount
        )

        fun updateCount() {
            val visibleServices = services.count {
                view.findViewById<View>(it.cardId).visibility == View.VISIBLE
            }

            val availableServices = services.count {
                it.available &&
                        view.findViewById<View>(it.cardId).visibility == View.VISIBLE
            }

            count.text =
                "$visibleServices services · $availableServices available"
        }

        fun updateStatus(item: ServiceItem) {
            val status = view.findViewById<TextView>(item.statusId)

            status.text = if (item.available) "AVAILABLE" else "PAUSED"
            status.setTextColor(
                android.graphics.Color.parseColor(
                    if (item.available) "#008568" else "#B7791F"
                )
            )

            status.setOnClickListener {
                item.available = !item.available
                updateStatus(item)
                updateCount()
            }
        }

        services.forEach { item ->
            updateStatus(item)

            view.findViewById<TextView>(item.editId).setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Edit ${item.name}",
                    Toast.LENGTH_SHORT
                ).show()

                // TODO: Open the edit service screen.
            }

            view.findViewById<TextView>(item.deleteId).setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete service")
                    .setMessage("Do you want to delete ${item.name}?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete") { _, _ ->
                        view.findViewById<View>(item.cardId).visibility =
                            View.GONE
                        updateCount()

                        Toast.makeText(
                            requireContext(),
                            "${item.name} removed from this screen",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
        }

        search.addTextChangedListener(
            object : android.text.TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    countChanged: Int
                ) {
                    val query = s.toString().trim()

                    services.forEach { item ->
                        val card = view.findViewById<View>(item.cardId)

                        val matches = item.name.contains(
                            query,
                            ignoreCase = true
                        )

                        if (card.visibility != View.GONE ||
                            matches
                        ) {
                            card.visibility =
                                if (matches) View.VISIBLE else View.GONE
                        }
                    }

                    updateCount()
                }

                override fun afterTextChanged(s: android.text.Editable?) =
                    Unit
            }
        )

        view.findViewById<TextView>(R.id.btnAddService)
            .setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Add Service clicked",
                    Toast.LENGTH_SHORT
                ).show()

                // TODO: Open Add Service form.
            }

        view.findViewById<LinearLayout>(R.id.navDashboard)
            .setOnClickListener {
                showMessage("Dashboard selected")
                // TODO: Navigate to dashboard.
            }

        view.findViewById<LinearLayout>(R.id.navBookings)
            .setOnClickListener {
                showMessage("Bookings selected")
                // TODO: Navigate to bookings.
            }

        view.findViewById<LinearLayout>(R.id.navServices)
            .setOnClickListener {
                showMessage("Services selected")
            }

        view.findViewById<LinearLayout>(R.id.navPromotions)
            .setOnClickListener {
                showMessage("Promotions selected")
                // TODO: Navigate to promotions.
            }

        view.findViewById<LinearLayout>(R.id.navProfile)
            .setOnClickListener {
                showMessage("Profile selected")
                // TODO: Navigate to profile.
            }

        updateCount()
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
