package com.example.taligado.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.taligado.R


class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnInscrever = findViewById<Button>(R.id.btnIncrever)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Método recomendado
        }


        btnInscrever.setOnClickListener {
            // Lógica de cadastro
        }
    }
}
