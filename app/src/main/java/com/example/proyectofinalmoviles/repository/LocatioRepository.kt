package com.example.proyectofinalmoviles.repository

import android.location.Location
import com.example.proyectofinalmoviles.api.BeneficiarioService
import com.example.proyectofinalmoviles.api.LocationService
import com.example.proyectofinalmoviles.models.BeneficiarioRespond
import com.example.proyectofinalmoviles.models.Ubicacion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LocatioRepository {

    fun fetchgetListLocation(token: String, success:(List<Ubicacion>) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val locationService = retrofit.create(LocationService::class.java)
        locationService.getLocations(token).enqueue(object :
            Callback<List<Ubicacion>> {
            override fun onResponse(
                call: Call<List<Ubicacion>>,
                response: Response<List<Ubicacion>>
            ) {
                if (response.isSuccessful) {
                    val locations = response.body()
                    locations?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Ubicacion>>, t: Throwable) {
                failure(t)
            }
        })

    }
}