package com.example.proyectofinalmoviles.repository

import com.example.proyectofinalmoviles.api.MovimientoService
import com.example.proyectofinalmoviles.api.QrService
import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.CobroQr
import com.example.proyectofinalmoviles.models.PagoQr
import com.example.proyectofinalmoviles.models.RespondCobroQr
import com.example.proyectofinalmoviles.models.ResponseGeneral
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object QrRepository {

    fun fetchCobroQr(
        token: String,
        cobro: CobroQr,
        success: (respond: RespondCobroQr) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val qrService = retrofit.create(QrService::class.java)
        qrService.generateCobroQr(cobro, token).enqueue(object : Callback<RespondCobroQr> {
            override fun onResponse(
                call: Call<RespondCobroQr>,
                response: Response<RespondCobroQr>
            ) {
                if (response.isSuccessful) {
                    val cobroQr = response.body()
                    cobroQr?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<RespondCobroQr>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchPagarQr(
        token: String,
        pagoqr: PagoQr,
        success: (respond: ResponseGeneral) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val qrService = retrofit.create(QrService::class.java)
        qrService.payQr(pagoqr, token).enqueue(object : Callback<ResponseGeneral> {
            override fun onResponse(
                call: Call<ResponseGeneral>,
                response: Response<ResponseGeneral>
            ) {
                if (response.isSuccessful) {
                    val pagoqr = response.body()
                    pagoqr?.let {
                        print(it)
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