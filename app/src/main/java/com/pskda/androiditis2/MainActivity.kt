package com.pskda.androiditis2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.media.session.PlaybackState
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.pskda.androiditis2.adapter.WeatherAdapter
import com.pskda.androiditis2.data.WeatherRepository
import com.pskda.androiditis2.data.api.response.WeatherResponse
import com.pskda.androiditis2.data.api.response.WeatherResponseList
import com.pskda.androiditis2.databinding.ActivityMainBinding
import com.pskda.androiditis2.databinding.RecyclerviewItemBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val weatherRepository by lazy {
        WeatherRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    searchCity(query)
                else
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Строка не должна быть пустой",
                        Snackbar.LENGTH_LONG
                    ).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        )

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error: permission not granted",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            return
        }


        val weatherList: RecyclerView = binding.weatherList
        weatherList.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            try {
                var lat = 57.0
                var lon = -2.15

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            lat = location.latitude
                            lon = location.longitude
                        }
                    }
                val responseList: WeatherResponseList = weatherRepository.getNearWeather(lat, lon)
                weatherList.adapter = WeatherAdapter(responseList)
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

    private fun searchCity(cityName: String) {
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