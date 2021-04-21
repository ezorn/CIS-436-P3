package com.cis436.project3weather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log

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

class MainActivity : AppCompatActivity() {

    private var requestQueue: RequestQueue? = null
    private final val API_KEY : String = "42baaa2f9a6308d59c4f77954ec6fea4"

    var dailyWeather : JSONObject? = null
    var day1Weather : JSONObject? = null
    var day2Weather : JSONObject? = null
    var day3Weather : JSONObject? = null
    var day4Weather : JSONObject? = null
    var day5Weather : JSONObject? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this)

        var zipCode : Int = 48302;
        var units : String = "imperial"

        // format URL by zipcode (defaul to US)
        // also will bring back data in imperial units
        var urlDay : String = "https://api.openweathermap.org/data/2.5/weather?zip=$zipCode&units=$units&appid=$API_KEY"
        var urlWeek : String = "https://api.openweathermap.org/data/2.5/forecast?zip=$zipCode&units=$units&&appid=$API_KEY"

        //create object request for single day weather
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,  //the request method
                urlDay,
                null,
                Response.Listener { response ->
                    //this prints the WHOLE string
                    Log.i("JSON response", response.toString());
                    try {
                        //get description of weather
                        val weather : JSONArray = response.getJSONArray("weather")
                        val mainObj : JSONObject = response.getJSONObject("main")
                        //since it's one day of weather,
                        // there's one object in the array
                        dailyWeather = weather.getJSONObject(0)
                        val id = dailyWeather?.getInt("id")
                        val mainWeather = dailyWeather?.getString("main")
                        val description = dailyWeather?.getString("description")
                        val icon = dailyWeather?.getString("icon")
                        val temp : Int? = mainObj.getInt("temp")
                        Log.i("JSON info", "ID: $id")
                        Log.i("JSON info", "main weather: $mainWeather")
                        Log.i("JSON info", "Description: $description")
                        Log.i("JSON info", "Icon: $icon")
                        Log.i("JSON info", "Temp: $temp")
                    } catch (ex: JSONException) {
                        Log.e("JSON Error", ex.message!!)
                    }
                },
                Response.ErrorListener { }
        ) //end of JSON object request
        requestQueue!!.add(jsonObjectRequest)


        //create object request for multi day weather
        val jsonObjectRequestWeek = JsonObjectRequest(
            Request.Method.GET,  //the request method
            urlWeek,
            null,
            Response.Listener { response ->
                //this prints the WHOLE string
                Log.i("JSON week response", response.toString());
                try {
                    //get description of weather
                    val forecast : JSONArray = response.getJSONArray("list")
                    // we want a 5 day forecast at the same time each day, so we first
                    // get an element for each day
                    var firstElement : JSONObject = forecast.getJSONObject(5)
                    var secondElement = forecast.getJSONObject(13)
                    var thirdElement = forecast.getJSONObject(21)
                    var fourthElement : JSONObject = forecast.getJSONObject(29)
                    var fifthElement : JSONObject = forecast.getJSONObject(37)

                    // Each object only has one weather array, so we get that from the element
                    var weatherArray1 : JSONArray = firstElement.getJSONArray("weather")
                    var weatherArray2 : JSONArray = secondElement.getJSONArray("weather")
                    var weatherArray3 : JSONArray = thirdElement.getJSONArray("weather")
                    var weatherArray4 : JSONArray = fourthElement.getJSONArray("weather")
                    var weatherArray5 : JSONArray = fifthElement.getJSONArray("weather")

                    // There's only one weather object in the array, so let's grab that
                    day1Weather = weatherArray1.getJSONObject(0)
                    day2Weather = weatherArray2.getJSONObject(0)
                    day3Weather = weatherArray3.getJSONObject(0)
                    day4Weather = weatherArray4.getJSONObject(0)
                    day5Weather = weatherArray5.getJSONObject(0)

                    // Print to info log
                    Log.i("Day 1 Weather", day1Weather.toString());
                    Log.i("Day 2 Weather", day2Weather.toString());
                    Log.i("Day 3 Weather", day3Weather.toString());
                    Log.i("Day 4 Weather", day4Weather.toString());
                    Log.i("Day 5 Weather", day5Weather.toString());


                } catch (ex: JSONException) {
                    Log.e("JSON Error", ex.message!!)
                }
            },
            Response.ErrorListener { }
        ) //end of JSON object request
        requestQueue!!.add(jsonObjectRequestWeek)
    } //end onCreate
}