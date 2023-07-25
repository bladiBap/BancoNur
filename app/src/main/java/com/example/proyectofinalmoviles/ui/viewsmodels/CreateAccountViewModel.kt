package com.example.proyectofinalmoviles.ui.viewsmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.NewUser
import com.example.proyectofinalmoviles.repository.LoginRepository

class CreateAccountViewModel : ViewModel() {
    private var _isValidateFullName = MutableLiveData<Boolean>()
    val isValidateFullName : LiveData<Boolean> get() = _isValidateFullName

    private var _isValidateEmail = MutableLiveData<Boolean>()
    val isValidateEmail : LiveData<Boolean> get() = _isValidateEmail

    private var _isValidatePassword = MutableLiveData<Boolean>()
    val isValidatePassword : LiveData<Boolean> get() = _isValidatePassword

    private var _isValidateDate = MutableLiveData<Boolean>()
    val isValidateDate : LiveData<Boolean> get() = _isValidateDate

    private var _isValidateCi = MutableLiveData<Boolean>()
    val isValidateCi : LiveData<Boolean> get() = _isValidateCi

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> get() = _isSuccess

    private var _messageToast = MutableLiveData<String>()
    val messageToast : LiveData<String> get() = _messageToast





    fun validateFields(name : String, email : String, password : String,
                       repetePass : String, date:String, ci : String){
        var message = ""

        if (name == "" ){
            _isValidateFullName.value = false
            message = message + "Nombre "
        }

        if (email == "" ){
            _isValidateEmail.value = false
            message = message + "Email "
        }

        if (password == "" || repetePass == "" || (password != repetePass) ){
            _isValidatePassword.value = false
            message = message + "Contraseñas "
        }

        if (date == "" ){
            _isValidateDate.value = false
            message = message + "Fecha "
        }

        if (ci == "" ){
            _isValidateCi.value = false
            message = message + "Ci "
        }

        if(message != ""){
            _messageToast.value = message + " son campos invalidos"
            return
        }

        var newUser  = NewUser(name,email,password, ci, date)
        LoginRepository.fetchNewUser(newUser,{
            _isSuccess.value = true
            Log.e("Entro", "success")

        },{
            _isSuccess.value = false
            Log.e("Entro", "paso algo error")
        })
    }
}