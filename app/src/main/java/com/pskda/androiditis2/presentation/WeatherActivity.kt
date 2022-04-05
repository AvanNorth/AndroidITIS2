package com.pskda.androiditis2.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.pskda.androiditis2.R
import com.pskda.androiditis2.databinding.ActivityWeatherBinding
import com.pskda.androiditis2.di.DIContainer
import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.presentation.viewmodel.WeatherViewModel
import com.pskda.androiditis2.utils.factory.ViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObjects()
        initObservers()

        val cityId = intent.getIntExtra("CityId", 0)
        cityId.let {
            viewModel.onGetWeatherByNameClick(it)
        }

    }

    private fun initObservers() {
        viewModel.weather.observe(LifecycleOwner) { result ->
            result.fold(onSuccess = { city ->
                setWeather(city)
            },
                onFailure = {
                    Log.e("WeatherErr",it.message.toString())
                })
        }
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer(this))
        viewModel = ViewModelProvider(
            this,
            factory
        )[WeatherViewModel::class.java]
    }


    private fun setWeather(
        weather: Weather
    ) {
        binding.countryTv.text = weather.name
        //binding.skyTv.text = weather
        binding.tempTv.text = resources.getString(R.string.temp_tv, weather.temp)
        binding.windTv.text = resources.getString(R.string.wind_tv, weather.windSpeed)
        binding.windDirTv.text = resources.getString(R.string.wind_dir_tv, weather.windDir)
        binding.tempFeelTv.text = resources.getString(R.string.temp_feels_tv, weather.feelsLike)
        Picasso.get()
            .load("https://openweathermap.org/img/wn/${weather.icon}@2x.png").fit()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.tempIcon, object : Callback {
                override fun onSuccess() {
                    //set animations here
                }

                override fun onError(e: Exception?) {
                    Log.e("Picasso", e?.message.toString())
                }
            })
    }
}