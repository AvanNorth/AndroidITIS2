package com.pskda.androiditis2.data.api.model

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)