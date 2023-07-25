package com.example.proyectofinalmoviles.models

//"descripcion": "te paso el cambio",
//"monto": 5,
//"cuentaOrigen": "130",
//"beneficiario": 49

data class Transferencia(
    val descripcion: String,
    val monto: Int,
    val cuentaOrigen: String,
    val beneficiario: Int
)