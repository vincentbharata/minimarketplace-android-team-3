package com.example.minimarketplace.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.minimarketplace.R
import com.example.minimarketplace.data.DataManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Hide action bar for splash screen
        supportActionBar?.hide()

        // Check for fresh app launch vs app restart
        val isAppRestart = intent.getBooleanExtra("APP_RESTART", false)

        // Delay for 2 seconds then check if user is logged in
        Handler(Looper.getMainLooper()).postDelayed({
            val username = DataManager.getUserSession(this)

            // If app is restarted from launcher, force login
            val intent = if (username != null && !isAppRestart) {
                Intent(this, HomeActivity::class.java)
            } else {
                // Clear session if app restarted
                if (isAppRestart) {
                    DataManager.clearUserSession(this)
                }
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }
}
