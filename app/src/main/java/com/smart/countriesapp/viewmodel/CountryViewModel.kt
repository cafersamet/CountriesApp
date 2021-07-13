package com.smart.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.smart.countriesapp.database.CountryDatabase
import com.smart.countriesapp.model.Country
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : BaseViewModel(application){
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}