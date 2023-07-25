package com.example.proyectofinalmoviles.ui.viewsmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.UserSesion
import com.example.proyectofinalmoviles.repository.LoginRepository



class LoginViewModel : ViewModel(){

    private var _isValidate = MutableLiveData<Boolean>()
    val isValidate : LiveData<Boolean> get() = _isValidate

    private var _token = MutableLiveData<String>()
    val token: MutableLiveData<String> get() = _token

    private val _showToast = MutableLiveData<String>()
    val showToast : LiveData<String> get() = _showToast

    fun validarUser (user:String, password: String, context: Context){
        val userSesion = UserSesion(user, password)
        LoginRepository.fetchUserSesion(userSesion,
            {
                val token = it.access_token
                val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("token", token)
                    apply()
                }
                _token.value = token
                _isValidate.value = true
            },{
                 _isValidate.value = false
                _showToast.value = "Usuario o contraseña incorrectos"

            }
        )
    }

}