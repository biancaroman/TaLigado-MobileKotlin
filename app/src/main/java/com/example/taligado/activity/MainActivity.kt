package com.example.taligado.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import com.example.taligado.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)

    }
}
