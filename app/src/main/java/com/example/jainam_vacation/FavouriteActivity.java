package com.example.jainam_vacation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.jainam_vacation.adapter.FavouriteAdapter;
import com.example.jainam_vacation.databinding.ActivityFavouriteBinding;
import com.example.jainam_vacation.databinding.ActivityMainBinding;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.viewmodels.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    ActivityFavouriteBinding binding;
    private final String TAG = "ABC";

    FavouriteAdapter adapter;
    private FavouriteViewModel favouriteViewModel;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.favouriteViewModel = FavouriteViewModel.getInstance(this.getApplication());
        this.favouriteViewModel.allCountry.observe(this, new Observer<List<CountryModel>>() {
            @Override
            public void onChanged(List<CountryModel> countryModelList) {
                if (countryModelList != null) {
                    for (CountryModel coModel : countryModelList) {
                        countryList.clear();
                        countryList.add(coModel);
                        Log.d(TAG, "CLICKED" + countryModelList.size());

                    }
                }
            }
        });

        adapter = new FavouriteAdapter(this, countryList);

        binding.favCountryList.setAdapter(adapter);

        binding.favCountryList.setLayoutManager(new LinearLayoutManager(this));

    }
}