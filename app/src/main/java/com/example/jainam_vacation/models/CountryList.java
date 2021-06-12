package com.example.jainam_vacation.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryList {
    public ArrayList<CountryModel> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountryModel> countryList) {
        this.countryList = countryList;
    }

    private ArrayList<CountryModel> countryList;


}
