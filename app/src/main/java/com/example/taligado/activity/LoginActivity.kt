package com.example.taligado.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.taligado.R


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val txtEsqueceuSenha = findViewById<TextView>(R.id.txtEsqueceuSenha)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Método recomendado
        }

        txtEsqueceuSenha.setOnClickListener {
            // Adicione aqui a lógica para lidar com a recuperação de senha
        }

        btnEntrar.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}
