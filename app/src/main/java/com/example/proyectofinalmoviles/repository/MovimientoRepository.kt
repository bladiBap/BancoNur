package com.example.proyectofinalmoviles.repository

import com.example.proyectofinalmoviles.api.MovimientoService
import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.Cuenta
import com.example.proyectofinalmoviles.models.IngresoEgreso
import com.example.proyectofinalmoviles.models.RespondIngresoEgreso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovimientoRepository {

    fun fetchListCards(token: String,success:(List<Card>) -> Unit, failure:(Throwable) -> Unit ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val movimientoService = retrofit.create(MovimientoService::class.java)
        movimientoService.getListCards(token).enqueue(object : Callback<List<Card>> {
            override fun onResponse(
                call: Call<List<Card>>,
                response: Response<List<Card>>
            ) {
                if (response.isSuccessful) {
                    val listaCuentas = response.body()
                    listaCuentas?.let {
                        print(it)
                        success(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Card>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchListMovimientos(token : String, id: Int, success:(List<Cuenta>) -> Unit, failure:(Throwable) -> Unit ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val movimientoService = retrofit.create(MovimientoService::class.java)
        movimientoService.getListMovimientos(token, id).enqueue(object : Callback<List<Cuenta>> {
            override fun onResponse(
                call: Call<List<Cuenta>>,
                response: Response<List<Cuenta>>
            ) {
                if (response.isSuccessful) {
                    val listaCuentas = response.body()
                    listaCuentas?.let {
                        print(it)
                        success(it)
                    }
                }
            }
            override fun onFailure(call: Call<List<Cuenta>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchAddAccount (token : String, success:(Card) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val movimientoService = retrofit.create(MovimientoService::class.java)
        movimientoService.addAccount(token).enqueue(object : Callback<Card> {
            override fun onResponse(
                call: Call<Card>,
                response: Response<Card>
            ) {
                if (response.isSuccessful) {
                    val cuenta = response.body()
                    cuenta?.let {
                        print(it)
                        success(it)
                    }
                }
            }
            override fun onFailure(call: Call<Card>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchIngreso(token : String,id : Int,ingresoEgreso : IngresoEgreso, success:(RespondIngresoEgreso) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val movimientoService = retrofit.create(MovimientoService::class.java)
        movimientoService.ingreso(ingresoEgreso,token, id).enqueue(object : Callback<RespondIngresoEgreso> {
            override fun onResponse(
                call: Call<RespondIngresoEgreso>,
                response: Response<RespondIngresoEgreso>
            ) {
                if (response.isSuccessful) {
                    val cuenta = response.body()
                    cuenta?.let {
                        print(it)
                        success(it)
                    }
                }
            }
            override fun onFailure(call: Call<RespondIngresoEgreso>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchEgreso(token : String,id : Int,ingresoEgreso : IngresoEgreso, success:(RespondIngresoEgreso) -> Unit, failure:(Throwable) -> Unit){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val movimientoService = retrofit.create(MovimientoService::class.java)
        movimientoService.egreso(ingresoEgreso,token, id).enqueue(object : Callback<RespondIngresoEgreso> {
            override fun onResponse(
                call: Call<RespondIngresoEgreso>,
                response: Response<RespondIngresoEgreso>
            ) {
                if (response.isSuccessful) {
                    val cuenta = response.body()
                    cuenta?.let {
                        print(it)
                        success(it)
                    }
                }
            }
            override fun onFailure(call: Call<RespondIngresoEgreso>, t: Throwable) {
                failure(t)
            }
        })
    }


}