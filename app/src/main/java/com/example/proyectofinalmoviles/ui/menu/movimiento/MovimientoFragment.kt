package com.example.proyectofinalmoviles.ui.menu.movimiento

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.databinding.FragmentMovimientoBinding
import com.example.proyectofinalmoviles.ui.adapters.adaptersMov.MovCardsAdapter
import com.example.proyectofinalmoviles.ui.adapters.adaptersMov.MovHistorialAdapter
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout


class MovimientoFragment : Fragment(), MovCardsAdapter.OnClickListenerCard {

    private lateinit var binding: FragmentMovimientoBinding
    private lateinit var viewModel: MovimientoViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovimientoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MovimientoViewModel::class.java)

        token = "Bearer " + requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("token", null).toString()

        setupViewModelObservers()
        setupListeners()
        setupInitFuntions()
        return binding.root
    }

    private fun setupViewModelObservers() {
        viewModel.listaCards.observe(viewLifecycleOwner) {
            binding.listCards.apply {
                adapter = MovCardsAdapter(it, this@MovimientoFragment)
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            }
        }

        viewModel.listaMovimientos.observe(viewLifecycleOwner)
        {
            binding.listMovimientos.apply {
                adapter = MovHistorialAdapter(it)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            }
        }

        viewModel.showToast.observe(viewLifecycleOwner){
            if (it ){
                Toast.makeText(requireContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setupListeners() {
        binding.btnDepositar.setOnClickListener {
            showDialog("ingreso")
        }

        binding.btnRetirar.setOnClickListener {
            showDialog("retiro")
        }

        binding.addAccount.setOnClickListener {
            viewModel.addAccount(token)
        }
    }

    private fun setupInitFuntions() {
        viewModel.getCards(token)
        viewModel.getMovimientos(token)
    }

    private fun showDialog(type : String){
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        builder.setTitle(type)
        val dialogLayout = inflater.inflate(R.layout.select_layout_movimiento, null)

        val cuentas = viewModel.listaCards.value?.map { it.id.toString() + " - " + it.saldo}
        val adapter = ArrayAdapter(requireContext(), R.layout.autocompletcards, cuentas!!)
        val menu= dialogLayout.findViewById<TextInputLayout>(R.id.MenuAccount)
        (menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        val monto = dialogLayout.findViewById<EditText>(R.id.inputMonto)
        val descripcion = dialogLayout.findViewById<EditText>(R.id.inputDescripcion)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Aceptar"){
                dialog, _ ->
            if (viewModel.verificarCampos(monto.text.toString(), descripcion.text.toString(), menu.editText?.text.toString())){
                viewModel.insertMovimiento(token, monto.text.toString(), descripcion.text.toString(), menu.editText?.text.toString(), type)
            }else{
                Toast.makeText(requireContext(), "Rellene los datos", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onClickCard(id: Int) {
        viewModel.setNroTarjeta(id)
        viewModel.getMovimientos(token)
    }


}