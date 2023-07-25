package com.example.proyectofinalmoviles.api

import com.example.proyectofinalmoviles.models.Beneficiario
import com.example.proyectofinalmoviles.models.BeneficiarioRespond
import com.example.proyectofinalmoviles.models.RespondIngresoEgreso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BeneficiarioService {

    @GET("/api/recipients")
    fun getListBneficiarios(@Header("Authorization") header : String): Call<List<BeneficiarioRespond>>

    @POST("/api/recipients")
    fun insertarBeneficiario(@Body body : Beneficiario, @Header("Authorization") header : String): Call<BeneficiarioRespond>
}