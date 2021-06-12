package com.example.jainam_vacation.viewholder;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.jainam_vacation.databinding.RowLayoutBinding;
import com.example.jainam_vacation.helper.CurrentApplication;
import com.example.jainam_vacation.helper.OnItemClickListener;
import com.example.jainam_vacation.helper.OnItemClickRemover;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.network.API;
import com.example.jainam_vacation.viewmodels.FavouriteViewModel;

import java.util.List;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    private final String TAG = "ABC";

    private RowLayoutBinding binding;
    private FavouriteViewModel favouriteViewModel;
    private OnItemClickListener clickBack;


    public FavouriteViewHolder(RowLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
//        this.favouriteViewModel = FavouriteViewModel.getInstance(CurrentApplication.getInstance());

    }

    // update our view holder to use best practices
    public void bind(Context context, CountryModel countryData, OnItemClickRemover listener
    ) {
        // to associate the UI with your data
        this.binding.countryTitle.setText(countryData.getName() + "," + countryData.getCountryCode());
        this.binding.countryCapital.setText(countryData.getCapital());


        Glide.with(context)
                .load("https://www.countryflags.io/" + countryData.getCountryCode() + "/flat/64.png")
                .placeholder(ContextCompat.getDrawable(context, android.R.drawable.ic_media_next))
                .into(binding.countryIcon);
        this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Log.d("ABC", "Clicked on " + position);
                    listener.removeItem(position);
                }
            }
        });

    }


}
