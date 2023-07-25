package com.example.proyectofinalmoviles.ui.menu.transferencia

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.Beneficiario
import com.example.proyectofinalmoviles.models.BeneficiarioRespond
import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.Transferencia
import com.example.proyectofinalmoviles.repository.BeneficiarioRepository
import com.example.proyectofinalmoviles.repository.MovimientoRepository
import com.example.proyectofinalmoviles.repository.TranferenciaRepository

class TransferenciaViewModel : ViewModel() {

    private val _listaCards = MutableLiveData<ArrayList<Card>>()
    val listaCards :LiveData<ArrayList<Card>> get() = _listaCards

    private val _listaBeneficiarios = MutableLiveData<ArrayList<BeneficiarioRespond>>()
    val listaBeneficiarios :LiveData<ArrayList<BeneficiarioRespond>> get() = _listaBeneficiarios

    private val _showToast = MutableLiveData<Boolean>()
    val showToast :LiveData<Boolean> get() = _showToast


    fun getCards(token : String){
        MovimientoRepository.fetchListCards(token,
            {
                if (it.isEmpty()){
                    _listaCards.value = ArrayList<Card>()
                }else{
                    _listaCards.value = it as ArrayList<Card>
                }
            },{
                print(it)
            }
        )
    }

    fun getBeneficiarios (token : String){
        BeneficiarioRepository.fetchListBeneficiario(token,
            {
                if (it.isEmpty()){
                    _listaBeneficiarios.value = ArrayList<BeneficiarioRespond>()
                }else{
                    _listaBeneficiarios.value = it as ArrayList<BeneficiarioRespond>
                }
            },{
                print(it)
            }
        )
    }

    fun insertarBeneficiario(token : String, nroCuenta : String, ci : String, nombre : String){
        val nuevoBeneficiario = Beneficiario(nroCuenta, ci, nombre)
        BeneficiarioRepository.insertarBeneficiario(token, nuevoBeneficiario,
            {
                getBeneficiarios(token)
            },{
                print(it)
            }
        )
    }

    fun insertarTransferencia (token: String, descripcion : String, monto : Int, cuentaOrigen : String, beneficiario : Int){
        val nuevaTransferencia = Transferencia(descripcion, monto, cuentaOrigen, beneficiario)
        TranferenciaRepository.fetchInsertTransferencia(token,nuevaTransferencia,
            {
                print(it)
                getCards(token)
            },{
                print(it)
            }
        )
    }

    fun verificarCamposTransferencia(descripcion: String, monto: String, cuentaOrigen: String, beneficiario: String) : Boolean{
        if (descripcion.isEmpty() || monto.isEmpty() || cuentaOrigen.isEmpty() || beneficiario.isEmpty()){
            return true
        }
        return false
    }


    fun verificarSaldo(saldo : Double, monto : Double) : Boolean{
        if (saldo < monto){
            return true
        }
        return false
    }

    fun verificarCamposBeneficiario(nroCuenta : String, ci : String, nombre : String) : Boolean{
        if (nroCuenta.isEmpty() || ci.isEmpty() || nombre.isEmpty()){
            return true
        }
        return false
    }

    fun transferir(token : String, descripcion : String, monto : String, cuentaOrigen : String, beneficiarioo : String){
        for (beneficiario in listaBeneficiarios.value!!){
            if (beneficiario.id.toString() == beneficiarioo){
                insertarTransferencia(token, descripcion, monto.toInt(), cuentaOrigen, beneficiario.id)
                _showToast.value = true
            }
        }
    }

}