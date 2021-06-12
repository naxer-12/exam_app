package com.example.jainam_vacation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jainam_vacation.adapter.VacationAdapter;
import com.example.jainam_vacation.databinding.ActivityMainBinding;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "ABC";


    ActivityMainBinding binding;


    VacationAdapter adapter;
    ArrayList<CountryModel> countryList = new ArrayList<CountryModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new VacationAdapter(this, countryList);

        binding.countryList.setAdapter(adapter);

        binding.countryList.setLayoutManager(new LinearLayoutManager(this));

        this.getCountryList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favourite:
                Log.d(TAG, "FAVOURITE CLICKED");
                Intent intent = new Intent(getApplicationContext(), FavouriteActivity.class);
                // intent.putExtra("product", selectedProduct);

                startActivity(intent);
                break;
            case R.id.homeCountry:
                Log.d(TAG, "HOME CLICKED");
                Intent intent2 = new Intent(getApplicationContext(), HomeCountryActivity.class);
                // intent.putExtra("product", selectedProduct);

                startActivity(intent2);
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    private void getCountryList() {

        Call<List<CountryModel>> countryListCall = RetrofitClient.getInstance().getApi().retrieveCountryList();


        try {
            countryListCall.enqueue(new Callback<List<CountryModel>>() {
                @Override
                public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                    Log.d(TAG, "onResponse: " + response.isSuccessful());
                    if (response.isSuccessful()) {

                        ArrayList<CountryModel> countriesFromApi = (ArrayList<CountryModel>) response.body();

                        Log.d(TAG, "onResponse: " + countriesFromApi.size());
                        countryList.clear();

                        countryList.addAll(countriesFromApi);

                        adapter.notifyDataSetChanged();


                        countryListCall.cancel();
                    }
                }

                @Override
                public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                    Log.e(TAG, "onFailure --- getCategoryList: Exception occurred while getting categories ");
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "getCategoryList: Exception occurred while getting categories " + ex.getLocalizedMessage());
        }

    }
}