package com.pskda.androiditis2.data.api.response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)