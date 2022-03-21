package com.pskda.androiditis2.data

import com.pskda.androiditis2.data.api.Api
import com.pskda.androiditis2.data.api.mapper.WeatherMapper
import com.pskda.androiditis2.data.api.model.WeatherResponse
import com.pskda.androiditis2.data.api.model.WeatherResponseList
import com.pskda.androiditis2.domain.entity.Cities
import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.domain.repository.WeatherRepository

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "27c7562ae39fc5e14083e53be2619ffe"
private const val QUERY_API_KEY = "appid"

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