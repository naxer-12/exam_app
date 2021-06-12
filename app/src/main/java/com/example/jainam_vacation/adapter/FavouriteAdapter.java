package com.example.jainam_vacation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.jainam_vacation.databinding.RowLayoutBinding;
import com.example.jainam_vacation.helper.CurrentApplication;
import com.example.jainam_vacation.helper.OnItemClickListener;
import com.example.jainam_vacation.helper.OnItemClickRemover;
import com.example.jainam_vacation.models.CountryModel;
import com.example.jainam_vacation.viewholder.FavouriteViewHolder;
import com.example.jainam_vacation.viewholder.ViewHolder;
import com.example.jainam_vacation.viewmodels.FavouriteViewModel;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private final String TAG = "ABC";

    private ArrayList<CountryModel> countryList;
    private Context context;
    private FavouriteViewModel favouriteViewModel;
    private CountryModel countryModel;
    private OnItemClickRemover clickRemover;

    public FavouriteAdapter(Context context, ArrayList<CountryModel> countryList) {
        this.countryList = countryList;
        this.context = context;
        this.favouriteViewModel = FavouriteViewModel.getInstance(CurrentApplication.getInstance());

        clickRemover = new OnItemClickRemover() {

            @Override
            public void removeItem(int position) {
                countryModel = countryList.get(position);
                favouriteViewModel.removeCountry(countryModel);
                notifyDataSetChanged();

            }
        };

    }


    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(this.context), viewGroup, false);
        return new FavouriteViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavouriteViewHolder viewHolder, final int position) {

        CountryModel countryModel = this.countryList.get(position);
        viewHolder.bind(this.context, countryModel, clickRemover);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.countryList.size();
    }


}
