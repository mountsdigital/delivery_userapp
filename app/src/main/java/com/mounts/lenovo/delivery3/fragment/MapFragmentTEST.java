package com.mounts.lenovo.delivery3.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.*;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.AutocompleteTestActivity;
import com.mounts.lenovo.delivery3.activity.Main;
import com.mounts.lenovo.delivery3.activity.NextOrder;
import com.mounts.lenovo.delivery3.helpers.ConnectionDetector;
import com.mounts.lenovo.delivery3.helpers.FirebaseEventListenerHelper;
import com.mounts.lenovo.delivery3.helpers.GoogleMapHelper;
import com.mounts.lenovo.delivery3.helpers.MarkerAnimationHelper;
import com.mounts.lenovo.delivery3.helpers.SharePreferenceManager;
import com.mounts.lenovo.delivery3.helpers.UiHelper;
import com.mounts.lenovo.delivery3.interfaces.FirebaseDriverListener;
import com.mounts.lenovo.delivery3.interfaces.IPositiveNegativeListener;
import com.mounts.lenovo.delivery3.model.Driver;
import com.mounts.lenovo.delivery3.model.MyoTwin_Order;
import com.mounts.lenovo.delivery3.response.AddOrderInfo;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;
import com.mounts.lenovo.delivery3.service.Token;
import com.mounts.lenovo.delivery3interfaces.LatLngInterpolator;
import com.spartons.passengerapp.collection.MarkerCollection;



import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.telephony.CellLocation.requestLocationUpdate;


