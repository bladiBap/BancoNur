package com.example.proyectofinalmoviles.models

data class NewUser (
    val nombreCompleto: String,
    val email: String,
    val password: String,
    val ci : String,
    val fechaNacimiento: String
    )


data class UserSesion(
    val email: String,
    val password: String
)

data class ResponseUserSession(
    val access_token : String
)

data class ResponseGeneral(
    val message : String
)