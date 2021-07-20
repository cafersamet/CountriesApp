package com.smart.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.smart.countriesapp.database.CountryDatabase
import com.smart.countriesapp.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(application: Application) : BaseViewModel(application){
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}