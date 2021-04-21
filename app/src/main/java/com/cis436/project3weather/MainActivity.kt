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

        //create object request
        val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,  //the request method
                "https://api.openweathermap.org/data/2.5/weather?q=London&appid=42baaa2f9a6308d59c4f77954ec6fea4",
                null,
                Response.Listener { response ->
                    //this prints the WHOLE string
                    //Log.i("JSON response", response.toString());
                    try {
                        //get description of weather
                        val weather = response.getJSONArray("weather")
                        //since it's one day of weather,
                        // there's one object in the array
                        val currentWeather = weather.getJSONObject(0)
                        val id = currentWeather.getInt("id")
                        val mainWeather = currentWeather.getString("main")
                        val description = currentWeather.getString("description")
                        val icon = currentWeather.getString("icon")
                        Log.i("JSON info", "ID: $id")
                        Log.i("JSON info", "main weather: $mainWeather")
                        Log.i("JSON info", "Description: $description")
                        Log.i("JSON info", "Icon: $icon")
                    } catch (ex: JSONException) {
                        Log.e("JSON Error", ex.message!!)
                    }
                },
                Response.ErrorListener { }
        ) //end of JSON object request
        requestQueue!!.add(jsonObjectRequest)
    } //end onCreate
}