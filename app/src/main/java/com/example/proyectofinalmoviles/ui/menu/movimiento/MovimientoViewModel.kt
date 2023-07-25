package com.example.proyectofinalmoviles.ui.menu.movimiento

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.Cuenta
import com.example.proyectofinalmoviles.models.IngresoEgreso
import com.example.proyectofinalmoviles.repository.MovimientoRepository

class MovimientoViewModel : ViewModel() {

    private val _listaCards = MutableLiveData<ArrayList<Card>>()
    val listaCards :LiveData<ArrayList<Card>> get() = _listaCards

    private val _listaMovimientos = MutableLiveData<ArrayList<Cuenta>>()
    val listaMovimientos :LiveData<ArrayList<Cuenta>> get() = _listaMovimientos

    private val _nroTarjeta = MutableLiveData<Int>(0)
    val nroTarjeta :LiveData<Int> get() = _nroTarjeta

    private val _showToast = MutableLiveData<Boolean>()
    val showToast :LiveData<Boolean> get() = _showToast


    fun getCards(token : String){
        MovimientoRepository.fetchListCards(token,
            {
                if (it.isEmpty()){
                    _listaCards.value = ArrayList<Card>()
                }else{
                    _listaCards.value = it as ArrayList<Card>
                    _nroTarjeta.value = _listaCards.value!![0].id
                    getMovimientos(token)
                }
            },{
                print(it)
            }
        )
    }

    fun getMovimientos(token : String){
        MovimientoRepository.fetchListMovimientos(token, _nroTarjeta.value!!,
            {
                _listaMovimientos.value = it as ArrayList<Cuenta>
            },{
                print(it)
            }
        )
    }

    fun setNroTarjeta(nroTarjeta : Int){
        _nroTarjeta.value = nroTarjeta
        Log.e("MOVIMIENTOVIEWMODEL", _nroTarjeta.value.toString())
    }

    fun addAccount(token : String){
        MovimientoRepository.fetchAddAccount(token,
            {
                getCards(token)
            },{
                print(it)
            }
        )
    }

    fun ingreso (token : String, id : Int, monto : String, descripcion : String){
        val ingreso = IngresoEgreso(descripcion, monto.toInt())
        MovimientoRepository.fetchIngreso(token, id, ingreso,
            {
                getMovimientos(token)
                getCards(token)

            },{
                print(it)
            }
        )
    }

    fun egreso (token : String, id : Int, monto : String, descripcion : String){
        val egreso = IngresoEgreso(descripcion, monto.toInt())
        MovimientoRepository.fetchEgreso(token, id, egreso,
            {
                getMovimientos(token)
                getCards(token)
            },{
                print(it)
            }
        )
    }



    fun verificarCampos (monto : String, descripcion : String, cuenta: String) : Boolean{
        if (monto.isNotEmpty() && descripcion.isNotEmpty() && cuenta.isNotEmpty()){
            return true
        }
        return false
    }



    fun insertMovimiento (token : String, monto : String, descripcion : String, cuenta: String, type : String){
        for (i in listaCards.value!!){
            if (i.id.toString() + " - " + i.saldo == cuenta){
                if (type == "ingreso"){
                    ingreso(token, i.id, monto, descripcion)
                }else{
                    if (i.saldo.toDouble() >= monto.toDouble()){
                        egreso(token, i.id, monto, descripcion)
                    }else{
                        _showToast.value = true
                    }
                }
            }
        }
    }
}