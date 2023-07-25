package com.example.proyectofinalmoviles.api

import com.example.proyectofinalmoviles.models.CobroQr
import com.example.proyectofinalmoviles.models.IngresoEgreso
import com.example.proyectofinalmoviles.models.PagoQr
import com.example.proyectofinalmoviles.models.RespondCobroQr
import com.example.proyectofinalmoviles.models.RespondIngresoEgreso
import com.example.proyectofinalmoviles.models.ResponseGeneral
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface QrService {

    @POST("/api/qr/generate")
    fun generateCobroQr(@Body body : CobroQr, @Header("Authorization") header : String): Call<RespondCobroQr>

    @POST("/api/qr/pay")
    fun payQr(@Body body : PagoQr, @Header("Authorization") header : String): Call<ResponseGeneral>
}