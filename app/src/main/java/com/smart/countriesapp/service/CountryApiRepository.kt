package com.smart.countriesapp.service

import com.smart.countriesapp.model.Country
import io.reactivex.Single
import javax.inject.Inject

class CountryApiRepository @Inject constructor(private val api: CountryApi) {
    fun getData(): Single<List<Country>> {
        return api.getCountries()
    }
}