public class MapFragmentTEST  extends Fragment implements
        NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener, OnMapReadyCallback, FirebaseDriverListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, View.OnClickListener {
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    public MapFragmentTEST() {
        // Required empty public constructor
    }



    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6161;
    private static final String ONLINE_DRIVERS = "online_drivers";
    private Button btnNextOrder;
    private FloatingSearchView floatingSearchView;
    private FragmentActivity mContext;
    private static LocationCallback locationCallback;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private UiHelper uiHelper = new UiHelper();
    private FirebaseEventListenerHelper valueEventListener;
    private GoogleMapHelper googleMapHelper  = new GoogleMapHelper();
    private DatabaseReference databaseReference;
    public  static GoogleMap googleMap;
    private boolean locationFlag = true;
    private Button conformButton;
    private final int REQUEST_CODE_FOR_DESTINATION = 456;
    private final int REQUEST_CODE_FOR_SOURCE = 123;
    private Driver driver;
    private EditText sourceEditText, destinationEditText;
    private FloatingActionButton currentLocBTN;
    private ProgressBar progressBar;
    private Marker currentPositionMarker = null;
    private MarkerAnimationHelper markerAnimationHelper = new MarkerAnimationHelper();
    public static LatLng currentLocation;
    private static Geocoder geo ;
    private Thread thread;
    private String source =null;
    private String destination = null;
    private String value = null;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private LatLng soucLatLan = null;
    private LatLng desLatLan = null;
    private  SupportMapFragment mapFragment;
    private SharePreferenceManager sharePreferenceManager;
    //  new FirebaseDatabase.getInstance().reference.child(ONLINE_DRIVERS)

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.acitivity_map_fragment, container, false);
        sourceEditText = view.findViewById(R.id.sourceEditAddress);
        destinationEditText = view.findViewById(R.id.destinationEditAddress);
        floatingSearchView = view.findViewById(R.id.floating_search_view);
        currentLocBTN = view.findViewById(R.id.current_Loc_Btn);
        conformButton = view.findViewById(R.id.conformButon);
        progressBar = view.findViewById(R.id.progress_circular);
       // conformButton.setVisibility(View.GONE);
        Main.toolbar.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        sharePreferenceManager = SharePreferenceManager.getInstance(getContext());
        MapsInitializer.initialize(getContext());
        createLocationCallback();
        //call user's current location..
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable(getContext())) {
            Toast.makeText(getContext(), "Play Services did not installed!", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else requestLocationUpdate();

        //Getting args from Select_Source_****Fragment
        source = getArguments().getString("SOURCE");
        soucLatLan = new LatLng(getArguments().getDouble("souc_Latitude"),
                getArguments().getDouble("souc_Longitude"));

        destination = getArguments().getString("DESTINATION");
        desLatLan = new LatLng(getArguments().getDouble("des_Latitude"),
                getArguments().getDouble("des_Longitude"));
         mapFragment = (SupportMapFragment)
                this.getChildFragmentManager()
                        .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);

        if(!ConnectionDetector.isMobileNetworkAvailable(getContext()) &&
                !ConnectionDetector.isGpsStatusEnabled(getContext()) &&
                  !ConnectionDetector.networkStatus(getContext()) &&
                  !ConnectionDetector.isWifiAvailable(getContext())){

            ConnectionDetector.displayNoNetworkDialoag(getActivity());
        }else{
          //  geo = new Geocoder(getContext(),Locale.getDefault());
         //   runNewThread();
        }
        //geo = new Geocoder(getContext(),Locale.getDefault());



        if(SharePreferenceManager.getInstance(getContext()).getOrder() != null)
        Log.e( "SaveOrder",SharePreferenceManager.getInstance(getContext()).getOrder().toString());


        value = getArguments().getString("YourKey");
        if(value != null){
            Log.e("Send Value", value);
            Log.e("DATA :", "Arrived From Order fragment");
        }

        if(source != null ) {
            Log.e("source", source);
           // Log.e("Source LatLan", soucLatLan.toString());
            sourceEditText.setText(source);
            progressBar.setVisibility(View.GONE);
        }else if(source == null){
            //   progressBar.setVisibility(View.GONE);
            sourceEditText.setText("");
            sourceEditText.setHint("Searching...");
            if(!ConnectionDetector.isMobileNetworkAvailable(getContext()) &&
                    !ConnectionDetector.isGpsStatusEnabled(getContext()) &&
                    !ConnectionDetector.networkStatus(getContext()) &&
                    !ConnectionDetector.isWifiAvailable(getContext())){

                ConnectionDetector.displayNoNetworkDialoag(getActivity());
            }else{
                 geo = new Geocoder(getContext(),Locale.getDefault());
                   runNewThread().start();
            }

        }
        if (destination != null) {
            Log.e("destination",destination);
            destinationEditText.setText(destination);
            progressBar.setVisibility(View.GONE);

        }
        if(sourceEditText.getText().toString().isEmpty()){
            conformButton.setVisibility(View.GONE);
        }else  if (destinationEditText.getText().toString().isEmpty()){
            conformButton.setVisibility(View.GONE);
        }else{
            conformButton.setVisibility(View.VISIBLE);
        }
        if(soucLatLan != null){
            Log.e(source+" LatLan", soucLatLan.toString());
        }else {
            Log.e(source+" LatLan",currentLocation.toString());
        }
        if(desLatLan != null){
            Log.e(destination+" LatLan", desLatLan.toString());
        }

        NavigationView navigationView = view.findViewById(R.id.nav_view);
        //Recall drawerLayout in Main Activity
        floatingSearchView.attachNavigationDrawerToMenuButton(Main.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        //Firebase ref call...
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(ONLINE_DRIVERS);
        //Recall drawerLayout in Main Activity
        floatingSearchView.attachNavigationDrawerToMenuButton(Main.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        floatingSearchView.setVisibility(View.GONE);

        //add Listener for edittext widget...
       // sourceEditText.setFocusableInTouchMode(true);
       // sourceEditText.requestFocus();//deafault cursr to source EditText(optoinal)

        //for Driver location...
        valueEventListener = new FirebaseEventListenerHelper(this);
        myRef.addChildEventListener(valueEventListener);
        //add listener to edittext
        sourceEditText.setOnTouchListener(this);
        destinationEditText.setOnTouchListener(this);
        conformButton.setOnClickListener(this);

        return view;

    }

    public Thread runNewThread(){
       // createLocationCallback();

       return thread = new Thread() {
        @Override
        public void run() {
            try { Thread.sleep(1500); }
            catch (InterruptedException e) {}
            mContext=getActivity();
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(geo != null && currentLocation != null) {
                        googleMapHelper.setLocationtoEditText(geo, sourceEditText, currentLocation);
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        //Log.e("currentlocaiton",currentLocation.toString());
                    }
                }
            });
        }
    };
}

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLastLocation() == null) return;
                //LatLn
                currentLocation = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());

                if (locationFlag) {
                    locationFlag = false;
                  if(currentLocation != null){
                        animateCamera(currentLocation);
                        Log.e("Location ***", currentLocation.latitude + " , " + currentLocation.longitude);
                        showMarker(currentLocation);
                  }else {
                      Log.e("currentLocation","Null");

                  }
                }
                //firebaseHelper.updateDriver(Driver(lat = latLng.latitude, lng = latLng.longitude))

            }
        };

    }

    private void showMarker(LatLng latLng){
    try{
        if (currentPositionMarker == null)
            currentPositionMarker = googleMap.addMarker(googleMapHelper.getUserMakerOptions(latLng));
        else {
            Log.e("LagLan", latLng.toString());
        }
    }catch (Exception e){
        Log.e("LagLan", e.getMessage());
    }
//    if (currentPositionMarker == null)
//        currentPositionMarker = googleMap.
//                addMarker(googleMapHelper.getUserMakerOptions(latLng));
}

    private  void showOrAnimateMarker(LatLng latLng) {
        if (currentPositionMarker == null)
            currentPositionMarker = googleMap.addMarker(googleMapHelper.getUserMakerOptions(latLng));
        else {
            //Log.e("LagLan", latLng.toString());
            markerAnimationHelper.animateMarkerToGB(currentPositionMarker, latLng,
                    new LatLngInterpolator.Spherical());

        }

    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdate() {
        if (!uiHelper.isHaveLocationPermission(getContext())) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        if (uiHelper.isLocationProviderEnabled(getContext())) {
            uiHelper.showPositiveDialogWithListener(getContext(),
                    getResources().getString(R.string.need_location),
                    getResources().getString(R.string.location_content),
                    new IPositiveNegativeListener() {
                        @Override
                        public void onNegative() {

                        }

                        @Override
                        public void onPositive() {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }, "Turn On", false);
        }
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void animateCamera(LatLng latLng) {
        CameraUpdate cameraUpdate;
        try {
            cameraUpdate = googleMapHelper.buildCameraUpdate(latLng);
            if (cameraUpdate != null) {
                googleMap.animateCamera(cameraUpdate, 10, null);
                Log.e("cameraUpdate", "cameraUpdate not null");

            } else if (cameraUpdate == null) {
                Log.e("AnimateCamera", "cameraupdat null null");
                googleMap.animateCamera(cameraUpdate, 11, null);
            }
        } catch (Exception e) {
            Log.e("AnimateCamera", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            int value = grantResults[0];
            if (value == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else if (value == PackageManager.PERMISSION_GRANTED) requestLocationUpdate();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_receive) {
            //toolbar.setVisibility(VISIBLE);
            setFragment(new ReceiverOrderFragment());
            floatingSearchView.setVisibility(View.INVISIBLE);
            btnNextOrder.setVisibility(View.GONE);
        } else if (id == R.id.nav_send) {
            //toolbar.setVisibility(VISIBLE);
            setFragment(new ReceiverOrderFragment());
            floatingSearchView.setVisibility(View.INVISIBLE);
            btnNextOrder.setVisibility(View.GONE);
        } else if (id == R.id.nav_map) {
            //  toolbar.setVisibility(View.GONE);
            setFragment(new MapFragmentTEST());
        } else if (id == R.id.nav_categories) {
            //toolbar.setVisibility(VISIBLE);
            //setFragment(new CategoriesFragment());
            setFragment(new CategoriesFragment());
            floatingSearchView.setVisibility(View.INVISIBLE);
            btnNextOrder.setVisibility(View.GONE);
        }

        DrawerLayout drawer = getView().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment).commit();
    }
    private void setFragment1(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
//        FragmentManager fragmentManager =  getActivity().getSupportFragmentManager();
//        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.frame, fragment).addToBackStack(BACK_STACK_ROOT_TAG).commit();
    }

    @Override
    public void onDriverAdded(@NotNull Driver driver) {
        this.driver = driver;
        LatLng latLng = new LatLng(driver.getLat(), driver.getLng());
        MarkerOptions markerOptions = googleMapHelper.getDriverMarkerOptions(new LatLng(driver.getLat(), driver.getLng()));
        markerOptions.position(new LatLng(driver.getLat(), driver.getLng()));
        Marker marker = googleMap.addMarker(markerOptions);
        marker.setTag(driver.getDriverId());
        MarkerCollection.insertMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
        //  totalOnlineDrivers.text = resources.getString(R.string.total_online_drivers).plus(" ").plus(MarkerCollection.allMarkers().size)
        Log.e(getResources().getString(R.string.total_online_drivers),
                String.format("%d", MarkerCollection.allMarkers().size()));
    }

    @Override
    public void onDriverRemoved(@NotNull Driver driver) {
        MarkerCollection.removeMarker(driver.getDriverId());
        Log.e(getResources().getString(R.string.total_online_drivers),
                String.format("%d", MarkerCollection.allMarkers().size()));
        // totalOnlineDrivers.text = resources.getString(R.string.total_online_drivers).plus(" ").plus(MarkerCollection.allMarkers().size)
    }

    @Override
    public void onDriverUpdated(@NotNull Driver driver) {
        Marker marker = MarkerCollection.getMarker(driver.getDriverId());
        MarkerAnimationHelper.animateMarkerToGB(marker, new LatLng(driver.getLat(), driver.getLng()), new LatLngInterpolator.Spherical());
    }

    public void start_To_NextOrder(int requestcode){
        Intent intent = new Intent(getContext(),NextOrder.class);
        startActivityForResult(intent,requestcode);
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        MapsInitializer.initialize(getContext());
     ///   googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnMarkerDragListener(this);
        googleMap.setOnMarkerClickListener(this);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.style_json_dark));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        // Position the map's camera near Sydney, Australia.
        if(currentLocation != null)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.latitude, currentLocation.longitude)));



        currentLocBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.setMyLocationEnabled(true);
                showOrAnimateMarker(currentLocation);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {

            if (v.getId() == R.id.sourceEditAddress ||
                    v.getId() == R.id.destinationEditAddress) {
                Bundle bundle = new Bundle();
                bundle.putString("SOURCE_FROM_MAP",sourceEditText.getText().toString());
                if (destinationEditText.getText().toString() != null) {
                    bundle.putString("DES_FROM_MAP",destinationEditText.getText().toString());
                }
                Select_Source_DestinationFragment fragment = new Select_Source_DestinationFragment();
                fragment.setArguments(bundle);
                setFragment1(fragment);
            }

                // Do what you want


            return true;
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
       // showOrAnimateMarker(marker.getPosition());
        Log.e("MarkerDragStart",marker.getPosition().toString());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.e("MarkerDragEnd",marker.getPosition().toString());


    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.e("onMarkerDrag",marker.getPosition().toString());
        // showOrAnimateMarker(marker.getPosition());
        currentLocation = marker.getPosition();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        sourceEditText.setText("");
        sourceEditText.setHint("Searching...");
        progressBar.setVisibility(View.VISIBLE);
        geo = new Geocoder(getContext(),Locale.getDefault());
        currentLocation = marker.getPosition();
        if(currentLocation != null)
        Log.e("Pin location",currentLocation.toString());

        runNewThread().start();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        createLocationCallback();
        //attching googleMap frgment...
//        SupportMapFragment mapFragment = (SupportMapFragment)
//                this.getChildFragmentManager()
//                        .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

    }

    @Override
    public void onPause() {
        super.onPause();  createLocationCallback();
        mapFragment = (SupportMapFragment)
                this.getChildFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //attching googleMap frgment...


    }

    @Override
    public void onResume() {
        super.onResume();
        createLocationCallback();
        mapFragment = (SupportMapFragment)
                this.getChildFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        createLocationCallback();

    }
