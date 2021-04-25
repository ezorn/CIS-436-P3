package com.cis436.project3weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SingleViewModel : ViewModel (){

    val dailyTemp: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }

}