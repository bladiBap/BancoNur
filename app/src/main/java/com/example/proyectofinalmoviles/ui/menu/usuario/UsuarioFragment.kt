package com.example.proyectofinalmoviles.ui.menu.usuario

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.proyectofinalmoviles.databinding.FragmentUsuarioBinding
import com.example.proyectofinalmoviles.ui.activities.MainActivity


class UsuarioFragment : Fragment() {

    private lateinit var viewModel: UsuarioViewModel
    private lateinit var binding : FragmentUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentUsuarioBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)
        setupListeners()
        return binding.root
    }

    private fun setupListeners(){
        binding.btnCerrarsesion.setOnClickListener {
            val sharedPreferen = requireContext().getSharedPreferences("auth", 0)
            val editor = sharedPreferen.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()

        }
    }

}