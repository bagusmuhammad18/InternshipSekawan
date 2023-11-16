package com.example.storyapp.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.storyapp.R
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.welcome.WelcomeActivity

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 1000 // Waktu penundaan dalam milidetik (3 detik)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            if (PreferenceManager.isLoggedIn(this)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIME_OUT)
    }


}