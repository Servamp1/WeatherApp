package com.example.weatherapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class CitySelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection)
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

        val list = findViewById<ListView>(R.id.cities)
        list.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice,
            City.cities.map { getString(it.nameRes) })
        list.setItemChecked(City.cities.indexOf(City.selected(this)), true)
        list.setOnItemClickListener { _, _, position, _ ->
            City.save(this, City.cities[position])
            finish()
        }
        findViewById<Button>(R.id.back).setOnClickListener { finish() }
    }
}
