package com.example.proyectofinalmoviles.api

import android.location.Location
import com.example.proyectofinalmoviles.models.BeneficiarioRespond
import com.example.proyectofinalmoviles.models.Ubicacion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface LocationService {

    @GET("/api/branches")
    fun getLocations(@Header("Authorization") header : String): Call<List<Ubicacion>>



}