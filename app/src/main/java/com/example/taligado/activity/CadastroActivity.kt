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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taligado.R
import com.example.taligado.model.Usuario
import com.example.taligado.viewModel.CadastroCallback
import com.example.taligado.viewModel.UsuarioViewModel

class CadastroActivity : AppCompatActivity() {

    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var nomeEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var cnpjEditText: EditText
    private lateinit var btnInscrever: Button

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

        nomeEditText = findViewById(R.id.edtNome)
        usuarioEditText = findViewById(R.id.edtUsuario)
        emailEditText = findViewById(R.id.edtEmail)
        senhaEditText = findViewById(R.id.edtSenha)
        cnpjEditText = findViewById(R.id.edtCnpj)
        btnInscrever = findViewById(R.id.btnIncrever)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnInscrever.setOnClickListener {
            // Testa a conexão antes de tentar o cadastro
            testarConexao { isConnected ->
                if (isConnected) {
                    cadastrarUsuario()
                } else {
                    // Se não houver conexão, notifica o usuário
                    Toast.makeText(this, "Sem conexão com a internet.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cadastrarUsuario() {
        val nome = nomeEditText.text.toString().trim()
        val usuarioInput = usuarioEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val senha = senhaEditText.text.toString().trim()
        val cnpj = cnpjEditText.text.toString().trim()

        if (nome.isEmpty() || usuarioInput.isEmpty() || email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            highlightEmptyField(nome, usuarioInput, email, senha, cnpj)
            return
        }

        val usuario = Usuario(id = "", nome = nome, email = email, senha = senha)

        usuarioViewModel.cadastrarUsuario(usuario, object : CadastroCallback {
            override fun onSuccess() {
                Toast.makeText(this@CadastroActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@CadastroActivity, LoginActivity::class.java))
                finish()
            }

            override fun onFailure() {
                Toast.makeText(this@CadastroActivity, "Falha ao cadastrar o usuário. Tente novamente.", Toast.LENGTH_SHORT).show()
                Log.e("CadastroActivity", "Erro ao cadastrar usuário no Firebase.")
            }
        })
    }

    private fun highlightEmptyField(nome: String, usuario: String, email: String, senha: String, cnpj: String) {
        if (nome.isEmpty()) nomeEditText.requestFocus()
        else if (usuario.isEmpty()) usuarioEditText.requestFocus()
        else if (email.isEmpty()) emailEditText.requestFocus()
        else if (senha.isEmpty()) senhaEditText.requestFocus()
        else if (cnpj.isEmpty()) cnpjEditText.requestFocus()
    }

    private fun testarConexao(callback: (Boolean) -> Unit) {
        val isConnected = true // Implemente sua lógica de verificação de conexão
        callback(isConnected)
    }
}
