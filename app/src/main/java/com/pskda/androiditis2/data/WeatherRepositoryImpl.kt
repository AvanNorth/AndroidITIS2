package com.pskda.androiditis2.data

import com.pskda.androiditis2.data.api.Api
import com.pskda.androiditis2.data.api.mapper.WeatherMapper
import com.pskda.androiditis2.data.api.model.WeatherResponse
import com.pskda.androiditis2.data.api.model.WeatherResponseList
import com.pskda.androiditis2.domain.entity.Cities
import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: Api,
    private val weatherMapper: WeatherMapper
): WeatherRepository {

    override suspend fun getWeather(cityName: String): Weather {
        return weatherMapper.toWeather(api.getWeather(cityName))
    }

    override suspend fun getWeather(cityId: Int): Weather {
        return weatherMapper.toWeather(api.getWeather(cityId))
    }

    override suspend fun getNearWeather(latitude: Double, longitude: Double, count: Int): Cities {
        return weatherMapper.toListWeather(api.getNearCity(latitude, longitude, count))
    }
}