package com.smart.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.smart.countriesapp.database.CountryDatabase
import com.smart.countriesapp.model.Country
import com.smart.countriesapp.service.CountryApiService
import com.smart.countriesapp.util.CustomSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    application: Application,
    private val countryApiService: CountryApiService
) : BaseViewModel(application) {
    val countries = MutableLiveData<List<Country>>()
    private val disposable = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L


    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        countryLoading.value = true
        val updateTime = customSharedPreferences.getTime();
        if (updateTime != null && updateTime != 0L
            && System.nanoTime() - updateTime < refreshTime
        ) {
            getDataFromRoom()
        } else {
            refreshFromAPI()
        }

    }

    fun refreshFromAPI() {
        getDataFromApi()
    }

    private fun getDataFromRoom() {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val countries = dao.getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From Room Database", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getDataFromApi() {
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        storeInRoom(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInRoom(countryList: List<Country>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listIDs = dao.insertAll(*countryList.toTypedArray())  //-> individual
            val newList = dao.getAllCountries()
            showCountries(newList)
        }
        customSharedPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}