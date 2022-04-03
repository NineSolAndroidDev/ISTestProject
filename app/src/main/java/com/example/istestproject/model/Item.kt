package com.example.istestproject.model

import androidx.annotation.Keep

@Keep
data class Item(
    val description: String,
    val id: Int,
    val itemRate: Int,
    val name: String,
    val price: Double
)