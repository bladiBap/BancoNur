package com.example.proyectofinalmoviles.api

import com.example.proyectofinalmoviles.models.NewUser
import com.example.proyectofinalmoviles.models.UserSesion
import com.example.proyectofinalmoviles.models.ResponseGeneral
import com.example.proyectofinalmoviles.models.ResponseUserSession
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AutenticacionService {



    @POST("/api/login")
    fun loginUsuario(@Body body: UserSesion): Call<ResponseUserSession>

    @POST("/api/register")
    fun insertUsuario(@Body body: NewUser): Call<ResponseGeneral>
}