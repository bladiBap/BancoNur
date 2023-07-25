package com.example.proyectofinalmoviles.repository

import com.example.proyectofinalmoviles.api.TransferenciaService
import com.example.proyectofinalmoviles.models.ResponseGeneral
import com.example.proyectofinalmoviles.models.Transferencia
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TranferenciaRepository {


    fun fetchInsertTransferencia(
        token : String,
        transferenia : Transferencia,
        success: (response: ResponseGeneral) -> Unit,
        failure: (throwable: Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val transferenciaService = retrofit.create(TransferenciaService::class.java)
        transferenciaService.insertTransferencia(transferenia, token).enqueue(object : Callback<ResponseGeneral> {
            override fun onResponse(
                call: Call<ResponseGeneral>,
                response: Response<ResponseGeneral>
            ) {
                if (response.isSuccessful) {
                    val responseTransferencia = response.body()
                    responseTransferencia?.let {
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