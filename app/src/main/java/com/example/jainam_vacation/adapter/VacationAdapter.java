package com.example.jainam_vacation.adapter;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.example.jainam_vacation.databinding.RowLayoutBinding;
import com.example.jainam_vacation.helper.CurrentApplication;
import com.example.jainam_vacation.helper.OnItemClickListener;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.viewholder.ViewHolder;
import com.example.jainam_vacation.viewmodels.FavouriteViewModel;

import java.util.ArrayList;

public class VacationAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final String TAG = "ABC";

    private ArrayList<CountryModel> countryList;
    private Context context;
    private FavouriteViewModel favouriteViewModel;
    private OnItemClickListener clickBack;
    private CountryModel countryModel;

    public VacationAdapter(Context context, ArrayList<CountryModel> countryList) {
        this.countryList = countryList;
        this.context = context;
        this.favouriteViewModel = FavouriteViewModel.getInstance(CurrentApplication.getInstance());

        clickBack = new OnItemClickListener() {


            @Override
            public void addItem(int position) {
                countryModel = countryList.get(position);
                favouriteViewModel.addCountry(countryModel);
                notifyDataSetChanged();
            }
        };

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(this.context), viewGroup, false);
        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        CountryModel countryModel = this.countryList.get(position);
        viewHolder.bind(this.context, countryModel, clickBack);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.countryList.size();
    }


}

