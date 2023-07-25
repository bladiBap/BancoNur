package com.example.proyectofinalmoviles.api

import com.example.proyectofinalmoviles.models.IngresoEgreso
import com.example.proyectofinalmoviles.models.RespondIngresoEgreso
import com.example.proyectofinalmoviles.models.ResponseGeneral
import com.example.proyectofinalmoviles.models.Transferencia
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TransferenciaService {

    @POST("/api/accounts/transfer")
    fun insertTransferencia(@Body body : Transferencia, @Header("Authorization") header : String): Call<ResponseGeneral>
}