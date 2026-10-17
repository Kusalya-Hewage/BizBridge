package com.example.bizbridge.admin

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bizbridge.connect.R


class AdminManagementActivity : AppCompatActivity() {

    private lateinit var btnLoadMore: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_management)

        btnLoadMore = findViewById(R.id.btnLoadMore)

        btnLoadMore.setOnClickListener {
            Toast.makeText(this, "Loading more users...", Toast.LENGTH_SHORT).show()
            // Add your pagination / load more logic here
        }
    }
}