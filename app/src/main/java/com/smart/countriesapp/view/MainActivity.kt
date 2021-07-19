package com.smart.countriesapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smart.countriesapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}