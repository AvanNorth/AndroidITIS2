package com.pskda.androiditis2

import android.media.session.PlaybackState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pskda.androiditis2.adapter.WeatherAdapter
import com.pskda.androiditis2.data.WeatherRepository
import com.pskda.androiditis2.databinding.ActivityMainBinding
import com.pskda.androiditis2.databinding.RecyclerviewItemBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val weatherRepository by lazy {
        WeatherRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null)
                searchCity(query)
                else
                    Snackbar.make(findViewById(android.R.id.content),
                    "Строка не должна быть пустой",
                    Snackbar.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        )

        var weatherMap: Map<String, Float>

        lifecycleScope.launch {
            try {
                val response = weatherRepository.getNearWeather(57.0,-2.15)
                Snackbar.make(
                    findViewById(android.R.id.content),
                    //TODO сделать
                    "Temp: ${response.main.temp} C\nCityCode: ${response.weather}",
                    Snackbar.LENGTH_LONG
                ).show()
            } catch (ex: Exception) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error: ${ex.message.toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.e("WeatherError", ex.message.toString())
            }
        }

        val weatherList: RecyclerView = binding.weatherList
        weatherList.layoutManager = LinearLayoutManager(this)
        //weatherList.adapter = WeatherAdapter()
    }

    private fun searchCity(cityName: String){
        lifecycleScope.launch {
            try {
                val response = weatherRepository.getWeather(cityName)
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Temp: ${response.main.temp} C\nCityCode: ${response.sys.id}",
                    Snackbar.LENGTH_LONG
                ).show()
            } catch (ex: Exception) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error: ${ex.message.toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.e("WeatherError", ex.message.toString())
            }
        }
    }
}