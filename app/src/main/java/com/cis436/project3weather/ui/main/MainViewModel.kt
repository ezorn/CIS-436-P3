package com.cis436.project3weather.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    init {
        Log.i("MainViewModel", "Main view model created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainViewModel", "Main view model destroyed!")
    }
}