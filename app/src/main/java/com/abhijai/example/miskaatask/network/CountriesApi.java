package com.abhijai.example.miskaatask.network;

import androidx.lifecycle.LiveData;

import com.abhijai.example.miskaatask.models.CountryResponse;
import com.abhijai.example.miskaatask.util.ApiResponse;

import java.util.List;

import retrofit2.http.GET;

public interface CountriesApi {
    //https://restcountries.eu/rest/v2/region/asia
    public static final String BASE_URL = "https://restcountries.eu/rest/v2/";
    @GET("region/asia")
    LiveData<ApiResponse<List<CountryResponse>>> getCountriesFromNetwork();
}
