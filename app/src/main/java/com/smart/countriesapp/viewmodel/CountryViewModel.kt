package com.smart.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smart.countriesapp.model.Country

class CountryViewModel : ViewModel(){
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(){
        val country = Country("TR", "Asia", "Ankara", "TRY", "TR", "www.ss.com")
        countryLiveData.value = country
    }
}