//ConformButton onClick
    @Override
    public void onClick(View v) {
        if(sharePreferenceManager.getOrder() != null){
            MyoTwin_Order order = sharePreferenceManager.getOrder();
        Log.e("Order Conform",SharePreferenceManager.getInstance(getContext()).getOrder().toString()+
                currentLocation.toString()+desLatLan.toString());
        Toast.makeText(getContext(),"Order Conform"+SharePreferenceManager.getInstance(getContext()).getOrder().toString()+
                currentLocation.toString()+desLatLan.toString(),Toast.LENGTH_SHORT).show();

            RetrofitService.getInstance().getApi().
                    fullOrderInformation(
                            order.getReceiver_City(),
                            order.getOrder_Name(),
                            order.getWeight(),
                            order.getReceiver_Name(),
                            order.getReceiver_Phone(),
                            order.getReceiver_Address(),
                            order.getReceiver_City(),
                            order.getPre_Paid(),
                            order.getPost_Paid(),
                           // Token.token,
                            order.getSame_city(),
                            currentLocation.latitude,
                            currentLocation.longitude,
                            desLatLan.latitude,
                            desLatLan.longitude)
                    .enqueue(new Callback<AddOrderInfo>() {
                        @Override
                        public void onResponse(Call<AddOrderInfo> call, Response<AddOrderInfo> response) {
                            try{
                                    if(response.isSuccessful()) {
                                        Log.e("make_order", Boolean.toString(response.isSuccessful())+"\n");

                                       // Log.e("\nOrder state", response.body().duration);
                                        setFragment(new FirstHomeFragment());
                                        Toast.makeText(getContext(),"Order Created",Toast.LENGTH_SHORT).show();
                                         Log.e("Order state", response.body().duration);

                                    }else Log.e("fail_order", response.errorBody().toString());

                            }catch (Exception e){
                                Log.e("catch exception", e.getMessage());
                            }

//                    TODO: get token from header


                        }

                        @Override
                        public void onFailure(Call<AddOrderInfo> call, Throwable t) {

                            Log.e("response", "fail"+t.getMessage());

                        }
                    });


        }
     //  sharePreferenceManager.clear();
    }
}
