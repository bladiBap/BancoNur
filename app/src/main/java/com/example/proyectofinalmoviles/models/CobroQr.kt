package com.example.proyectofinalmoviles.models

data class CobroQr (
    val monto: String,
    val cuentaDestino: Int,
    val fechaLimite: String
        )


data class RespondCobroQr(
    val codigo : String,
    val monto : String,
    val cuentaDestino : Int,
    val fechaLimite : String,
    val created_at : String,
    val id : Int,
    val imagen : String
)
