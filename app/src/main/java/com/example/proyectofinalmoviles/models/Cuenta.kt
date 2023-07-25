package com.example.proyectofinalmoviles.models

data class Cuenta(
    var id: Int,
    var descripcion: String,
    var monto: String,
    var tipo: Int,
    var cuentaOrigen: Int,
    var cuentaDestino: Int,
    var created_at: String
) : java.io.Serializable {
    override fun toString(): String {
        return "$descripcion"
    }
}
