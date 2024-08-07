package com.example.apiapp.Apiservice

import com.example.apiapp.data.ProductX
import com.example.apiapp.data.ProductXDet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/products")
    fun getProducts(): Call<ProductResponse>

    @GET("/products/{id}")
    fun getProdDet(@Path("id") id: Int): Call<ProductXDet>
}


data class ProductResponse(val products: List<ProductX>)
