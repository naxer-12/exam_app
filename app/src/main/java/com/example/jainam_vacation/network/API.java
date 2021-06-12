package com.example.jainam_vacation.network;

import com.example.jainam_vacation.models.CountryList;
import com.example.jainam_vacation.models.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {

    String BASE_URL = "https://restcountries.eu/rest/v2/";

    @GET("./all")
    Call<List<CountryModel>> retrieveCountryList();

    @GET("./alpha/{countryCode}")
    Call<CountryModel> retrieveCountry(@Path("country") String countryCode);


}
