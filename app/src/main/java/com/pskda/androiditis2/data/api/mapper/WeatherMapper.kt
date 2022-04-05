package com.pskda.androiditis2.data.api.mapper

import com.pskda.androiditis2.data.api.model.WeatherResponse
import com.pskda.androiditis2.data.api.model.WeatherResponseList
import com.pskda.androiditis2.domain.conventer.WindConverter
import com.pskda.androiditis2.domain.entity.Cities
import com.pskda.androiditis2.domain.entity.Weather


class WeatherMapper(
    private val windConverter: WindConverter
) {
    fun toWeather(response: WeatherResponse): Weather = Weather(
        id = response.id,
        name = response.name,
        latitude = response.coord.lat,
        longitude = response.coord.lon,
        temp = response.main.temp,
        icon = response.weather[0].icon,
        desc = response.weather[0].description,
        feelsLike = response.main.feelsLike,
        humidity = response.main.humidity,
        windSpeed = response.wind.speed,
        windDir = windConverter.convertWindDir(response.wind.deg),
        timezone = response.timezone,
        sunrise = response.sys.sunrise,
        sunset = response.sys.sunset
    )

    fun toListWeather(response: WeatherResponseList): Cities = Cities(
        list = response.weatherList.map { weatherInfo ->  toWeather(weatherInfo)}
    )
}