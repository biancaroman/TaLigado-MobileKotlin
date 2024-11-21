package com.example.taligado.viewModel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.taligado.model.Filial
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class FilialViewModel : ViewModel() {

    private val baseURL = "https://taligado-mobile-default-rtdb.firebaseio.com/filiais"
    private val client = OkHttpClient()
    private val gson = Gson()
    private val handler = Handler(Looper.getMainLooper())

    // Função para adicionar uma filial
    fun adicionarFilial(filial: Filial, callback: (Boolean) -> Unit) {
        val jsonFilial = gson.toJson(filial)

        val requestBody = jsonFilial.toRequestBody("application/json".toMediaTypeOrNull())

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

    // Função para listar as filiais do Firebase
    fun listarFiliais(callback: (List<Filial>) -> Unit) {
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
                val filiais = mutableListOf<Filial>()
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    val filiaisMap = gson.fromJson(jsonResponse, Map::class.java)

                    filiaisMap?.forEach { (key, value) ->
                        val filial = gson.fromJson(gson.toJson(value), Filial::class.java)
                        filial.id = key.toString()
                        filiais.add(filial)
                    }

                    handler.post {
                        callback(filiais)
                    }
                } else {
                    handler.post {
                        callback(emptyList())
                    }
                }
            }
        })
    }

    // Função para atualizar uma filial
    fun atualizarFilial(id: String, filial: Filial, callback: (Boolean) -> Unit) {
        val jsonFilial = gson.toJson(filial)
        val requestBody = jsonFilial.toRequestBody("application/json".toMediaTypeOrNull())

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

    // Função para deletar uma filial
    fun deletarFilial(id: String, callback: (Boolean) -> Unit) {
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
