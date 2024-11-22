package com.example.taligado.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.taligado.model.Dispositivo
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class DispositivosViewModel : ViewModel() {

    private val baseURL = "https://taligado-mobile-default-rtdb.firebaseio.com/dispositivos"
    private val client = OkHttpClient()
    private val gson = Gson()
    private val handler = Handler(Looper.getMainLooper())

    // Função para adicionar um dispositivo
    fun adicionarDispositivo(dispositivo: Dispositivo, callback: (Boolean) -> Unit) {
        val jsonDispositivo = gson.toJson(dispositivo)

        val requestBody = jsonDispositivo.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("$baseURL.json")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    callback(false)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
                    }
                }
            }
        })
    }

    // Função para listar os dispositivos do Firebase
    fun listarDispositivos(callback: (List<Dispositivo>) -> Unit) {
        val request = Request.Builder()
            .url("$baseURL.json")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    callback(emptyList())
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val dispositivos = mutableListOf<Dispositivo>()
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    val dispositivosMap = gson.fromJson(jsonResponse, Map::class.java)

                    dispositivosMap?.forEach { (key, value) ->
                        val dispositivo = gson.fromJson(gson.toJson(value), Dispositivo::class.java)
                        dispositivo.id = key.toString()
                        dispositivos.add(dispositivo)
                    }

                    handler.post {
                        callback(dispositivos)
                    }
                } else {
                    handler.post {
                        callback(emptyList())
                    }
                }
            }
        })
    }

    // Função para atualizar um dispositivo
    fun atualizarDispositivos(id: String, dispositivo: Dispositivo, callback: (Boolean) -> Unit) {
        val jsonDispositivo = gson.toJson(dispositivo)
        val requestBody = jsonDispositivo.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("$baseURL/$id.json")
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    callback(false)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
                    }
                }
            }
        })
    }

    // Função para deletar um dispositivo
    fun deletarDispositivo(id: String, callback: (Boolean) -> Unit) {
        val request = Request.Builder()
            .url("$baseURL/$id.json")
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    callback(false)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    handler.post {
                        callback(true)
                    }
                } else {
                    handler.post {
                        callback(false)
                    }
                }
            }
        })
    }
}

