package com.example.da1_tpo.ui

import android.graphics.Color
import android.widget.TextView

class SplashActivity {
    private var textSplash: TextView


    fun start(title: String = "") {
        textSplash.text = title

    }


    init {

        textSplash.setTextColor(Color.WHITE)
    }

}