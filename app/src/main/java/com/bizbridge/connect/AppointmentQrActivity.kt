package com.bizbridge.connect

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.util.Locale

class AppointmentQrActivity : AppCompatActivity() {

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_qr)

        findViewById<TextView>(R.id.btnQrBack).setOnClickListener {
            finish()
        }

        val bookingId = intent.getStringExtra("bookingId").orEmpty()
        if (bookingId.isBlank() || auth.currentUser == null) {
            finish()
            return
        }

        db.collection("bookings").document(bookingId).get()
            .addOnSuccessListener { doc ->
                if (!doc.exists() ||
                    doc.getString("customerId") != auth.currentUser?.uid
                ) {
                    Toast.makeText(this, "Booking not found.", Toast.LENGTH_LONG).show()
                    finish()
                    return@addOnSuccessListener
                }

                val status = doc.getString("status") ?: "PENDING"
                val business = doc.getString("businessName").orEmpty()
                val service = doc.getString("serviceName").orEmpty()
                val date = doc.getString("appointmentDate").orEmpty()
                val time = doc.getString("appointmentTime").orEmpty()
                val price = doc.getLong("servicePrice") ?: 0L
                val duration = doc.getLong("serviceDuration") ?: 0L

                findViewById<TextView>(R.id.tvQrStatus).text = status
                findViewById<TextView>(R.id.tvQrBusiness).text = business
                findViewById<TextView>(R.id.tvQrService).text =
                    "$service · $duration min"
                findViewById<TextView>(R.id.tvQrBookingId).text =
                    "Booking ID: ${bookingId.take(8).uppercase(Locale.ROOT)}"
                findViewById<TextView>(R.id.tvQrDate).text = "Date: $date"
                findViewById<TextView>(R.id.tvQrTime).text = "Time: $time"
                findViewById<TextView>(R.id.tvQrPrice).text =
                    "Price: Rs. ${String.format(Locale.US, "%,d", price)}"

                val qrPayload = """
                    BizBridge Appointment
                    Booking ID: $bookingId
                    Customer ID: ${auth.currentUser?.uid}
                    Business: $business
                    Service: $service
                    Date: $date
                    Time: $time
                    Status: $status
                """.trimIndent()

                try {
                    val matrix = MultiFormatWriter().encode(
                        qrPayload,
                        BarcodeFormat.QR_CODE,
                        600,
                        600
                    )
                    findViewById<ImageView>(R.id.ivAppointmentQr).setImageBitmap(
                        matrix.toBitmap()
                    )
                } catch (_: Exception) {
                    Toast.makeText(this, "Could not generate QR.", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Could not load booking.", Toast.LENGTH_LONG).show()
            }
    }

    private fun BitMatrix.toBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (get(x, y)) android.graphics.Color.BLACK
                    else android.graphics.Color.WHITE
                )
            }
        }
        return bitmap
    }
}
