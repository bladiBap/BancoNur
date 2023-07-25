package com.example.proyectofinalmoviles.ui.menu.qr



import android.content.Context
import android.content.Intent


import android.os.Bundle
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.FileProvider

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.example.proyectofinalmoviles.databinding.FragmentQrBinding
import com.google.zxing.integration.android.IntentIntegrator
import java.io.File
import java.io.FileOutputStream


class QrFragment : Fragment() {

    companion object {
        fun newInstance() = QrFragment()
    }

    private lateinit var binding: FragmentQrBinding
    private lateinit var viewModel : QrViewModel
    private lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(QrViewModel::class.java)
        token = "Bearer " + requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null).toString()
        setupListeners()
        cargarListCard()
        setupObservers()
//        cargarListCardInMenu()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.imagenQrBase64.observe(viewLifecycleOwner) {
            if (it != null){
                Toast.makeText(requireContext(), "Qr generado con exito", Toast.LENGTH_SHORT).show()
                binding.imageQr.setImageBitmap(it)
            }
        }

        viewModel.success_pay.observe(viewLifecycleOwner) {
            if (it != null){
                if (it){
                    Toast.makeText(requireContext(), "Pago realizado con exito", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Error al realizar el pago", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.codigo_qr.observe(viewLifecycleOwner){
            binding.txtCodigoQr.editText?.setText(it)
        }
    }

    private fun setupListeners() {
        binding.btnShowCobroQr.setOnClickListener(){
            binding.LayoutCobroQr.visibility = View.VISIBLE
            binding.LayoutQrImg.visibility = View.GONE
            binding.layoutPagarQr.visibility = View.GONE
            cargarListCardInMenuCobro()
        }

        binding.btnShowPagoQr.setOnClickListener(){
            binding.LayoutCobroQr.visibility = View.GONE
            binding.LayoutQrImg.visibility = View.GONE
            binding.layoutPagarQr.visibility = View.VISIBLE
            cargarListCardInMenuPago()

        }
        binding.btnGenerarQr.setOnClickListener(){
            binding.LayoutCobroQr.visibility = View.VISIBLE
            binding.LayoutQrImg.visibility = View.VISIBLE
            binding.layoutPagarQr.visibility = View.GONE
            generarQr()
        }

        binding.btnPagarQr.setOnClickListener(){
            pagarQr()
        }

        binding.btnCargarQr.setOnClickListener(){
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Scan QR code")
            integrator.setBeepEnabled(false)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.initiateScan()
        }

        binding.sharedQr.setOnClickListener(){
            val bitma = viewModel.imagenQrBase64.value
            val imgStr = bitma.toString()
            val decodeBytes = Base64.decode(imgStr, Base64.DEFAULT)
            val decodedBitmat = BitmapFactory.decodeByteArray(decodeBytes, 0, decodeBytes.size)

            if (bitma != null){
                val shareIntent : Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, getImageUri(decodedBitmat))
                    type = "image/jpeg"
                }
                val sharedIntentChooser = Intent.createChooser(shareIntent, "Compartir imagen")
                startActivity(sharedIntentChooser)
            }
        }
    }

    private fun getImageUri(bitmap: Bitmap): Uri {
        val imagesFolder = File(requireContext().cacheDir, "images")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }

        val file = File(imagesFolder, "shared_image.jpeg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()

        return FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".fileprovider",file)}

    fun cargarListCard(){
        viewModel.getCards(token)
    }

    fun cargarListCardInMenuCobro(){
        val cards = viewModel.listaCards.value?.map { it.id.toString() + " - " + it.saldo }
        val adapter = ArrayAdapter(requireContext(),com.example.proyectofinalmoviles.R.layout.autocompletecobroqr, cards!!)
        val menu= binding.MenuAccountAcreditacion
        (menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    fun cargarListCardInMenuPago(){
        val cards = viewModel.listaCards.value?.map { it.id.toString() + " - " + it.saldo }
        val adapter2 = ArrayAdapter(requireContext(),com.example.proyectofinalmoviles.R.layout.autocompletepagoqr, cards!!)
        val menu2= binding.MenuAccountRetiroPqr
        (menu2.editText as? AutoCompleteTextView)?.setAdapter(adapter2)
    }

    fun generarQr(){
        val menu = binding.MenuAccountAcreditacion
        val monto = binding.inputMontoCqr
        val fecha = binding.inputFechaLimCqr
        val cards = viewModel.listaCards.value?.map { it.id.toString() + " - " + it.saldo }

        if (viewModel.verificarCamposGenerarQr( menu.editText?.text.toString(), monto.text.toString(), fecha.text.toString())){
            Toast.makeText(requireContext(), "Rellenar los campos", Toast.LENGTH_SHORT).show()
            binding.LayoutQrImg.visibility = View.GONE
            return
        }

        for (card in cards!!){
            if (card == menu.editText?.text.toString()){
                val id = card.split(" - ")[0].toInt()
                viewModel.postCobroQr(token,monto.text.toString(), fecha.text.toString(), id)
                return
            }
        }
    }

    fun pagarQr(){
        val menu = binding.MenuAccountRetiroPqr
        val codigo = binding.inputCodigoQr
        val cards = viewModel.listaCards.value?.map { it.id.toString() + " - " + it.saldo }

        if (menu.editText?.text.toString() == "" || codigo.text.toString() == "" ){
            Toast.makeText(requireContext(), "Rellenar los campos", Toast.LENGTH_SHORT).show()
            viewModel
            return
        }

        for (card in cards!!){
            if (card == menu.editText?.text.toString()){
                val id = card.split(" - ")[0].toInt()
                Toast.makeText(requireContext(),codigo.text.toString()+" "+id, Toast.LENGTH_SHORT ).show()
                viewModel.postPagoQr(token,codigo.text.toString(), id.toString())

                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                viewModel.saveCodeQr(result.contents)
            }
        }
    }







}