package com.example.mytugas_api.model

import com.google.gson.annotations.SerializedName

data class Duck(

    // Maps the "url" field in the JSON response to this variable
    @SerializedName("url")
    val picture: String,

    // Maps the "message" field in the JSON response to this variable
    @SerializedName("message")
    val message: String?
)
//ini untuk mendefinisikan apasaja yang akan diambil