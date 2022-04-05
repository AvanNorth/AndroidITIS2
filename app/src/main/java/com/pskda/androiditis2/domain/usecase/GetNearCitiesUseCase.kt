package com.pskda.androiditis2.domain.usecase

import com.pskda.androiditis2.domain.entity.Cities
import com.pskda.androiditis2.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNearCitiesUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        count: Int
    ): Cities {
        return withContext(dispatcher) {
            weatherRepository.getNearWeather(
                latitude,
                longitude,
                count
            )
        }
    }
}