package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONException
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {
    private val executor = Executors.newFixedThreadPool(2)
    private var request: Future<*>? = null
    private var requestId = 0
    private var city: City? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val root = findViewById<android.view.View>(R.id.root)
        val left = root.paddingLeft
        val top = root.paddingTop
        val right = root.paddingRight
        val bottom = root.paddingBottom
        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars() or androidx.core.view.WindowInsetsCompat.Type.displayCutout())
            view.setPadding(left + bars.left, top + bars.top, right + bars.right, bottom + bars.bottom)
            insets
        }

        findViewById<Button>(R.id.choose).setOnClickListener {
            startActivity(Intent(this, CitySelectionActivity::class.java))
        }
        findViewById<Button>(R.id.authors).setOnClickListener {
            startActivity(Intent(this, AuthorsActivity::class.java))
        }
        findViewById<Button>(R.id.refresh).setOnClickListener { loadWeather(City.selected(this)) }
    }

    override fun onResume() {
        super.onResume()
        val selected = City.selected(this)
        if (selected != city) loadWeather(selected)
    }

    private fun loadWeather(selected: City) {
        city = selected
        val id = ++requestId
        request?.cancel(true)
        findViewById<TextView>(R.id.city).setText(selected.nameRes)
        findViewById<View>(R.id.weather).visibility = View.GONE
        findViewById<View>(R.id.progress).visibility = View.VISIBLE
        findViewById<TextView>(R.id.status).apply {
            setText(R.string.loading)
            visibility = View.VISIBLE
        }
        findViewById<Button>(R.id.refresh).isEnabled = false
        request = executor.submit {
            var data: WeatherData? = null
            var error = R.string.network_error
            try {
                data = WeatherApi.load(selected)
            } catch (_: JSONException) {
                error = R.string.data_error
            } catch (_: IllegalArgumentException) {
                error = R.string.data_error
            } catch (_: java.text.ParseException) {
                error = R.string.data_error
            } catch (exception: IOException) {
                if (exception.message?.startsWith("HTTP ") == true) error = R.string.http_error
            }
            val result = data
            runOnUiThread {
                // Отменённый запрос может завершиться позже запроса для другого города.
                if (id != requestId || isDestroyed || isFinishing) return@runOnUiThread
                findViewById<View>(R.id.progress).visibility = View.GONE
                findViewById<Button>(R.id.refresh).isEnabled = true
                if (result == null) {
                    findViewById<TextView>(R.id.status).setText(error)
                } else {
                    showWeather(result)
                }
            }
        }
    }

    private fun showWeather(data: WeatherData) {
        findViewById<View>(R.id.status).visibility = View.GONE
        findViewById<View>(R.id.weather).visibility = View.VISIBLE
        findViewById<TextView>(R.id.temperature).text = getString(R.string.temperature, data.temperature)
        findViewById<TextView>(R.id.description).setText(data.descriptionRes())
        val direction = resources.getStringArray(R.array.directions)[data.directionIndex()]
        findViewById<TextView>(R.id.wind).text = getString(R.string.wind, direction, data.windSpeed)
        findViewById<TextView>(R.id.precipitation).text = getString(R.string.precipitation, data.precipitation)
        findViewById<TextView>(R.id.interval).text = getString(R.string.interval, data.intervalSeconds / 60)
        val parts = data.time.split('T')
        val date = parts[0].split('-').reversed().joinToString(".")
        findViewById<TextView>(R.id.updated).text = getString(R.string.updated, date + " " + parts[1], data.timezone)
    }

    override fun onDestroy() {
        requestId++
        request?.cancel(true)
        executor.shutdownNow()
        super.onDestroy()
    }
}
