package com.smart.countriesapp.di

import android.content.Context
import androidx.room.Room
import com.smart.countriesapp.database.CountryDatabase
import com.smart.countriesapp.service.CountryApi
import com.smart.countriesapp.service.CountryApiRepository
import com.smart.countriesapp.util.baseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCountryRepository(api : CountryApi) : CountryApiRepository{
        return CountryApiRepository(api)
    }

    @Singleton
    @Provides
    fun provideCountryApi() : CountryApi {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCountryDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        CountryDatabase::class.java,
        "countrydatabase"
    ).build()

    @Singleton
    @Provides
    fun provideCountryDao(countryDatabase: CountryDatabase) = countryDatabase.countryDao()
}