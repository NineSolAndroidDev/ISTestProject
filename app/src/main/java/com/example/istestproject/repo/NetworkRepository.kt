package com.example.istestproject.repo

import com.example.istestproject.di.DataService
import com.example.istestproject.model.DetailsListModel
import com.example.istestproject.model.RawJsonModel
import com.example.istestproject.model.TokenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val dataService: DataService) {

    suspend fun getToken(body: RawJsonModel): Call<TokenModel> = withContext(Dispatchers.IO)
    {
        dataService.getAuthToken(body)
    }

    suspend fun getDetails(token: String): Response<DetailsListModel> = withContext(Dispatchers.IO)
    {
        dataService.getProductsDetail("Bearer ${token}")
    }
}
//fun getDummyList() = listOf(
//    Item("test item product", 1, 0, "Product1", 3789.20),
//    Item("test item product", 2, 0, "Product2", 1789.20),
//    Item("test item product", 3, 0, "Product3", 2789.20),
//    Item("test item product", 4, 0, "Product4", 5789.20),
//    Item("test item product", 5, 0, "Product5", 4789.20),
//    Item("test item product", 6, 0, "Product6", 7789.20),
//    Item("test item product", 7, 0, "Product7", 8789.20),
//    Item("test item product", 8, 0, "Product8", 9789.20),
//    Item("test item product", 9, 0, "Product9", 1589.20),
//    Item("test item product", 10, 0, "Product10", 3700.20),
//    Item("test item product", 11, 0, "Product11", 3701.20)
//)
