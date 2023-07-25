package com.example.proyectofinalmoviles.ui.fragments

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.databinding.FragmentLoginBinding
import com.example.proyectofinalmoviles.ui.activities.MainActivityApp
import com.example.proyectofinalmoviles.ui.viewsmodels.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding  = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setupEventlinsteners()
        setupObservers()
        return binding.root
    }


    private fun setupEventlinsteners() {
        binding.btnSesion.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword2.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Campos vacios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.validarUser(email, password, requireContext())
        }

        binding.txtCreate.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }
    }

    private fun setupObservers() {
        viewModel.isValidate.observe(requireActivity()) {
            if (!it){
                Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()

            }else{
            startActivity(MainActivityApp.newIntent(requireContext(), viewModel.token.value!!))
            activity?.finish()
            }
        }

        viewModel.showToast.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearFields() {
        binding.editTextTextEmailAddress.setText("")
        binding.editTextTextPassword2.setText("")
    }
}