package com.example.istestproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.istestproject.R
import com.example.istestproject.databinding.FragmentLoginBinding
import com.example.istestproject.di.ApiModule_ProvideRetrofitFactory.create
import com.example.istestproject.di.RequestStates
import com.example.istestproject.model.RawJsonModel
import com.example.istestproject.showToast
import com.example.istestproject.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


@AndroidEntryPoint
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loginBinding = FragmentLoginBinding.inflate(inflater, container, false)
        addListeners()
        return loginBinding.root
    }

    private fun addListeners() {
        loginBinding.loginBtn.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        handleObserver()

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginBtn -> {


//                val token =
//                    "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsImV4cCI6MTY0ODk2Mjc1MCwiaWF0IjoxNjQ4OTQ0NzUwfQ.a6kloMt5AiYmxzzELfzQY5EVgMIlvSV_2XVsU81_THqu1GgxA5UelLCPRJcakjdckfnwuwBOdh-yMp48Cc1KxQ"
//
//                val action =
//                    LoginFragmentDirections.actionLoginFragmentToViewItemDetails(
//                        token
//                    )
//                findNavController().navigate(action)


                if (loginBinding.usernameEt.text.toString()
                        .isNotEmpty() && loginBinding.passwordnameEt.text.toString().isNotEmpty()
                ) {


//                    val paramObject = JSONObject()
//                    paramObject.put("username", loginBinding.usernameEt.text.toString())
//                    paramObject.put("password", loginBinding.passwordnameEt.text.toString())
//
//                    val body: RequestBody = create(MediaType.parse("text/plain"), paramObject.toString())
//                    val body: RequestBody = create(MediaType.parse("application/json"), paramObject.toString())

                    loginViewModel.getToken(
                        RawJsonModel(
                            loginBinding.passwordnameEt.text.toString(),
                            loginBinding.usernameEt.text.toString()
                        )
                    )

                } else {

                    requireContext().showToast("text fields cannot be empty", 0)
                }


//                LoginFragmentDirections.actionLoginFragmentToViewItemDetails("")

            }
        }
    }


    private fun handleObserver() {

        loginViewModel.getTokenLiveData.observe(viewLifecycleOwner)
        {
            when (it) {

                is RequestStates.Loading -> {
                    // TODO show loader or animation what ever you want
                }
                is RequestStates.Success -> {
                    // when api response was successfully received when send token to the next fragment using nav controller
                    val token = it.data.toString()
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToViewItemDetails(
                            token
                        )
                    findNavController().navigate(action)

                }
                is RequestStates.Error -> {


                    // display error message to the user
                    requireContext().showToast(it.message.toString(), 0)
                }
            }
        }
    }


}