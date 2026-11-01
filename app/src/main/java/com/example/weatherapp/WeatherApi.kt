package com.example.weatherapp

import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import javax.net.ssl.HttpsURLConnection

object WeatherApi {
    fun load(city: City): WeatherData {
        val url = "https://api.open-meteo.com/v1/forecast?latitude=" + city.latitude +
            "&longitude=" + city.longitude +
            "&current=temperature_2m,wind_speed_10m,wind_direction_10m,precipitation,weather_code" +
            "&temperature_unit=celsius&wind_speed_unit=ms&precipitation_unit=mm&timezone=auto"
        val connection = URL(url).openConnection() as HttpsURLConnection
        try {
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            if (connection.responseCode != 200) throw IOException("HTTP " + connection.responseCode)
            val json = connection.inputStream.bufferedReader().use { JSONObject(it.readText()) }
            require(!json.optBoolean("error", false))
            val units = json.getJSONObject("current_units")
            require(units.getString("temperature_2m") == "°C")
            require(units.getString("wind_speed_10m") == "m/s")
            require(units.getString("wind_direction_10m") == "°")
            require(units.getString("precipitation") == "mm")
            require(units.getString("time") == "iso8601" && units.getString("interval") == "seconds")
            val current = json.getJSONObject("current")
            fun number(key: String): Double {
                val value = current.get(key)
                require(value is Number && value.toDouble().isFinite())
                return value.toDouble()
            }
            val temperature = number("temperature_2m")
            val speed = number("wind_speed_10m")
            val direction = number("wind_direction_10m")
            val rain = number("precipitation")
            val code = number("weather_code")
            val interval = number("interval")
            require(speed >= 0 && rain >= 0 && direction in 0.0..360.0)
            require(code >= 0 && code <= Int.MAX_VALUE && code % 1 == 0.0)
            require(interval > 0 && interval <= 86400 && interval % 60 == 0.0)
            val time = current.getString("time")
            require(time.matches(Regex("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}")))
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ROOT)
            format.isLenient = false
            requireNotNull(format.parse(time))
            val timezone = json.getString("timezone")
            require(timezone.isNotBlank())
            return WeatherData(temperature, speed, direction, rain, code.toInt(), time, timezone, interval.toInt())
        } finally {
            connection.disconnect()
        }
    }
}
