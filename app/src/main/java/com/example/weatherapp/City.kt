package com.example.weatherapp

import android.content.Context
import androidx.core.content.edit

data class City(val id: String, val nameRes: Int, val latitude: Double, val longitude: Double) {
    companion object {
        val cities = listOf(
            City("moscow", R.string.city_0, 55.75204, 37.61781),
            City("saint_petersburg", R.string.city_1, 59.93863, 30.31413),
            City("kazan", R.string.city_2, 55.78874, 49.12214),
            City("yekaterinburg", R.string.city_3, 56.85733, 60.61529),
            City("novosibirsk", R.string.city_4, 55.02259, 82.93175),
            City("nizhny_novgorod", R.string.city_5, 56.32867, 44.00205),
            City("samara", R.string.city_6, 53.20767, 50.13553),
            City("krasnodar", R.string.city_7, 45.04534, 38.98178),
            City("rostov", R.string.city_8, 47.21997, 39.70769),
            City("vladivostok", R.string.city_9, 43.10562, 131.87354)
        )
        fun selected(context: Context): City {
            val id = context.getSharedPreferences("weather", Context.MODE_PRIVATE).getString("city", null)
            return cities.find { it.id == id } ?: cities.first()
        }
        fun save(context: Context, city: City) {
            context.getSharedPreferences("weather", Context.MODE_PRIVATE).edit { putString("city", city.id) }
        }
    }
}
