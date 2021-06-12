package com.example.jainam_vacation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.jainam_vacation.databinding.ActivityHomeCountryBinding;
import com.example.jainam_vacation.helper.LocationHelper;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.network.RetrofitClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCountryActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    ActivityHomeCountryBinding binding;
    private LocationHelper locationHelper;
    private Location lastLocation;
    private LocationCallback locationCallback;
    private Address obtainedAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityHomeCountryBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.locationHelper = LocationHelper.getInstance();
        this.locationHelper.checkPermissions(this);


        if (this.locationHelper.locationPermissionGranted) {
            Log.d(TAG, "onCreate: Location Permission Granted");


            this.initiateLocationListener();

            this.locationHelper.getLastLocation(this).observe(this, new Observer<Location>() {
                @Override
                public void onChanged(Location location) {
                    if (location != null) {
                        lastLocation = location;

                         obtainedAddress = locationHelper.getAddress(getApplicationContext(), lastLocation);
                        getCountryList(obtainedAddress.getCountryCode());

//                        Log.d(TAG, "onCreate: Last Location obtained " + obtainedAddress.isEmpty());
                    }
                }
            });

        }
    }

    private void getCountryList(String countryCode) {

        Call<CountryModel> countryListCall = RetrofitClient.getInstance().getApi().retrieveCountry(countryCode);


        try {
            countryListCall.enqueue(new Callback<CountryModel>() {
                @Override
                public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {
                    Log.d(TAG, "onResponse API: " + response.isSuccessful());
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponseC: " + response.body());

                        CountryModel countriesFromApi = response.body();
                        Log.d(TAG, "onResponseC: " + countriesFromApi);

                        binding.countryTitle.setText("COUNTRY");
                        binding.countryCapital.setText("CAPITAL");
                        binding.population.setText("POPULATION");
                        binding.countryCurrency.setText("CURRENCY");
                        binding.language.setText("LANGUAGE");

                        binding.countryTitleValue.setText(countriesFromApi.getName());
                        binding.countryCapitalValue.setText(countriesFromApi.getCapital());
                        binding.populationValue.setText(String.valueOf(countriesFromApi.getPopulation()));
                        binding.countryCurrencyValue.setText(countriesFromApi.getCurrencies().get(0).getName());
                        binding.languageValue.setText(countriesFromApi.getLanguages().get(0).getName());
                        Glide.with(getApplicationContext())
                                .load("https://www.countryflags.io/" + countryCode + "/flat/64.png")
                                .placeholder(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_media_next))
                                .into(binding.countryIcon);

                        countryListCall.cancel();
                    }
                }

                @Override
                public void onFailure(Call<CountryModel> call, Throwable t) {
                    Log.e(TAG, "onFailure --- getCategoryList: Exception occurred while getting Countries ");
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "getCategoryList: Exception occurred while getting categories " + ex.getLocalizedMessage());
        }

    }

    private void initiateLocationListener() {
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location loc : locationResult.getLocations()) {
                    lastLocation = loc;

                    Log.e(TAG, "onLocationResult: update location " + loc.toString());
                }
            }
        };

        this.locationHelper.requestLocationUpdates(this, this.locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == this.locationHelper.REQUEST_CODE_LOCATION) {
            this.locationHelper.locationPermissionGranted = (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);

            if (this.locationHelper.locationPermissionGranted) {

                Log.d(TAG, "onCreate: Location Permission Granted - " + this.locationHelper.locationPermissionGranted);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHelper.stopLocationUpdates(this, this.locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHelper.requestLocationUpdates(this, this.locationCallback);
    }
}