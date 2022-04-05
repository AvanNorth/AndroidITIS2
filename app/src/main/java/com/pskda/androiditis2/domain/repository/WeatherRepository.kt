package com.pskda.androiditis2.domain.repository

import com.pskda.androiditis2.domain.entity.Cities
import com.pskda.androiditis2.domain.entity.Weather

interface WeatherRepository {

    suspend fun getWeather(cityName: String): Weather

    suspend fun getWeather(cityId: Int): Weather

    suspend fun getNearWeather(latitude: Double, longitude: Double, count: Int): Cities
}