package com.mounts.lenovo.delivery3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.PlacesFieldSelector;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.fragment.MapFragmentTEST;
import com.mounts.lenovo.delivery3.response.AddOrderInfo;
import com.mounts.lenovo.delivery3.response.GetTown;
import com.mounts.lenovo.delivery3.response.ReceiverCity;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;
import com.mounts.lenovo.delivery3.service.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextOrder extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener{
    private final int AUTOCOMPLETE_REQUEST_CODE_FOR_SOURCE = 1;
    private final int AUTOCOMPLETE_REQUEST_CODE_FOR_DESTINATION = 2;
    private AutoCompleteTextView sourceEdit;
    private EditText destinationEdit;
    private PlacesFieldSelector fieldSelector;
    private ListView listView;
    private List<String> placeList;
    private List<Place.Field> fields;
    private Toolbar toolbar;
    private String source =null;
    private String destination = null;
    private LatLng soucLatLan;
    private LatLng desLatLan;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete_layout);
        // Initialize the SDK
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),"AIzaSyDr9069GHPYzaktXBLFhmCUWByZWhyP5ns");
        }
        // Create a new Places client instance
            PlacesClient placesClient = Places.createClient(this);

        toolbar = findViewById(R.id.auto_complete_toolbar);
        setSupportActionBar(toolbar);
//    getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setHomeButtonEnabled(true);
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fieldSelector = new PlacesFieldSelector();
        fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);

        //sourceEdit = findViewById(R.id.sourceEditAddress);
        destinationEdit =findViewById(R.id.destinationEditAddress);
        listView = findViewById(R.id.listView);

        placeList = new ArrayList<String>();
       // placeList.addAll(fields);

        sourceEdit.setOnTouchListener(this);
        destinationEdit.setOnTouchListener(this);
        if(source != null){
            sourceEdit.setText(source);
        }
        if (destination != null){
            destinationEdit.setText(destination);
        }
        Main.toolbar.setVisibility(View.GONE);
        toolbar = findViewById(R.id.auto_complete_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(this);


         // Initialize the AutocompleteSupportFragment.
      //  sourceAutoComplete = findViewById(R.id.sourceEditAddress);
        /*AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        //autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
       //        new LatLng(21.989205, 96.074405), //21.989205, 96.074405
       //         new LatLng(33.858754, 76.229596)));

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
// Set up a PlaceSelectionListener to handle the response.
       autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.e("TAG1", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG2", "Place: " + place.getName() + ", " + place.getAddress());
                sourceEdit.setText(place.getName());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.e("TAG", "An error occurred: " + status);
            }
        });*/
       toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Bundle bundle = new Bundle();
                bundle.putString("edttext", "From Activity");
                int i =0;
                Log.e("Cliclk",String.format("%d",i++));
              // set Fragmentclass Arguments
               MapFragmentTEST mapFragmentTEST = new MapFragmentTEST();
                mapFragmentTEST.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.map, mapFragmentTEST).commit();//now replace the argument fragment
                //setFragment(mapFragmentTEST);

            }
        });



    // Set the fields to specify which types of place data to
// return after the user has made a selection.
      //  fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG);

// Start the autocomplete intent.
    /*    Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,fields)
                .setCountry("MM")
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
*/

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE_FOR_SOURCE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("TAG3", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG4", "Place: " + place.getName() + ", " + place.getLatLng());
                sourceEdit.setText(place.getName());
                placeList.addAll(Collections.singleton(place.getName()));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,placeList);
                listView.setAdapter(adapter);
                destinationEdit.requestFocus();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("RESULT_ERROR", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if(requestCode == AUTOCOMPLETE_REQUEST_CODE_FOR_DESTINATION){
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.e("TAG5", "Place: " + place.getName() + ", " + place.getId());
                Log.e("TAG6", "Place: " + place.getName() + ", " + place.getLatLng());
                destinationEdit.setText(place.getName());
                //destinationEdit.requestFocus();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("RESULT_ERROR", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.map, fragment).commit();
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
                        .build(this);
        startActivityForResult(autocompleteIntent, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {

    }
}