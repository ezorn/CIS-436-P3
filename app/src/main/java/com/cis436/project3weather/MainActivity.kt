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

    var requestQueue = RequestQueue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        // API code
        // instantiate request
        requestQueue = Volley.newRequestQueue(this)

        // create object request
        val url = "https://api.openweathermap.org/data/2.5/weather?q=London&appid=42baaa2f9a6308d59c4f77954ec6fea4"
        JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        var weather : JSONArray = response.getJSONArray("weather");
                        // get description of weahter from the JSON object

                        // one day of weather -- get object 0 from the array
                        // multi day forecast -- iterate through array

                        // store current weather ID
                        // get main weather String
                        // get description String
                        // get icon String value -- use to pull icon file for imageViewer

                    } catch (e: JSONException){

                    }
                }
        )



    }
}