package com.example.proyectofinalmoviles.repository

import com.example.proyectofinalmoviles.api.BeneficiarioService
import com.example.proyectofinalmoviles.api.MovimientoService
import com.example.proyectofinalmoviles.models.Beneficiario
import com.example.proyectofinalmoviles.models.BeneficiarioRespond
import com.example.proyectofinalmoviles.models.Card
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BeneficiarioRepository {

    fun fetchListBeneficiario(token:String, success:(List<BeneficiarioRespond>) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val beneficiarioService = retrofit.create(BeneficiarioService::class.java)
        beneficiarioService.getListBneficiarios(token).enqueue(object : Callback<List<BeneficiarioRespond>> {
            override fun onResponse(
                call: Call<List<BeneficiarioRespond>>,
                response: Response<List<BeneficiarioRespond>>
            ) {
                if (response.isSuccessful) {
                    val listaBeneficiarios = response.body()
                    listaBeneficiarios?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<BeneficiarioRespond>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertarBeneficiario(token:String,beneficiario : Beneficiario, success:(BeneficiarioRespond) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val beneficiarioService = retrofit.create(BeneficiarioService::class.java)
        beneficiarioService.insertarBeneficiario(beneficiario,token).enqueue(object : Callback<BeneficiarioRespond> {
            override fun onResponse(
                call: Call<BeneficiarioRespond>,
                response: Response<BeneficiarioRespond>
            ) {
                if (response.isSuccessful) {
                    val beneficiario = response.body()
                    beneficiario?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<BeneficiarioRespond>, t: Throwable) {
                failure(t)
            }
        })
    }
}