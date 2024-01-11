package com.example.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: Country) :  List<Long>

    // insert -> insert into
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys

    @Query("Select * from country")
    suspend fun getAllCountries() : List<Country>

    @Query("Select * from country where uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("Delete from country")
    suspend fun deleteAllCountries()
}