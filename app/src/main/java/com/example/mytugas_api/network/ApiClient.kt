package com.example.mytugas_api.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Fungsi untuk mendapatkan instance dari ApiService
    fun getInstance() : ApiService {
        // Menambahkan interceptor untuk mencatat log HTTP
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        // Membuat OkHttpClient dengan interceptor
        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        // Membuat Retrofit instance
        val builder = Retrofit.Builder()
            .baseUrl("https://random-d.uk/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return builder.create(ApiService::class.java)
    }
}
