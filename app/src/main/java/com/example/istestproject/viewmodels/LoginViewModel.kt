package com.example.istestproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.istestproject.di.RequestStates
import com.example.istestproject.model.RawJsonModel
import com.example.istestproject.model.TokenModel
import com.example.istestproject.repo.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    // create live data with different state type to handle network api calling states
    val getTokenLiveData: MutableLiveData<RequestStates<String>> = MutableLiveData()
    var myResponse: String? = null


    // this function will get product details list from network repository
    fun getToken(requestBody: RawJsonModel) = viewModelScope.launch {

        apiCalling(requestBody)
    }

    private suspend fun apiCalling(requestBody: RawJsonModel) {
        getTokenLiveData.postValue(RequestStates.Loading())
        try {

            val response = networkRepository.getToken(requestBody)

            response.enqueue(object : Callback<TokenModel> {
                override fun onResponse(call: Call<TokenModel>, response: Response<TokenModel>) {

                    val myResponse = response.body()?.token
                    if (myResponse == null) {
                        getTokenLiveData.postValue(
                            RequestStates.Error(
                                response.errorBody().toString()
                            )
                        )

                    } else {
                        getTokenLiveData.postValue(
                            RequestStates.Success(
                                myResponse
                            )
                        )
                    }


                }

                override fun onFailure(call: Call<TokenModel>, t: Throwable) {

                    getTokenLiveData.postValue(RequestStates.Error(t.message.toString()))
                }


            })


        } catch (ex: Exception) {

            when (ex) {
                is IOException -> getTokenLiveData.postValue(RequestStates.Error("Network Failure"))
                else -> getTokenLiveData.postValue(RequestStates.Error("Conversion Error"))
            }
        }
    }


}