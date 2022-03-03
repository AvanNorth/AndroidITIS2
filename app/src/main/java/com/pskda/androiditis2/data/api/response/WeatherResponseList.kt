package com.pskda.androiditis2.data.api.response

import com.google.gson.annotations.SerializedName

class WeatherResponseList(
    @SerializedName("list")
    val weatherList: List<WeatherResponse>


)