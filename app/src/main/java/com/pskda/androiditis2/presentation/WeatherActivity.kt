package com.pskda.androiditis2.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pskda.androiditis2.R
import com.pskda.androiditis2.data.WeatherRepositoryImpl
import com.pskda.androiditis2.data.api.model.WeatherResponse
import com.pskda.androiditis2.databinding.ActivityWeatherBinding
import com.pskda.androiditis2.domain.entity.Weather
import com.pskda.androiditis2.domain.usecase.GetWeatherByIdUseCase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var getWeatherUserCase: GetWeatherByIdUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cityId = intent.getIntExtra("CityId", 0)
        var response: Weather

        lifecycleScope.launch {
            try {
                response = getWeatherUserCase(cityId)
                setWeather(
                    binding,
                    response.name,
                    response.temp,
                    response.desc,
                    response.windSpeed,
                    response.icon,
                    response.windDir,
                    response.feelsLike
                )
            } catch (ex: Exception) {
                Log.e("WeatherError", ex.message.toString())
            }
        }
    }

    private fun setWeather(
        binding: ActivityWeatherBinding,
        countryName: String,
        temp: Double,
        sky: String,
        wind: Double,
        ico: String,
        windDir: Int,
        feelsLike: Double
    ) {
        binding.countryTv.text = countryName
        binding.skyTv.text = sky
        binding.tempTv.text = resources.getString(R.string.temp_tv, temp)
        binding.windTv.text = resources.getString(R.string.wind_tv, wind)
        binding.windDirTv.text = resources.getString(R.string.wind_dir_tv, getWindDir(windDir))
        binding.tempFeelTv.text = resources.getString(R.string.temp_feels_tv, feelsLike)
        Picasso.get()
            .load("https://openweathermap.org/img/wn/${ico}@2x.png").fit()
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

    private fun getWindDir(windDegree: Int): String {
        when (windDegree) {
            in 33..56 -> return "NE"
            in 78..101 -> return "E"
            in 123..146 -> return "SE"
            in 168..191 -> return "S"
            in 213..236 -> return "SW"
            in 258..281 -> return "W"
            in 303..326 -> return "NW"
            in 348..365 -> return "N"
            in 0..11 -> return "N"
        }
        return "Error"
    }
}