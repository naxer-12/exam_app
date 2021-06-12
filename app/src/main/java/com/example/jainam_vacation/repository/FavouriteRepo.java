package com.example.jainam_vacation.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.jainam_vacation.models.CountryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteRepo {
    private final String TAG  = "FIREBASE";
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "Favourite";
    public MutableLiveData<List<CountryModel>> allCountry = new MutableLiveData<List<CountryModel>>();


    public FavouriteRepo(){
        db = FirebaseFirestore.getInstance();
    }

    public void addCountry(CountryModel countryModel){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put("name", countryModel.getName());
            data.put("countryCode", countryModel.getCountryCode());
            data.put("capital", countryModel.getCapital());


            db.collection(COLLECTION_NAME)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Document added successfully" + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error creating document on Firestore" + e.getLocalizedMessage() );
                        }
                    });



        }catch(Exception ex){
            Log.e(TAG, "addFriend: " + ex.getLocalizedMessage() );
        }
    }

    public void deleteCountries(String docID){
        try{
            db.collection(COLLECTION_NAME)
                    .document(docID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document successfully deleted");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Unable to delete document" + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "deleteFriend: Unable to delete document " + ex.getLocalizedMessage() );
        }
    }
    public void getAllCountries(){
        try{
            db.collection(COLLECTION_NAME)
                    .orderBy("name", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e(TAG, "onEvent: Listening to collection failed due to some reason " + error );
                                return;
                            }

                            List<CountryModel> favouriteList = new ArrayList<>();

                            if (value.isEmpty()){
                                Log.d(TAG, "onEvent: No documents (change) in the collection");
                            }else{
                                //we have document(changes) within the collection
                                Log.d(TAG, "onEvent: Current data : " + value.getDocumentChanges());

                                for (DocumentChange docChange: value.getDocumentChanges()){
                                    CountryModel currentCountryModel = docChange.getDocument().toObject(CountryModel.class);
                                    currentCountryModel.setId(docChange.getDocument().getId());



                                    switch (docChange.getType()){
                                        case ADDED:
                                            //do some operation for new document
                                            favouriteList.add(currentCountryModel);
                                            break;
                                        case MODIFIED:
                                            // do some operation for updated document
                                            //search for existing object in the list and replace it with updated one
                                            break;
                                        case REMOVED:
                                            //do some operation for deleted document
                                            favouriteList.remove(currentCountryModel);
                                            break;
                                    }
                                }
                            }//else

                            Log.d(TAG, "onEvent: countryList " + favouriteList.toString());
                            allCountry.postValue(favouriteList);
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "countryList: " + ex.getLocalizedMessage());
        }
    }
}