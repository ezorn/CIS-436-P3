package com.cis436.project3weather.forecast

import androidx.lifecycle.ViewModelProvider
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

class FiveDayForecastFragment : Fragment() {

    companion object {
        fun newInstance() = FiveDayForecastFragment()
    }

    private lateinit var viewModel: FiveDayForecastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.five_day_forecast_fragment, container, false)

        // Get view references
        var dayTxt1 : TextView = view.findViewById(R.id.dayBox1)
        var dayTxt2 : TextView = view.findViewById(R.id.dayBox2)
        var dayTxt3 : TextView = view.findViewById(R.id.dayBox3)
        var dayTxt4 : TextView = view.findViewById(R.id.dayBox4)
        var dayTxt5 : TextView = view.findViewById(R.id.dayBox5)

        var tempTxt1 : TextView = view.findViewById(R.id.tempBox1)
        var tempTxt2 : TextView = view.findViewById(R.id.tempBox2)
        var tempTxt3 : TextView = view.findViewById(R.id.tempBox3)
        var tempTxt4 : TextView = view.findViewById(R.id.tempBox4)
        var tempTxt5 : TextView = view.findViewById(R.id.tempBox5)

        var weather1JSON : JSONObject? = (activity as MainActivity).day1Weather
        var weather2JSON : JSONObject? = (activity as MainActivity).day2Weather
        var weather3JSON : JSONObject? = (activity as MainActivity).day3Weather
        var weather4JSON : JSONObject? = (activity as MainActivity).day4Weather
        var weather5JSON : JSONObject? = (activity as MainActivity).day5Weather

        var temp1 : Int? = (activity as MainActivity).day1Temp
        var temp2 : Int? = (activity as MainActivity).day2Temp
        var temp3 : Int? = (activity as MainActivity).day3Temp
        var temp4 : Int? = (activity as MainActivity).day4Temp
        var temp5 : Int? = (activity as MainActivity).day5Temp

        var dt1 = (activity as MainActivity).day1dt
        var dt2 = (activity as MainActivity).day2dt
        var dt3 = (activity as MainActivity).day3dt
        var dt4 = (activity as MainActivity).day4dt
        var dt5 = (activity as MainActivity).day5dt

        // Get icon file name -- the value that dailyIcon equals needs to go
        // R.drawable.HERE, but I am still figuring out how to retrieve it

        // Update UI
        dayTxt1.text = dt1
        dayTxt2.text = dt2
        dayTxt3.text = dt3
        dayTxt4.text = dt4
        dayTxt5.text = dt5

        tempTxt1.text = temp1.toString()
        tempTxt2.text = temp2.toString()
        tempTxt3.text = temp3.toString()
        tempTxt4.text = temp4.toString()
        tempTxt5.text = temp5.toString()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FiveDayForecastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}