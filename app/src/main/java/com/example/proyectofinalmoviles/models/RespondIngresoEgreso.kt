package com.example.proyectofinalmoviles.models

data class RespondIngresoEgreso(
    val movimiento : Movimiento,
    val saldo : Double
)

data class IngresoEgreso(
    val descripcion: String,
    val monto: Int,
)