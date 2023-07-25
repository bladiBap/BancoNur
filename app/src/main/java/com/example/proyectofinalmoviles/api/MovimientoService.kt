package com.example.proyectofinalmoviles.api


import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.Cuenta
import com.example.proyectofinalmoviles.models.IngresoEgreso
import com.example.proyectofinalmoviles.models.RespondIngresoEgreso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface MovimientoService {

    @GET("/api/accounts")
    fun getListCards(@Header("Authorization") header : String): Call<List<Card>>

    @GET("/api/accounts/{id}/statement")
    fun getListMovimientos(@Header("Authorization") header : String, @Path("id") id: Int): Call<List<Cuenta>>

    @POST("/api/accounts")
    fun addAccount(@Header("Authorization") header : String): Call<Card>

    @POST("/api/accounts/{id}/income")
    fun ingreso(@Body body : IngresoEgreso, @Header("Authorization") header : String , @Path("id") id: Int): Call<RespondIngresoEgreso>

    @POST("/api/accounts/{id}/expense")
    fun egreso(@Body body : IngresoEgreso, @Header("Authorization") header : String , @Path("id") id: Int): Call<RespondIngresoEgreso>

}