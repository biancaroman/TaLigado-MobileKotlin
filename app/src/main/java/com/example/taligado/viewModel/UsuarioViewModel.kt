package com.example.taligado.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.taligado.model.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UsuarioViewModel : ViewModel() {
    private val baseURL = "https://taligado-mobile-default-rtdb.firebaseio.com/"
    private val client = OkHttpClient()
    private val gson = Gson()
    private val handler = Handler(Looper.getMainLooper())

    fun login(email: String, password: String, context: Context, callback: LoginCallback) {
        val request = Request.Builder()
            .url("$baseURL/usuarios.json")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("UsuarioViewModel", "Falha na requisição de login: ${e.message}", e)
                handler.post { callback.onFailure() }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.d("UsuarioViewModel", "Resposta do servidor: $body")

                try {
                    if (!response.isSuccessful) {
                        Log.e("UsuarioViewModel", "Código de resposta inesperado: ${response.code}")
                        handler.post { callback.onFailure() }
                        return
                    }

                    val typeToken = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
                    val usuariosMap: Map<String, Map<String, Any>> = gson.fromJson(body, typeToken)

                    val usuario = usuariosMap.values.find {
                        it["email"] == email && it["senha"] == password
                    }

                    if (usuario != null) {
                        Log.d("UsuarioViewModel", "Usuário encontrado: $usuario")
                        salvarSessaoUsuario(email, context)
                        handler.post { callback.onSuccess() }
                    } else {
                        Log.d("UsuarioViewModel", "Usuário não encontrado.")
                        handler.post { callback.onFailure() }
                    }
                } catch (e: Exception) {
                    Log.e("UsuarioViewModel", "Erro ao processar a resposta: ${e.message}", e)
                    handler.post { callback.onFailure() }
                }
            }
        })
    }

    fun cadastrarUsuario(usuario: Usuario, callback: CadastroCallback) {
        val usuarioComId = if (usuario.id.isEmpty()) {
            usuario.copy(id = gerarIdUnico())
        } else {
            usuario
        }

        val usuarioJson = gson.toJson(usuarioComId)
        val requestBody = usuarioJson.toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("$baseURL/usuarios/${usuarioComId.id}.json")
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("UsuarioViewModel", "Falha ao cadastrar usuário: ${e.message}", e)
                handler.post { callback.onFailure() }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("UsuarioViewModel", "Usuário cadastrado com sucesso.")
                    handler.post { callback.onSuccess() }
                } else {
                    Log.e(
                        "UsuarioViewModel",
                        "Falha ao cadastrar usuário. Código de resposta: ${response.code}, Mensagem: ${response.message}"
                    )
                    handler.post { callback.onFailure() }
                }
            }
        })
    }

    private fun gerarIdUnico(): String {
        return "user_${System.currentTimeMillis()}"
    }

    fun verificarSessaoUsuario(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return sharedPreferences.contains("user_email")
    }

    fun salvarSessaoUsuario(email: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("user_email", email).apply()
    }

    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("user_email").apply()
    }
}

interface LoginCallback {
    fun onSuccess()
    fun onFailure()
}

interface CadastroCallback {
    fun onSuccess()
    fun onFailure()
}
