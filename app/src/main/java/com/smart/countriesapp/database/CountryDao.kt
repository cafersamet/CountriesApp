package com.smart.countriesapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smart.countriesapp.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>
    //suspend -> coroutine, pause & resume(kendi kendine optimize ediyor)
    // vararg -> multiple country alıyor
    // primary keys

    @Query("Select * from Country")
    suspend fun getAllCountries(): List<Country>

    @Query("Select * from Country where uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("Delete from Country")
    suspend fun deleteAllCountries()
}