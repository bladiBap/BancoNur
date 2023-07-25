package com.example.proyectofinalmoviles.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRepository {
    companion object {
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://apibancomoviles1.jmacboy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}