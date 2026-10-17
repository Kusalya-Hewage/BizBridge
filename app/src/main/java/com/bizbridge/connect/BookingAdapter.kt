package com.bizbridge.connect

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

data class Booking(
    val id: String,
    val businessName: String,
    val serviceName: String,
    val appointmentDate: String,
    val appointmentTime: String,
    val servicePrice: Long,
    val status: String,
    val serviceDuration: Long = 0L,
    val businessAddress: String = "",
    val bookingCode: String = ""
)

class BookingAdapter(
    private var bookings: List<Booking>,
    private val onViewClick: (Booking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val business: TextView = view.findViewById(R.id.tvItemBusiness)
        val service: TextView = view.findViewById(R.id.tvItemService)
        val dateTime: TextView = view.findViewById(R.id.tvItemDateTime)
        val price: TextView = view.findViewById(R.id.tvItemPrice)
        val status: TextView = view.findViewById(R.id.tvItemStatus)
        val viewButton: Button = view.findViewById(R.id.btnViewBooking)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)

        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]

        holder.business.text = booking.businessName

        // Display the actual booking code when available.
        // Otherwise use a shortened document ID.
        val displayedId = booking.bookingCode.ifBlank {
            "BB-${booking.id.takeLast(5).uppercase(Locale.ROOT)}"
        }

        holder.service.text = "${booking.serviceName} · $displayedId"
        holder.dateTime.text =
            "${formatDate(booking.appointmentDate)} · ${booking.appointmentTime}"

        holder.price.text =
            "Rs. ${String.format(Locale.US, "%,d", booking.servicePrice)}"

        holder.status.text = booking.status.replace('_', ' ')

        val statusColor = when (booking.status.uppercase(Locale.ROOT)) {
            "CONFIRMED" -> Color.rgb(39, 133, 108)
            "PENDING" -> Color.rgb(177, 124, 35)
            "CANCELLED" -> Color.rgb(190, 86, 96)
            "COMPLETED" -> Color.rgb(39, 133, 108)
            else -> Color.rgb(100, 116, 128)
        }

        holder.status.setTextColor(statusColor)

        holder.viewButton.setOnClickListener {
            onViewClick(booking)
        }
    }

    override fun getItemCount(): Int = bookings.size

    fun updateData(newBookings: List<Booking>) {
        bookings = newBookings
        notifyDataSetChanged()
    }

    private fun formatDate(date: String): String {
        return try {
            val input = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val output = SimpleDateFormat("dd MMMM yyyy", Locale.US)
            output.format(input.parse(date)!!)
        } catch (_: Exception) {
            date
        }
    }
}
