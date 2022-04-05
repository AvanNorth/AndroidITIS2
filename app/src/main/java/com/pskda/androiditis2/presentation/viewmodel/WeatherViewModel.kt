package com.pskda.androiditis2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.domain.usecase.GetWeatherByIdUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    fun onGetWeatherByNameClick(cityId: Int) {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase(cityId)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }
}