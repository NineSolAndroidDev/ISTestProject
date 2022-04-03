package com.example.istestproject.di

import com.example.istestproject.model.DetailsListModel
import com.example.istestproject.model.RawJsonModel
import com.example.istestproject.model.TokenModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface DataService {


    @POST("app/authenticate")
    fun getAuthToken(@Body body: RawJsonModel): Call<TokenModel>

    @POST("app/item-list")
    suspend fun getProductsDetail(
        @Header("Authorization") apiKey: String?
    ): Response<DetailsListModel>
}