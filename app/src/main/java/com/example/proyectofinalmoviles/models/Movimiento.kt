package com.example.proyectofinalmoviles.models


//    "descripcion": "Ingreso por Pa tu chicle",
//    "monto": 200,
//    "tipo": 1,
//    "cuentaOrigen": "131",
//    "cuentaDestino": null,
//    "created_at": "2023-07-06T02:04:07.000000Z",
//    "id": 539

data class Movimiento (
    val descripcion : String,
    val monto : Int,
    val tipo : Int,
    val cuentaOrigen : String,
    val cuentaDestino : String,
    val created_at : String,
    val id : Int
        )