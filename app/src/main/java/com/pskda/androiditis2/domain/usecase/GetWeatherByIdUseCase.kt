package com.pskda.androiditis2.domain.usecase

import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByIdUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        id: Int
    ): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeather(
                id
            )
        }
    }
}