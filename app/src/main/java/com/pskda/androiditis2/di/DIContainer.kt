package com.pskda.androiditis2.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.pskda.androiditis2.data.WeatherRepositoryImpl
import com.pskda.androiditis2.data.api.Api
import com.pskda.androiditis2.data.api.mapper.WeatherMapper
import com.pskda.androiditis2.domain.conventer.WindConverter
import com.pskda.androiditis2.domain.repository.WeatherRepository
import com.pskda.androiditis2.domain.usecase.GetNearCitiesUseCase
import com.pskda.androiditis2.domain.usecase.GetWeatherByIdUseCase
import com.pskda.androiditis2.domain.usecase.GetWeatherByNameUseCase
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "27c7562ae39fc5e14083e53be2619ffe"
private const val QUERY_API_KEY = "appid"

private const val METRIC = "metric"
private const val QUERY_UNITS = "units"

private const val LANG_CODE = "en"
private const val QUERY_LANG = "lang"

class DIContainer(
    private val context: Context
) {
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

    private val unitsInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, METRIC)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val langInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG_CODE)
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
            .addInterceptor(unitsInterceptor)
            .addInterceptor(langInterceptor)
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

    private val weatherMapper: WeatherMapper = WeatherMapper(
        windConverter = WindConverter()
    )

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = weatherMapper
    )

    val getNearCitiesUseCase: GetNearCitiesUseCase = GetNearCitiesUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getWeatherByIdUseCase: GetWeatherByIdUseCase = GetWeatherByIdUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getWeatherByNameUseCase: GetWeatherByNameUseCase = GetWeatherByNameUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )
}