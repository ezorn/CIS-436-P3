package com.cis436.project3weather

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.cis436.project3weather.forecast.DailyForecastFragment
import org.json.JSONObject
import java.lang.NumberFormatException

class LocationInputFragment : Fragment() {

    companion object {
        fun newInstance() = LocationInputFragment()
    }

    fun validateZipcodeInput(userInput : String) : Boolean {
        // make sure it is 5 characters
        if (userInput.length != 5) {
            return false
        }

        // make sure those characters are digits
        try {
            userInput.toInt()
            return true
        } catch (ex : NumberFormatException) {
            return false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.location_input_fragment, container, false)

        // update UI, get view references
        val zipcodeEditText : EditText = view.findViewById(R.id.zipcodeEditText)
        val dailyForecastBtn : Button = view.findViewById(R.id.dailyForecastBtn)
        val weeklyForecastBtn : Button = view.findViewById(R.id.weeklyForecastBtn)

        // Activate daily forecast button
        dailyForecastBtn.setOnClickListener {
            // Get user input zipcode
            var userInputZipcode : String = zipcodeEditText.text.toString()

            // Validate it
            var validInput : Boolean = validateZipcodeInput(userInputZipcode)
            var zipcode : Int? = null
            if (validInput) {
                // Convert user input to integer
                zipcode = userInputZipcode.toInt()
            }
            else {
                // quit out
                return@setOnClickListener
            }

            // Then, load forecast
            (activity as MainActivity).getDailyWeather(zipcode!!)
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.weatherFragmentContainer, DailyForecastFragment()).commit()
        }

        // Enable weekly forecast button
        weeklyForecastBtn.setOnClickListener {
            // Get user input zipcode
            var userInputZipcode : String = zipcodeEditText.text.toString()

            // Validate it
            var validInput : Boolean = validateZipcodeInput(userInputZipcode)
            var zipcode : Int? = null
            if (validInput) {
                // Convert user input to integer
                zipcode = userInputZipcode.toInt()
            }
            else {
                // quit out
                return@setOnClickListener
            }

            // Then, load forecast
            (activity as MainActivity).getWeeklyForecast(zipcode!!)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }


}