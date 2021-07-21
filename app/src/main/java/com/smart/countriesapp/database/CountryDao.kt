package com.smart.countriesapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smart.countriesapp.model.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg countries: Country): List<Long>
    //suspend -> coroutine, pause & resume(kendi kendine optimize ediyor)
    // vararg -> multiple country alıyor
    // primary keys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: Country): Long

    @Query("Select * from Country")
    suspend fun getAllCountries(): List<Country>

    @Query("Select * from Country where uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("Delete from Country")
    suspend fun deleteAllCountries()
}