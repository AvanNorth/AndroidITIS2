package com.pskda.androiditis2.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pskda.androiditis2.di.DIContainer
import com.pskda.androiditis2.presentation.viewmodel.ListViewModel
import com.pskda.androiditis2.presentation.viewmodel.WeatherViewModel

class ViewModelFactory (
    private val di: DIContainer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) ->
                ListViewModel(
                    di.getWeatherByNameUseCase,
                    di.getNearCitiesUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(WeatherViewModel::class.java) ->
                WeatherViewModel(
                    di.getWeatherByIdUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}