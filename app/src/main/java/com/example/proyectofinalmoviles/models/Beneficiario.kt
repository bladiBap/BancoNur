package com.example.proyectofinalmoviles.models

data class Beneficiario(
    val numeroCuenta : String,
    val ci : String,
    val nombreCompleto : String
)

data class BeneficiarioRespond(
    val id : Int,
    val numeroCuenta : String,
    val ci : String,
    val nombreCompleto : String,
    val userId : Int,
    val created_at : String,
    val updated_at : String
)