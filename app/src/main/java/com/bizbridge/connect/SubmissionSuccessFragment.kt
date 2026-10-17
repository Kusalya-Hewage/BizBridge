package com.bizbridge.connect

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SubmissionSuccessFragment :
   Fragment(R.layout.fragment_registration_success){

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val btnBackToLogin =
            view.findViewById<Button>(R.id.btnBackToLogin)

        val btnClose =
            view.findViewById<TextView>(R.id.btnClose)

        // Close the success screen
        btnClose.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Success screen closed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Navigate back to the Login screen
        btnBackToLogin.setOnClickListener {
            parentFragmentManager.popBackStack()

            Toast.makeText(
                requireContext(),
                "Return to Login screen",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
