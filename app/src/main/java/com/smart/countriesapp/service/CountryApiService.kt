package com.smart.countriesapp.service

import com.smart.countriesapp.model.Country
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {
    // https://raw.githubusercontent.com
    // atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    private val baseURL = "https://raw.githubusercontent.com"

    private val api = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryApi::class.java)

    fun getData() : Single<List<Country>> {
        return  api.getCountries()
    }
}