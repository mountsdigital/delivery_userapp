package com.mounts.lenovo.delivery3.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.Main;
import com.mounts.lenovo.delivery3.adapter.PlacesFieldSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Select_Source_DestinationFragment extends Fragment implements View.OnTouchListener ,View.OnClickListener{
    private final int AUTOCOMPLETE_REQUEST_CODE_FOR_SOURCE = 1;
    private final int AUTOCOMPLETE_REQUEST_CODE_FOR_DESTINATION = 2;
    private EditText sourceEdit;
    private EditText destinationEdit;
    private ListView listView;
    private List<String> placeList;
    private List<Place.Field> fields;
    private Toolbar toolbar;
    private String source =null;
    private String destination = null;
    private static LatLng soucLatLan;
    private static LatLng desLatLan;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private RectangularBounds bounds;
    private AutocompleteSessionToken token;
    private String query = null;
    private PlacesClient placesClient;
    private FindAutocompletePredictionsRequest request;
    public Select_Source_DestinationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.autocomplete_layout, container, false);
        if (!Places.isInitialized()) {
            Places.initialize(getContext(),"AIzaSyDr9069GHPYzaktXBLFhmCUWByZWhyP5ns");
        }
        // Create a new Places client instance
         placesClient = Places.createClient(getContext());

        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);

        sourceEdit = view.findViewById(R.id.sourceEditAddress);
        destinationEdit =view.findViewById(R.id.destinationEditAddress);
        sourceEdit.selectAll();
        sourceEdit.setFocusable(false);
        sourceEdit.requestFocus();
        listView = view.findViewById(R.id.listView);

        placeList = new ArrayList<String>();

        sourceEdit.setOnTouchListener(this);
        destinationEdit.setOnTouchListener(this);
        source = getArguments().getString("SOURCE_FROM_MAP");
        destination =getArguments().getString("DES_FROM_MAP");
        if(source != null){
            sourceEdit.setText(source);
        }
        if (destination != null){
            destinationEdit.setText(destination);
        }
        Main.toolbar.setVisibility(View.GONE);
       toolbar = view.findViewById(R.id.auto_complete_toolbar);
       toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
       toolbar.setNavigationOnClickListener(this);
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        token = AutocompleteSessionToken.newInstance();
        // Create a RectangularBounds object.
        bounds = RectangularBounds.newInstance(
                new LatLng(MapFragmentTEST.currentLocation.latitude, MapFragmentTEST.currentLocation.longitude),
                new LatLng(MapFragmentTEST.currentLocation.latitude,
                        MapFragmentTEST.currentLocation.longitude));
        // Use the builder to create a FindAutocompletePredictionsRequest.



        return view;
    }
    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        getActivity().getSupportFragmentManager().
                beginTransaction().replace(R.id.frame, fragment, BACK_STACK_ROOT_TAG).commit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_FOR_SOURCE) {
            if (resultCode ==AutocompleteActivity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("TAG3", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG4", "Place: " + place.getName() + ", " + place.getLatLng());
                sourceEdit.setText(place.getName());
              //  source = place.getName();
                soucLatLan = place.getLatLng();
                query = sourceEdit.getText().toString();
                setQuery(query);
              //  placeList.addAll(Collections.singleton(place.getName()));

                destinationEdit.requestFocus();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("RESULT_ERROR", status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if(requestCode == AUTOCOMPLETE_REQUEST_CODE_FOR_DESTINATION){
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                 Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("TAG5", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG6", "Place: " + place.getName() + ", " + place.getLatLng());
                destinationEdit.setText(place.getName());
             //   destination = place.getName();
                desLatLan = place.getLatLng();
                //destinationEdit.requestFocus();
                query = destinationEdit.getText().toString();
               setQuery(query);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("RESULT_ERROR", status.getStatusMessage());
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
public void setQuery(String query){
    request = FindAutocompletePredictionsRequest.builder()
            // Call either setLocationBias() OR setLocationRestriction().
            .setLocationBias(bounds)
            //.setLocationRestriction(bounds)
            .setCountry("MM")
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(token)
            .setQuery(query)
            .build();
    placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
        for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
            Log.e("TAG", prediction.getPlaceId());
            Log.e("TAG", prediction.getPrimaryText(null).toString());
            placeList.add(prediction.getPrimaryText(null).toString());

        }
    }).addOnFailureListener((exception) -> {
        if (exception instanceof ApiException) {
            ApiException apiException = (ApiException) exception;
            Log.e("TAG", "Place not found: " + apiException.getStatusCode());
        }
    });
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
            android.R.layout.simple_list_item_1,placeList);
    listView.setAdapter(adapter);

}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case R.id.sourceEditAddress:

                  /*  Intent intent = new Autocomplete.IntentBuilder(
                            AutocompleteActivityMode.OVERLAY, fields)
                            .setCountry("Myanmar")
                            .build(this);
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/
                    autoCompleteIntentBuilder(AUTOCOMPLETE_REQUEST_CODE_FOR_SOURCE);


                    break;
                case R.id.destinationEditAddress:
                    autoCompleteIntentBuilder(AUTOCOMPLETE_REQUEST_CODE_FOR_DESTINATION);
                    break;
                // Do what you want

            }
            return true;
        }
        return false;
    }
    public void autoCompleteIntentBuilder(int REQUEST_CODE){
        Intent autocompleteIntent =
                new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fields)
                        .setCountry("MM")
                        .setLocationBias(bounds)
                        .build(getContext());
        startActivityForResult(autocompleteIntent, REQUEST_CODE);
    }
    @Override
    public void onClick(View v) {
            source = sourceEdit.getText().toString();
            destination = destinationEdit.getText().toString();
        //What to do on back clicked
        Bundle bundle = new Bundle();
        if(source != null ){
            bundle.putString("SOURCE", source);

        } else if(sourceEdit.getText().toString().equals("")) {
            sourceEdit.setError("Please Select Address");
            sourceEdit.requestFocus();
        }

        if(destination != null){
            bundle.putString("DESTINATION",destination);

        }else if(destinationEdit.getText().toString().equals("")){
            destinationEdit.setError("Please Select Address");
            destinationEdit.requestFocus();

        }
        if(desLatLan != null){
            bundle.putDouble("des_Latitude",desLatLan.latitude);
            bundle.putDouble("des_Longitude",desLatLan.longitude);
        } if(soucLatLan != null){
            bundle.putDouble("souc_Latitude",soucLatLan.latitude);
            bundle.putDouble("souc_Longitude",soucLatLan.longitude);
        }


        // set Fragmentclass Arguments
        MapFragmentTEST mapFragmentTEST = new MapFragmentTEST();
        mapFragmentTEST.setArguments(bundle);
        setFragment(mapFragmentTEST);





    }
}
