package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Redirect to SplashActivity since it's the entry point
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}
