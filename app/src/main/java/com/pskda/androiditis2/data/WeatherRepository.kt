package com.pskda.androiditis2.data

import com.pskda.androiditis2.BuildConfig
import com.pskda.androiditis2.data.api.Api
import com.pskda.androiditis2.data.api.response.WeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "27c7562ae39fc5e14083e53be2619ffe"
private const val QUERY_API_KEY = "appid"

class WeatherRepository {
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    private val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    suspend fun getWeather(cityName: String): WeatherResponse {
        return api.getWeather(cityName)
    }
    suspend fun getWeather(cityId: Int): WeatherResponse {
        return api.getWeather(cityId)
    }
    suspend fun getNearWeather(lat: Double, lon: Double): WeatherResponse {
        return api.getNearCity(lat,lon)
    }
}