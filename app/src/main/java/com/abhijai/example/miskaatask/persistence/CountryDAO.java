package com.abhijai.example.miskaatask.persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhijai.example.miskaatask.models.CountryResponse;

import java.util.List;

@Dao
public interface CountryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertCountries(List<CountryResponse>  countryResponses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(CountryResponse countryResponse);

    @Query("SELECT * FROM country_table")
    LiveData<List<CountryResponse>> getCountriesFromLocalDatabase();

    @Query("SELECT * FROM country_table LIMIT 1")
    CountryResponse[] doesDbHasAnyCountry();

    @Query("SELECT * FROM country_table WHERE name=:countryName LIMIT 1")
    LiveData<CountryResponse> getCountryByName(String countryName);

    @Query("DELETE FROM country_table")
    int deleteAllData();
}
