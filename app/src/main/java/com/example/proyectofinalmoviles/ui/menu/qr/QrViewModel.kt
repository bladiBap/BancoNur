package com.example.proyectofinalmoviles.ui.menu.qr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinalmoviles.models.Card
import com.example.proyectofinalmoviles.models.CobroQr
import com.example.proyectofinalmoviles.models.PagoQr
import com.example.proyectofinalmoviles.models.RespondCobroQr
import com.example.proyectofinalmoviles.repository.MovimientoRepository
import com.example.proyectofinalmoviles.repository.QrRepository

class QrViewModel : ViewModel() {

    private val _cobroQr = MutableLiveData<RespondCobroQr>()
    val cobroQr : LiveData<RespondCobroQr> get() = _cobroQr

    private val _listaCards = MutableLiveData<ArrayList<Card>>()
    val listaCards :LiveData<ArrayList<Card>> get() = _listaCards

    private val _imagenQrBase64 = MutableLiveData<Bitmap>()
    val imagenQrBase64 :LiveData<Bitmap> get() = _imagenQrBase64

    private val _success_pay = MutableLiveData<Boolean>()
    val success_pay :LiveData<Boolean> get() = _success_pay

    private val _codigo_qr = MutableLiveData<String>()
    val codigo_qr : LiveData<String> get() = _codigo_qr


    fun postCobroQr(token : String, monto : String, fechaLimite : String, cuentaDestino : Int){
        val cobroQr = CobroQr(monto, cuentaDestino, fechaLimite)
        QrRepository.fetchCobroQr(token, cobroQr,
            {
                val decoded = it.imagen.split(",")[1]
                val decodedBytes = Base64.decode(decoded, Base64.DEFAULT)
                val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                _imagenQrBase64.value = decodedBitmap
            },{
                print(it)
            }
        )
    }

    fun postPagoQr(token: String, codigoQr: String, cuentaOrige: String){
        val pagoqr = PagoQr(codigoQr, cuentaOrige)
        QrRepository.fetchPagarQr(token, pagoqr,
            {
                _success_pay.value = true
                Log.e("error", "success")
            },{
                print(it)
                Log.e("error", "error")

            }
        )
    }

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

    fun saveCodeQr(code : String){
        _codigo_qr.value = code
    }
//    if (menu.editText?.text.toString() == "" || monto.text.toString() == "" || fecha.text.toString() == ""){
//        Toast.makeText(requireContext(), "Rellenar los campos", Toast.LENGTH_SHORT).show()
//        return
//    }
    fun verificarCamposGenerarQr(monto : String, fecha : String, id :String) : Boolean{
        if (monto == "" || fecha == "" || id == ""){
            return true
        }
        return false
    }



//    fun insertCobroQr(token : String, monto : String, fecha : String, cards : ArrayList<String>){
//        for (card in cards){
//            if (card == monto){
//                val id = card.split(" - ")[0].toInt()
//                Log.e("id", id.toString())
//                postCobroQr(token,monto, fecha, id)
//                return
//            }
//        }
//
//    }
}