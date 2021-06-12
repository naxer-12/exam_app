package com.example.jainam_vacation.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.repository.FavouriteRepo;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private static FavouriteViewModel ourInstance;
    private final FavouriteRepo favRepo = new FavouriteRepo();
    public MutableLiveData<List<CountryModel>> allCountry;


    public static FavouriteViewModel getInstance(Application application) {
        if (ourInstance == null) {
            ourInstance = new FavouriteViewModel(application);
        }

        return ourInstance;
    }

    private FavouriteViewModel(Application application) {
        super(application);
        this.favRepo.getAllCountries();
        this.allCountry = this.favRepo.allCountry;
    }

    public void addCountry(CountryModel countryModel) {
        this.favRepo.addCountry(countryModel);
    }

    public void removeCountry(CountryModel countryModel) {
        this.favRepo.deleteCountries(countryModel.getId());
    }

}
