package com.example.proyectofinalmoviles.ui.menu.transferencia

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.databinding.FragmentTransferenciaBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class TransferenciaFragment : Fragment() {

    private lateinit var binding: FragmentTransferenciaBinding
    private lateinit var viewModel : TransferenciaViewModel
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransferenciaBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(TransferenciaViewModel::class.java)
        token = "Bearer " + requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null).toString()
        Log.e("token", token)
        setupData()
        setupListeners()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.showToast.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(requireContext(), "Transferencia realizada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupData() {
        viewModel.getCards(token)
        viewModel.getBeneficiarios(token)
    }


    private fun setupListeners() {
        binding.btnTransferencia.setOnClickListener {
            showDialogTransferir()
        }
        binding.btnRegistarNuevoBeneficiario.setOnClickListener {
            insertarBeneficiario()
        }
    }

    private fun insertarBeneficiario() {
        val nombre = binding.inputNombreCompleto.text.toString()
        val ci = binding.inputCarnetCi.text.toString()
        val nroCuenta = binding.inputNumeroCuenta.text.toString()

        if (viewModel.verificarCamposBeneficiario(nombre, ci, nroCuenta)){
            Toast.makeText(requireContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.insertarBeneficiario(token, nroCuenta,ci, nombre)
        limpiarCampos()
    }

    private fun limpiarCampos(){
        binding.inputNombreCompleto.setText("")
        binding.inputCarnetCi.setText("")
        binding.inputNumeroCuenta.setText("")
    }

    private fun showDialogTransferir() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        builder.setTitle("Transferir")
        val dialogLayout = inflater.inflate(R.layout.select_layout_transferir, null)
        var monto = dialogLayout.findViewById<EditText>(R.id.inputMontoTransferir)
        var descripcion = dialogLayout.findViewById<EditText>(R.id.inputDescriptionTransferir)

        var listaCards = viewModel.listaCards.value?.map { "ID: "+it.id.toString() + " - " + it.saldo + " Bs."} as ArrayList<String>
        var listaBeneficiarios = viewModel.listaBeneficiarios.value?.map { "ID: "+it.id.toString() + " - " + it.nombreCompleto} as ArrayList<String>

        var adapterTransfer = ArrayAdapter(requireContext(), R.layout.autocompletetransferencia, listaCards)
        var adapterDestination = ArrayAdapter(requireContext(), R.layout.autocompletetransferencia, listaBeneficiarios)

        val menuTransfer= dialogLayout.findViewById<TextInputLayout>(R.id.MenuAccountTrasferir)
        val menuDestiantion = dialogLayout.findViewById<TextInputLayout>(R.id.MenuAccountDestiny)

        (menuTransfer.editText as? AutoCompleteTextView)?.setAdapter(adapterTransfer)
        (menuDestiantion.editText as? AutoCompleteTextView)?.setAdapter(adapterDestination)

        builder.setView(dialogLayout)

        builder.setPositiveButton("Aceptar"){
                dialog, _ ->
            if (viewModel.verificarCamposTransferencia(monto.text.toString(), descripcion.text.toString(), menuTransfer.editText?.text.toString(), menuDestiantion.editText?.text.toString())){
                Toast.makeText(requireContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }else{

                if (viewModel.verificarSaldo( menuTransfer.editText?.text.toString().split(" ")[1].toDouble(), monto.text.toString().toDouble())){
                    Toast.makeText(requireContext(), "No tiene saldo suficiente", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                viewModel.transferir(token,descripcion.text.toString(), monto.text.toString() , menuTransfer.editText?.text.toString().split(" ")[1], menuDestiantion.editText?.text.toString().split(" ")[1])

            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}