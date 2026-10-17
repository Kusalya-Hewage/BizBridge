package com.bizbridge.connect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BusinessSearchActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var tvBusinessCount: TextView
    private lateinit var tvNoResults: TextView

    private lateinit var cardBusiness1: LinearLayout
    private lateinit var cardBusiness2: LinearLayout
    private lateinit var cardBusiness3: LinearLayout

    private var selectedCategory = "All"

    data class Business(
        val name: String,
        val categoryText: String,
        val details: String,
        val categories: List<String>
    )

    // Demo businesses for testing
    private val businesses = listOf(
        Business(
            name = "Glow Beauty Salon",
            categoryText = "Salon · Beauty",
            details = "★ 4.8 · Negombo",
            categories = listOf("Salon", "Beauty")
        ),
        Business(
            name = "FitLife Fitness Center",
            categoryText = "Fitness",
            details = "★ 4.6 · Negombo",
            categories = listOf("Fitness")
        ),
        Business(
            name = "SmartFix Repair Center",
            categoryText = "Repair",
            details = "★ 4.5 · Negombo",
            categories = listOf("Repair")
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_search)

        // Read the category selected on the Dashboard
        selectedCategory =
            intent.getStringExtra("selectedCategory") ?: "All"

        // Connect XML views
        etSearch = findViewById(R.id.etBusinessSearch)
        tvBusinessCount = findViewById(R.id.tvBusinessCount)
        tvNoResults = findViewById(R.id.tvNoResults)

        cardBusiness1 = findViewById(R.id.cardBusiness1)
        cardBusiness2 = findViewById(R.id.cardBusiness2)
        cardBusiness3 = findViewById(R.id.cardBusiness3)

        setupSearch()
        setupCategoryFilters()
        setupBusinessCards()
        setupBottomNavigation()

        // Display matching businesses
        filterBusinesses()
    }

    // Search businesses by name, category or location
    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {

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
                count: Int
            ) {
                filterBusinesses()
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    // Handle category filter clicks
    private fun setupCategoryFilters() {

        val filters = linkedMapOf(
            R.id.filterAll to "All",
            R.id.filterSalon to "Salon",
            R.id.filterBeauty to "Beauty",
            R.id.filterFitness to "Fitness",
            R.id.filterRepair to "Repair",
            R.id.filterEducation to "Education"
        )

        filters.forEach { (id, category) ->

            findViewById<TextView>(id).setOnClickListener {

                selectedCategory = category

                updateCategoryAppearance(filters)

                filterBusinesses()
            }
        }

        // Highlight the category received from Dashboard
        updateCategoryAppearance(filters)
    }

    // Highlight the currently selected category
    private fun updateCategoryAppearance(
        filters: Map<Int, String>
    ) {
        filters.forEach { (id, category) ->

            val filterView = findViewById<TextView>(id)

            if (category.equals(
                    selectedCategory,
                    ignoreCase = true
                )
            ) {
                filterView.alpha = 1.0f
            } else {
                filterView.alpha = 0.6f
            }
        }
    }

    // Filter and display matching businesses
    private fun filterBusinesses() {

        val query = etSearch.text.toString().trim()

        val results = businesses.filter { business ->

            val matchesSearch =
                business.name.contains(query, ignoreCase = true) ||
                        business.categoryText.contains(query, ignoreCase = true) ||
                        business.details.contains(query, ignoreCase = true)

            val matchesCategory =
                selectedCategory.equals("All", ignoreCase = true) ||
                        business.categories.any { category ->
                            category.equals(
                                selectedCategory,
                                ignoreCase = true
                            )
                        }

            matchesSearch && matchesCategory
        }

        val cards = listOf(
            cardBusiness1,
            cardBusiness2,
            cardBusiness3
        )

        val nameIds = listOf(
            R.id.tvBusiness1Name,
            R.id.tvBusiness2Name,
            R.id.tvBusiness3Name
        )

        val categoryIds = listOf(
            R.id.tvBusiness1Category,
            R.id.tvBusiness2Category,
            R.id.tvBusiness3Category
        )

        val detailIds = listOf(
            R.id.tvBusiness1Details,
            R.id.tvBusiness2Details,
            R.id.tvBusiness3Details
        )

        cards.forEachIndexed { index, card ->

            if (index < results.size) {

                val business = results[index]

                card.findViewById<TextView>(
                    nameIds[index]
                ).text = business.name

                card.findViewById<TextView>(
                    categoryIds[index]
                ).text = business.categoryText

                card.findViewById<TextView>(
                    detailIds[index]
                ).text = business.details

                card.visibility = View.VISIBLE

            } else {
                card.visibility = View.GONE
            }
        }

        tvBusinessCount.text = "${results.size} results"

        tvNoResults.visibility =
            if (results.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    // Handle business card clicks
    private fun setupBusinessCards() {

        val cards = listOf(
            cardBusiness1,
            cardBusiness2,
            cardBusiness3
        )

        val nameIds = listOf(
            R.id.tvBusiness1Name,
            R.id.tvBusiness2Name,
            R.id.tvBusiness3Name
        )

        val categoryIds = listOf(
            R.id.tvBusiness1Category,
            R.id.tvBusiness2Category,
            R.id.tvBusiness3Category
        )

        val detailIds = listOf(
            R.id.tvBusiness1Details,
            R.id.tvBusiness2Details,
            R.id.tvBusiness3Details
        )

        cards.forEachIndexed { index, card ->

            card.setOnClickListener {

                val businessName =
                    card.findViewById<TextView>(
                        nameIds[index]
                    ).text.toString()

                val businessCategory =
                    card.findViewById<TextView>(
                        categoryIds[index]
                    ).text.toString()

                val details =
                    card.findViewById<TextView>(
                        detailIds[index]
                    ).text.toString()

                val rating = Regex("""\d+(\.\d+)?""")
                    .find(details)?.value ?: "0.0"

                val intent = Intent(
                    this,
                    BusinessDetailsActivity::class.java
                )

                intent.putExtra("businessName", businessName)
                intent.putExtra("businessCategory", businessCategory)
                intent.putExtra("businessRating", rating)
                intent.putExtra("reviewCount", "124")
                intent.putExtra(
                    "businessAddress",
                    "42 Galle Road, Colombo 03"
                )
                intent.putExtra("businessPhone", "0774567890")

                startActivity(intent)
            }
        }
    }

    // Bottom navigation
    private fun setupBottomNavigation() {

        findViewById<TextView>(R.id.navHome)
            .setOnClickListener {
                finish()
            }

        findViewById<TextView>(R.id.navSearch)
            .setOnClickListener {
                etSearch.requestFocus()
            }

        findViewById<TextView>(R.id.navBookings)
            .setOnClickListener {
                showComingSoon("My Bookings")
            }

        findViewById<TextView>(R.id.navNotifications)
            .setOnClickListener {
                showComingSoon("Notifications")
            }

        findViewById<TextView>(R.id.navProfile)
            .setOnClickListener {
                showComingSoon("Customer Profile")
            }
    }

    private fun showComingSoon(feature: String) {
        Toast.makeText(
            this,
            "$feature will be connected next.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
