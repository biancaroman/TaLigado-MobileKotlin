package com.example.taligado.model

data class Dispositivo(
    var id: String? = null,
    val nome: String = "",
    val status: String = "",
    val consumoEnergia: String = "",
    val imagemUrl: String,
)
