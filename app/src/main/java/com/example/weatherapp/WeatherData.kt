package com.example.weatherapp

data class WeatherData(
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val precipitation: Double,
    val code: Int,
    val time: String,
    val timezone: String,
    val intervalSeconds: Int
) {
    fun directionIndex(): Int = (((windDirection % 360) + 22.5) / 45).toInt() % 8

    fun descriptionRes(): Int = when (code) {
        0 -> R.string.clear
        1 -> R.string.mostly_clear
        2 -> R.string.partly_cloudy
        3 -> R.string.overcast
        45 -> R.string.fog
        48 -> R.string.rime_fog
        51, 53, 55 -> R.string.drizzle
        56, 57 -> R.string.freezing_drizzle
        61, 63, 65 -> R.string.rain
        66, 67 -> R.string.freezing_rain
        71, 73, 75 -> R.string.snow
        77 -> R.string.snow_grains
        80, 81, 82 -> R.string.showers
        85, 86 -> R.string.snow_showers
        95, 97 -> R.string.thunder
        96, 99 -> R.string.hail
        else -> R.string.unknown
    }
}
