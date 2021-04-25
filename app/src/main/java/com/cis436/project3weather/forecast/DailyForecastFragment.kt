package com.cis436.project3weather.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.cis436.project3weather.MainActivity
import com.cis436.project3weather.R
import org.json.JSONObject
import org.w3c.dom.Text


class DailyForecastFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daily_forecast, container, false)

        // Get values to set daily view fragment
        var dailyWeather : JSONObject? = (activity as MainActivity).dailyWeather
        val mainDailyWeather : String? = dailyWeather?.getString("main")
        val dailyIcon : String? = "icon_" + dailyWeather?.getString("icon")
        val dailyTemp : Int? = (activity as MainActivity).dailyTemp
        val dailyDescription : String? = dailyWeather?.getString("description")
        val dailyLocation : String? = (activity as MainActivity).dailyLocation

        // Get view references
        var cityName : TextView = view.findViewById(R.id.cityNameDaily)
        var weatherIconView : ImageView = view.findViewById(R.id.dailyWeatherIcon)
        var tempView : TextView = view.findViewById(R.id.dailyTempView)
        var weatherView : TextView = view.findViewById(R.id.dailyWeatherView)
        var descriptionView : TextView = view.findViewById(R.id.dailyDescriptionView)

        // Get icon file name -- the value that dailyIcon equals needs to go
        // R.drawable.HERE, but I am still figuring out how to retrieve it

        // Update UI
        cityName.text = dailyLocation
        // assign imageView to R.drawable.icon_0
        weatherIconView.setImageResource(R.drawable.icon_01d)
        tempView.text = dailyTemp.toString() + "˚"
        weatherView.text = mainDailyWeather
        descriptionView.text = dailyDescription

        return view
    }
}