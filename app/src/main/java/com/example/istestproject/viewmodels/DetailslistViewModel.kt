package com.example.istestproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.istestproject.di.RequestStates
import com.example.istestproject.model.DetailsListModel
import com.example.istestproject.repo.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsListViewModel
@Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {

    var myResponse: DetailsListModel? = null

    val getProductListLiveData: MutableLiveData<RequestStates<DetailsListModel>> = MutableLiveData()

    fun getProducts(key: String) = viewModelScope.launch {

        apiCalling(key)
    }

    private suspend fun apiCalling(key: String) {
        getProductListLiveData.postValue(RequestStates.Loading())



        try {

            val response = networkRepository.getDetails(key)
            getProductListLiveData.postValue(handleResponse(response))

        } catch (ex: Exception) {
            when (ex) {
                is IOException -> getProductListLiveData.postValue(RequestStates.Error("Network Failure"))

            }
        }
    }

    private fun handleResponse(response: Response<DetailsListModel>): RequestStates<DetailsListModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (myResponse == null)
                    myResponse = resultResponse
                myResponse?.let {
                    return RequestStates.Success(it)
                }

            }
        }
        return RequestStates.Error(response.message())
    }

}