package com.pskda.androiditis2.data.api

import com.pskda.androiditis2.data.api.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather?units=metric&lang=RU")
    suspend fun getWeather(@Query("q") cityName: String): WeatherResponse
    suspend fun getWeather(@Query("id") cityId: Int): WeatherResponse

    @GET("find")
    suspend fun getNearCity(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("cnt") count: Int = 10): WeatherResponse
}