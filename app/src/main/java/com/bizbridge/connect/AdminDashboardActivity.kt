package com.bizbridge.connect

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(0xFFF4F7FC.toInt())
        }

        val title = TextView(this).apply {
            text = "BizBridge Admin Dashboard"
            textSize = 24f
            setTextColor(0xFF087BC1.toInt())
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Welcome, Administrator!"
            textSize = 16f
            setTextColor(0xFF172D43.toInt())
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 0)
        }

        layout.addView(title)
        layout.addView(subtitle)

        setContentView(layout)
    }
}
