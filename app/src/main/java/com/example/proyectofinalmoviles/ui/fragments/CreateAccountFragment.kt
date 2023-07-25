package com.example.proyectofinalmoviles.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.databinding.FragmentCreateAccountBinding
import com.example.proyectofinalmoviles.databinding.FragmentLoginBinding
import com.example.proyectofinalmoviles.repository.RetrofitRepository
import com.example.proyectofinalmoviles.ui.activities.MainActivityApp
import com.example.proyectofinalmoviles.ui.viewsmodels.CreateAccountViewModel
import com.example.proyectofinalmoviles.ui.viewsmodels.LoginViewModel

class CreateAccountFragment : Fragment() {

    private lateinit var viewModel: CreateAccountViewModel
    private lateinit var binding : FragmentCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentCreateAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateAccountViewModel::class.java)

        setupListeners()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.messageToast.observe(requireActivity()) {
            if (it == ""){
                RetrofitRepository.getRetrofitInstance()
            }
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.isSuccess.observe(requireActivity()) {
            if (it){
//                (activity as MainActivityApp).navController.navigate(R.id.action_createAccountFragment_to_loginFragment)
            }
        }


    }

    private fun setupListeners() {
        binding.btnCrearNuevaCuenta.setOnClickListener(){
            val fullName = binding.inputncNombre.text.toString()
            val email = binding.inputncEmail.text.toString()
            val password = binding.inputncPassword.text.toString()
            val repetePassword = binding.inputRepetePass.text.toString()
            val dateBirth = binding.inputncFecha.text.toString()
            val dni = binding.inputncCI.text.toString()
            viewModel.validateFields(fullName,email,password,repetePassword,dateBirth,dni)
        }
    }
}