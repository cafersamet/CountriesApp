package com.smart.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smart.countriesapp.database.CountryDao
import com.smart.countriesapp.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val dao: CountryDao
) : ViewModel() {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int) {
        viewModelScope.launch {
            val country = dao.getCountry(uuid)
            countryLiveData.value = country
        }
    }
}