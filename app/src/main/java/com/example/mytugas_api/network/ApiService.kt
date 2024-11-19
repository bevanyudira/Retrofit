package com.example.mytugas_api.network

import com.example.mytugas_api.model.Duck
import retrofit2.Call
import retrofit2.http.GET

// Interface yang mendefinisikan endpoint untuk mengakses API
interface ApiService {

    @GET("random")
    fun getRandomDuckImage(): Call<Duck>
}
