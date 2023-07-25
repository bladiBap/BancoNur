package com.example.proyectofinalmoviles.ui.menu.movimiento.ingreso

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofinalmoviles.R

class IngresoFragment : Fragment() {

    companion object {
        fun newInstance() = IngresoFragment()
    }

    private lateinit var viewModel: IngresoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingreso, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IngresoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}