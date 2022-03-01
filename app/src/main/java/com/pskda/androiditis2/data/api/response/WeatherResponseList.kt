package com.pskda.androiditis2.data.api.response

data class WeatherResponseList(
    val clouds: CloudsX,
    val coord: CoordX,
    val dt: Int,
    val id: Int,
    val main: MainX,
    val name: String,
    val rain: Any,
    val snow: Any,
    val sys: SysX,
    val weather: List<WeatherX>,
    val wind: WindX
)