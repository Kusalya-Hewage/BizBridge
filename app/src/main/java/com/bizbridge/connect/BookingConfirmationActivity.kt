package com.bizbridge.connect

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import java.util.EnumMap
import java.util.Locale

class BookingConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_confirmation)

        val bookingId = intent.getStringExtra("bookingId") ?: ""
        val businessName =
            intent.getStringExtra("businessName") ?: "Business"
        val serviceName =
            intent.getStringExtra("serviceName") ?: "Service"
        val appointmentDate =
            intent.getStringExtra("appointmentDate") ?: ""
        val appointmentTime =
            intent.getStringExtra("appointmentTime") ?: ""
        val servicePrice =
            intent.getIntExtra("servicePrice", 0)

        findViewById<TextView>(R.id.tvConfirmationBusiness).text =
            businessName

        findViewById<TextView>(R.id.tvConfirmationService).text =
            "Service: $serviceName"

        findViewById<TextView>(R.id.tvConfirmationDate).text =
            "Date: $appointmentDate"

        findViewById<TextView>(R.id.tvConfirmationTime).text =
            "Time: $appointmentTime"

        findViewById<TextView>(R.id.tvConfirmationPrice).text =
            "Price: Rs. ${String.format(Locale.US, "%,d", servicePrice)}"

        findViewById<TextView>(R.id.tvConfirmationStatus).text =
            "Status: PENDING"

        findViewById<TextView>(R.id.tvConfirmationBookingId).text =
            bookingId

        // Generate QR code using the saved booking ID.
        val qrContent = """
            BizBridge Appointment
            Booking ID: $bookingId
            Business: $businessName
            Service: $serviceName
            Date: $appointmentDate
            Time: $appointmentTime
        """.trimIndent()

        val qrImage = findViewById<ImageView>(R.id.ivBookingQr)

        try {
            val hints = EnumMap<EncodeHintType, Any>(
                EncodeHintType::class.java
            )
            hints[EncodeHintType.MARGIN] = 1

            val bitMatrix = QRCodeWriter().encode(
                qrContent,
                BarcodeFormat.QR_CODE,
                500,
                500,
                hints
            )

            val bitmap = Bitmap.createBitmap(
                500,
                500,
                Bitmap.Config.ARGB_8888
            )

            for (x in 0 until 500) {
                for (y in 0 until 500) {
                    bitmap.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) Color.BLACK
                        else Color.WHITE
                    )
                }
            }

            qrImage.setImageBitmap(bitmap)

        } catch (exception: Exception) {
            Toast.makeText(
                this,
                "Could not generate QR code.",
                Toast.LENGTH_LONG
            ).show()
        }

        findViewById<Button>(R.id.btnConfirmationHome)
            .setOnClickListener {
                val homeIntent = Intent(
                    this,
                    CustomerDashboardActivity::class.java
                )

                homeIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_SINGLE_TOP

                startActivity(homeIntent)
                finish()
            }
    }
}
