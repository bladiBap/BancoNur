package com.example.proyectofinalmoviles.ui.menu.sucursales

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.Ubicacion
import com.example.proyectofinalmoviles.repository.LocatioRepository

class SucursalViewModel : ViewModel() {

    private val _listLocations = MutableLiveData<ArrayList<Ubicacion>>()
    val listLocations : LiveData<ArrayList<Ubicacion>> get() = _listLocations


    fun getLocations(token : String){
        LocatioRepository.fetchgetListLocation(token,
            {
                if (it.isEmpty()){
                    _listLocations.value = ArrayList<Ubicacion>()
                }else{
                    _listLocations.value = it as ArrayList<Ubicacion>
                }
            },{
                print(it)
            }
        )
    }
}