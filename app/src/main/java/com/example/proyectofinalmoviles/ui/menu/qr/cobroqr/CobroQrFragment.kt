package com.example.proyectofinalmoviles.ui.menu.qr.cobroqr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofinalmoviles.R

class CobroQrFragment : Fragment() {

    companion object {
        fun newInstance() = CobroQrFragment()
    }

    private lateinit var viewModel: CobroQrViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cobro_qr, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CobroQrViewModel::class.java)
        // TODO: Use the ViewModel
    }

}