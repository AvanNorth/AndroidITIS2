package com.pskda.androiditis2.data.api.model

import com.google.gson.annotations.SerializedName

class WeatherResponseList(
    @SerializedName("list")
    val weatherList: List<WeatherResponse>


)