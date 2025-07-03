package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.R
import com.example.minimarketplace.data.DataManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var fabBack: com.google.android.material.floatingactionbutton.FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // No ActionBar setup needed - using NoActionBar theme
        initViews()
        setupProfile()
        setupClickListeners()
    }

    private fun initViews() {
        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        fabBack = findViewById(R.id.fabBack)
    }

    private fun setupProfile() {
        val username = DataManager.getUserSession(this)
        tvUsername.text = username ?: "User"
        tvEmail.text = "${username}@minimarketplace.com"
    }

    private fun setupClickListeners() {
        // Order History Card Click
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardOrderHistory).setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }

        // Logout Card Click
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.cardLogout).setOnClickListener {
            showLogoutDialog()
        }

        // Back button click
        fabBack.setOnClickListener {
            finish()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun performLogout() {
        DataManager.clearUserSession(this)
        DataManager.clearCart() // Clear cart on logout

        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
