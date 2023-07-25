package com.example.proyectofinalmoviles.ui.menu.movimiento.retiro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofinalmoviles.R

class RetiroFragment : Fragment() {

    companion object {
        fun newInstance() = RetiroFragment()
    }

    private lateinit var viewModel: RetiroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_retiro, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RetiroViewModel::class.java)
        // TODO: Use the ViewModel
    }

}