package com.example.taligado.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taligado.R
import com.example.taligado.viewModel.LoginCallback
import com.example.taligado.viewModel.UsuarioViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var usuarioViewModel: UsuarioViewModel

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

        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val txtEsqueceuSenha = findViewById<TextView>(R.id.txtEsqueceuSenha)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Método recomendado
        }

        txtEsqueceuSenha.setOnClickListener {
            // Adicione aqui a lógica para lidar com a recuperação de senha
        }

        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtSenha.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Testa a conexão antes de tentar o login
                testarConexao { isConnected ->
                    if (isConnected) {
                        // Se a conexão for bem-sucedida, realiza o login
                        usuarioViewModel.login(email, password, this, object : LoginCallback {
                            override fun onSuccess() {
                                runOnUiThread {
                                    Log.d("LoginActivity", "Login realizado com sucesso!")
                                    Toast.makeText(this@LoginActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                            override fun onFailure() {
                                runOnUiThread {
                                    Log.e("LoginActivity", "Falha no login. Credenciais inválidas.")
                                    Toast.makeText(this@LoginActivity, "Falha no login. Verifique suas credenciais.", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    } else {
                        // Se não houver conexão, notifica o usuário
                        runOnUiThread {
                            Log.e("LoginActivity", "Sem conexão com a internet.")
                            Toast.makeText(this@LoginActivity, "Sem conexão com a internet.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                runOnUiThread {
                    Log.d("LoginActivity", "Campos de email ou senha não preenchidos.")
                    Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun testarConexao(callback: (Boolean) -> Unit) {
        val isConnected = true // Implemente sua lógica de verificação de conexão
        callback(isConnected)
    }
}
