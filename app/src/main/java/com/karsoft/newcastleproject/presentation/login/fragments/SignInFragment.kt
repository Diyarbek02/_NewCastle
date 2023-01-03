package com.karsoft.newcastleproject.presentation.login.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.karsoft.newcastleproject.MainActivity
import com.karsoft.newcastleproject.R
import com.karsoft.newcastleproject.core.Constants.TOKEN
import com.karsoft.newcastleproject.core.NetworkResult
import com.karsoft.newcastleproject.core.loginRequest
import com.karsoft.newcastleproject.databinding.FragmentSignInBinding
import com.karsoft.newcastleproject.presentation.login.LoginActivity
import com.karsoft.newcastleproject.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        navController = findNavController()

        binding.apply {
            textRegister2.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fcv, SignUpFragment())
                    .addToBackStack(SignUpFragment::class.java.simpleName)
                    .commit()
            }

            btnSignIn.setOnClickListener {
                val phone = etPhone.text.toString()
                val password = etPassword.text.toString()

                val loginUser = loginRequest(phone, password)

                viewModel.login(loginUser)

                viewModel.login.observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }

                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Login Successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            TOKEN = it.data?.token ?: ""
                            val intent = Intent(LoginActivity(), MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()

                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Failed authorization!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }


}