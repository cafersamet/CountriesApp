package com.smart.countriesapp.service

import com.smart.countriesapp.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryApi {

    // https://raw.githubusercontent.com
    // atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries() : Single<List<Country>>

}