package com.cis436.project3weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import com.cis436.project3weather.ui.main.MainFragment
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private var requestQueue: RequestQueue? = null

    // Utilities
    val degreeUnicode = "\u00b0"
    val units = "imperial"
    private val API_KEY : String = "42baaa2f9a6308d59c4f77954ec6fea4"

    // Daily forecast values
    var dailyWeather : JSONObject? = null
    var dailyTemp : Int? = null

    // Weekly forecast values
    var day1Weather : JSONObject? = null
    var day2Weather : JSONObject? = null
    var day3Weather : JSONObject? = null
    var day4Weather : JSONObject? = null
    var day5Weather : JSONObject? = null

    var day1Temp : Int? = null
    var day2Temp : Int? = null
    var day3Temp : Int? = null
    var day4Temp : Int? = null
    var day5Temp : Int? = null

    var day1dt : String? = null
    var day2dt : String? = null
    var day3dt : String? = null
    var day4dt : String? = null
    var day5dt : String? = null

    // format URL by zipcode (default to US)
    // also will bring back data in imperial units
    fun getDailyWeather(zipCode : Int) {
        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this)

        var urlDay : String = "https://api.openweathermap.org/data/2.5/weather?zip=$zipCode&units=$units&appid=$API_KEY"

        //create object request for single day weather
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,  //the request method
            urlDay,
            null,
            Response.Listener { response ->
                //this prints the WHOLE string
                Log.i("JSON response", response.toString())
                try {
                    // get description of weather and temperature
                    val weather : JSONArray = response.getJSONArray("weather")
                    val mainObj : JSONObject = response.getJSONObject("main")
                    //since it's one day of weather, there's one object in the array
                    dailyWeather = weather.getJSONObject(0)
                    val id = dailyWeather?.getInt("id")
                    val mainWeather = dailyWeather?.getString("main")
                    val description = dailyWeather?.getString("description")
                    val icon = dailyWeather?.getString("icon")
                    dailyTemp = mainObj.getInt("temp")
                    Log.i("JSON info", "ID: $id")
                    Log.i("JSON info", "main weather: $mainWeather")
                    Log.i("JSON info", "Description: $description")
                    Log.i("JSON info", "Icon: $icon")
                    Log.i("JSON info", "Temp: $dailyTemp")
                } catch (ex: JSONException) {
                    Log.e("JSON Error", ex.message!!)
                }
            },
            Response.ErrorListener { }
        ) //end of JSON object request
        requestQueue!!.add(jsonObjectRequest)
    }

    fun getWeeklyForecast(zipCode: Int) {
        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this)

        var urlWeek : String = "https://api.openweathermap.org/data/2.5/forecast?zip=$zipCode&units=$units&&appid=$API_KEY"

        //create object request for multi day weather
        val jsonObjectRequestWeek = JsonObjectRequest(
            Request.Method.GET,  //the request method
            urlWeek,
            null,
            Response.Listener { response ->
                //this prints the WHOLE string
                Log.i("JSON week response", response.toString())
                try {
                    //get description of weather
                    val forecast : JSONArray = response.getJSONArray("list")

                    // Pick index to choose a time on the first day to get the weather
                    var timeIndex : Int = 0

                    // we want a 5 day forecast at the same time each day, so we first
                    // get an element for each day by incrementing the time index by 8
                    val firstElement : JSONObject = forecast.getJSONObject(timeIndex)
                    val secondElement = forecast.getJSONObject(timeIndex + 8)
                    val thirdElement = forecast.getJSONObject(timeIndex + 16)
                    val fourthElement : JSONObject = forecast.getJSONObject(timeIndex + 24)
                    val fifthElement : JSONObject = forecast.getJSONObject(timeIndex + 32)

                    // Each object only has one weather array, so we get that from the element
                    val weatherArray1 : JSONArray = firstElement.getJSONArray("weather")
                    val weatherArray2 : JSONArray = secondElement.getJSONArray("weather")
                    val weatherArray3 : JSONArray = thirdElement.getJSONArray("weather")
                    val weatherArray4 : JSONArray = fourthElement.getJSONArray("weather")
                    val weatherArray5 : JSONArray = fifthElement.getJSONArray("weather")

                    // Get the main obj for temperature
                    val mainObj1 : JSONObject = firstElement.getJSONObject("main")
                    val mainObj2 : JSONObject = secondElement.getJSONObject("main")
                    val mainObj3 : JSONObject = thirdElement.getJSONObject("main")
                    val mainObj4 : JSONObject = fourthElement.getJSONObject("main")
                    val mainObj5 : JSONObject = fifthElement.getJSONObject("main")

                    // Get the date
                    day1dt = firstElement.getString("dt_txt")
                    day2dt = secondElement.getString("dt_txt")
                    day3dt = thirdElement.getString("dt_txt")
                    day4dt = fourthElement.getString("dt_txt")
                    day5dt = fifthElement.getString("dt_txt")

                    // There's only one weather object in the array, so let's grab that
                    day1Weather = weatherArray1.getJSONObject(0)
                    day2Weather = weatherArray2.getJSONObject(0)
                    day3Weather = weatherArray3.getJSONObject(0)
                    day4Weather = weatherArray4.getJSONObject(0)
                    day5Weather = weatherArray5.getJSONObject(0)

                    // Get the temperature for each day
                    day1Temp = mainObj1.getInt("temp")
                    day2Temp = mainObj2.getInt("temp")
                    day3Temp = mainObj3.getInt("temp")
                    day4Temp = mainObj4.getInt("temp")
                    day5Temp = mainObj5.getInt("temp")

                    // Print to info log
                    Log.i("Day 1: ", day1dt.toString())
                    Log.i("Day 1 Weather", day1Weather.toString())
                    Log.i("Day 1 Temp", day1Temp.toString())
                    Log.i("Day 2: ", day2dt.toString())
                    Log.i("Day 2 Weather", day2Weather.toString())
                    Log.i("Day 2 Temp", day2Temp.toString())
                    Log.i("Day 3: ", day3dt.toString())
                    Log.i("Day 3 Weather", day3Weather.toString())
                    Log.i("Day 3 Temp", day3Temp.toString())
                    Log.i("Day 4: ", day4dt.toString())
                    Log.i("Day 4 Weather", day4Weather.toString())
                    Log.i("Day 4 Temp", day4Temp.toString())
                    Log.i("Day 5: ", day5dt.toString())
                    Log.i("Day 5 Weather", day5Weather.toString())
                    Log.i("Day 5 Temp", day5Temp.toString())


                } catch (ex: JSONException) {
                    Log.e("JSON Error", ex.message!!)
                }
            },
            Response.ErrorListener { }
        ) //end of JSON object request
        requestQueue!!.add(jsonObjectRequestWeek)
    }

    // Take dt_text string from OpenWeather API and format it to be the date we want on the forecast screen
    fun formatDate(dt : String): String {
        // dt_text string format: "YYYY-MM-DD HH:MM:SS"
        // Take dt_text string and split date & time
        val delim1 = " "
        val list1 = dt.split(delim1)   // [ date, time ]

        // Split year, month, day
        val delim2 = "-"
        val dateString = list1[0]
        val dateList = dateString.split(delim2) // [ YYYY, MM, DD ]

        // Trim any leading zeroes from the month
        val month = dateList[1].trimStart('0')
        val day = dateList[2]

        // Assemble final formatted string
        val cleanDate : String = month + "/" + day

        return cleanDate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
        getDailyWeather(48302)
        loadWeeklyForecast(48302)
    } //end onCreate

    // FUNCTION: get zipcode from user input and return as integer

    // FUNCTION: load daily weather
        // get zip code
        // getDailyWeather(userZipCode)
        // Get weather data
            // mainWeather = dailyWeather.getString("main")
            // description = dailyWeather.getString("description")
            // icon = dailyWeather.getString("icon")

    // FUNCTION: load 5-day weather
        // get zipcode
        // getWeeklyForecast(userZipCode)
        // Get weather data
            // dates -- global variables
                // format them with formatDate()
            // temp -- global variable -- format as string
            // icon -- get from each day's weather object
                // dayXweather.getString("icon")

    fun loadWeeklyForecast(zipCode : Int) {
        // Get the weekly forecast
        getWeeklyForecast(zipCode)

        // Initialize the TextViews for dates & temperatures
        var day1Date : TextView = findViewById(R.id.DayBox1)
        var day2Date : TextView = findViewById(R.id.DayBox2)
        var day3Date : TextView = findViewById(R.id.DayBox3)
        var day4Date : TextView = findViewById(R.id.DayBox4)
        var day5Date : TextView = findViewById(R.id.DayBox5)

        var day1Temp : TextView = findViewById(R.id.TempaturesBox1)
        var day2Temp : TextView = findViewById(R.id.TempaturesBox2)
        var day3Temp : TextView = findViewById(R.id.TempaturesBox3)
        var day4Temp : TextView = findViewById(R.id.TempaturesBox4)
        var day5Temp : TextView = findViewById(R.id.TempaturesBox5)

        // Format dates for UI
        var dates = arrayOf(day1dt, day2dt, day3dt, day4dt, day5dt)
        var index : Int = 0
        while (index < 5) {
            var formattedDate : String = formatDate(dates[index].toString())
            dates[index] = formattedDate
        }

        // Populate TextViews with formatted dates
        day1Date.setText(dates[0])
        day2Date.setText(dates[1])
        day3Date.setText(dates[2])
        day4Date.setText(dates[3])
        day5Date.setText(dates[4])

    }

}