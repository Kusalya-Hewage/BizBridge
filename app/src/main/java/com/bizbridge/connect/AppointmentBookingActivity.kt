package com.bizbridge.connect

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AppointmentBookingActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var tvSelectedDate: TextView
    private lateinit var rgTimeSlots: RadioGroup
    private lateinit var etBookingNotes: EditText
    private lateinit var btnConfirmBooking: Button

    private var selectedDate = ""
    private var selectedTime = ""

    private var businessName = "ABC Salon"
    private var businessAddress = ""
    private var serviceName = "Haircut"
    private var servicePrice = 1500
    private var serviceDuration = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_booking)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Receive selected business and service
        businessName = intent.getStringExtra("businessName")
            ?: "ABC Salon"

        businessAddress = intent.getStringExtra("businessAddress")
            ?: ""

        serviceName = intent.getStringExtra("serviceName")
            ?: "Haircut"

        servicePrice = intent.getIntExtra("servicePrice", 1500)
        serviceDuration = intent.getIntExtra("serviceDuration", 30)

        // Connect views
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        rgTimeSlots = findViewById(R.id.rgTimeSlots)
        etBookingNotes = findViewById(R.id.etBookingNotes)
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking)

        findViewById<TextView>(R.id.tvBookingBusinessName).text =
            businessName

        findViewById<TextView>(R.id.tvBookingServiceName).text =
            serviceName

        findViewById<TextView>(R.id.tvBookingDuration).text =
            "$serviceDuration minutes"

        findViewById<TextView>(R.id.tvBookingPrice).text =
            "Rs. ${"%,d".format(Locale.US, servicePrice)}"

        // Back button
        findViewById<TextView>(R.id.btnBookingBack)
            .setOnClickListener {
                finish()
            }

        // Select appointment date
        findViewById<Button>(R.id.btnSelectDate)
            .setOnClickListener {
                showDatePicker()
            }

        // Confirm booking
        btnConfirmBooking.setOnClickListener {
            confirmBooking()
        }
    }

    private fun showDatePicker() {
        val today = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->

                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)

                val formatter = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

                selectedDate = formatter.format(calendar.time)

                tvSelectedDate.text = SimpleDateFormat(
                    "EEE, dd MMM yyyy",
                    Locale.getDefault()
                ).format(calendar.time)

            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.datePicker.minDate = today.timeInMillis
        datePicker.show()
    }

    private fun confirmBooking() {

        val currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(
                this,
                "Please log in before booking.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (selectedDate.isBlank()) {
            Toast.makeText(
                this,
                "Please select an appointment date.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val selectedRadioId = rgTimeSlots.checkedRadioButtonId

        if (selectedRadioId == -1) {
            Toast.makeText(
                this,
                "Please select an appointment time.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val selectedRadio = findViewById<android.widget.RadioButton>(
            selectedRadioId
        )
        selectedTime = selectedRadio.text.toString()

        val reviewIntent = android.content.Intent(
            this,
            ReviewBookingActivity::class.java
        ).apply {
            putExtra("businessName", businessName)
            putExtra("businessAddress", businessAddress)
            putExtra("serviceName", serviceName)
            putExtra("servicePrice", servicePrice)
            putExtra("serviceDuration", serviceDuration)
            putExtra("appointmentDate", selectedDate)
            putExtra("appointmentTime", selectedTime)
            putExtra(
                "notes",
                etBookingNotes.text.toString().trim()
            )
        }

        startActivity(reviewIntent)
    }
}