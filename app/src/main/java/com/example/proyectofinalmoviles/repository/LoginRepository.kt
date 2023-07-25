package com.example.proyectofinalmoviles.repository

import com.example.proyectofinalmoviles.api.AutenticacionService
import com.example.proyectofinalmoviles.models.NewUser
import com.example.proyectofinalmoviles.models.ResponseGeneral
import com.example.proyectofinalmoviles.models.UserSesion
import com.example.proyectofinalmoviles.models.ResponseUserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {


    fun fetchUserSesion(user: UserSesion, success:(ResponseUserSession) -> Unit, failure:(Throwable) -> Unit ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val autenticationService = retrofit.create(AutenticacionService::class.java)
        autenticationService.loginUsuario(user).enqueue(object : Callback<ResponseUserSession> {
            override fun onResponse(
                call: Call<ResponseUserSession>,
                response: Response<ResponseUserSession>
            ) {
                if (response.isSuccessful) {

                    val accsesToken = response.body()
                    accsesToken?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUserSession>, t: Throwable) {
                failure(t)
            }
        })
    }

     fun fetchNewUser (newUser: NewUser, success: (ResponseGeneral) -> Unit, failure: (Throwable) -> Unit){
         val retrofit = RetrofitRepository.getRetrofitInstance()
         val autenticationService = retrofit.create(AutenticacionService::class.java)
         autenticationService.insertUsuario(newUser).enqueue(object : Callback<ResponseGeneral> {
             override fun onResponse(
                 call: Call<ResponseGeneral>,
                 response: Response<ResponseGeneral>
             ) {
                 if (response.isSuccessful) {
                     val message = response.body()
                     message?.let {
                         success(it)
                     }
                 }
             }

             override fun onFailure(call: Call<ResponseGeneral>, t: Throwable) {
                 failure(t)
             }
         })
     }



}
