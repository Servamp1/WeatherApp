package com.example.weatherapp

import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherDataTest {
    private fun weather(angle: Double = 0.0, code: Int = 0) =
        WeatherData(0.0, 0.0, angle, 0.0, code, "2026-11-01T09:00", "Europe/Moscow", 900)

    @Test fun windSectorsAndBoundaries() {
        val cases = mapOf(0.0 to 0, 22.49 to 0, 22.5 to 1, 45.0 to 1,
            90.0 to 2, 135.0 to 3, 180.0 to 4, 225.0 to 5,
            270.0 to 6, 315.0 to 7, 337.49 to 7, 337.5 to 0, 360.0 to 0)
        for ((angle, expected) in cases) assertEquals(expected, weather(angle).directionIndex())
    }

    @Test fun weatherCodesAndUnknownFallback() {
        assertEquals(R.string.clear, weather(code = 0).descriptionRes())
        assertEquals(R.string.freezing_rain, weather(code = 67).descriptionRes())
        assertEquals(R.string.snow, weather(code = 75).descriptionRes())
        assertEquals(R.string.hail, weather(code = 99).descriptionRes())
        assertEquals(R.string.unknown, weather(code = 1234).descriptionRes())
    }

    @Test fun cityIdentifiersAndCoordinates() {
        assertEquals(10, City.cities.size)
        assertEquals(10, City.cities.map { it.id }.toSet().size)
        assertEquals("moscow", City.cities.first().id)
        assertEquals(49.12214, City.cities.first { it.id == "kazan" }.longitude, 0.00001)
    }
}
