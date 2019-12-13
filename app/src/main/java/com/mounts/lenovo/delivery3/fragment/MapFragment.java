package com.mounts.lenovo.delivery3.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.Main;
import com.mounts.lenovo.delivery3.activity.NextOrder;
import com.mounts.lenovo.delivery3.interfaces.FirebaseDriverListener;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.VISIBLE;
import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener, LocationListener  {

    private static GoogleMap mMap;
    private Button btnNextOrder;
    private FloatingSearchView floatingSearchView;
    private Toolbar toolbar;
    private FragmentActivity mContext;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
   // ManagePermissions permissions;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    private static final int REQUEST_LOCATION = 1;

    public static LatLng currentLoction ;
    public   Double lattitude ;
    public   Double longitude;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("MapFragment", "Arrive");
        final View view = inflater.inflate(R.layout.acitivity_map_fragment, container, false);
     //   btnNextOrder = view.findViewById(R.id.btnNextOrder);
        floatingSearchView = view.findViewById(R.id.floating_search_view);

        NavigationView navigationView = view.findViewById(R.id.nav_view);

        //Recall drawerLayout in Main Activity
        floatingSearchView.attachNavigationDrawerToMenuButton(Main.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);

        btnNextOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click", "btn");
                Intent intent = new Intent(getActivity(), NextOrder.class);
                startActivity(intent);
            }
        });
    /*    FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
       // transaction.add(R.id.map, fragment);
        transaction.commit();
         fragment.getMapAsync(this);
*/
        //  SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
        /// SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
        ///         .findFragmentById(R.id.map);
        ///   mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {Log.e("GPs Error",ex.getMessage()); }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {Log.e("Network Error",ex.getMessage());}

        if(!gps_enabled && !network_enabled){
            buildAlertMessageNoGps();
        }else {
            getLocation();
        }

        //if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        //      buildAlertMessageNoGps();

        //  }
        //if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        // currentLoction = new LatLng(lattitude,longitude);

        //   }

        SupportMapFragment mapFragment = (SupportMapFragment)
                this.getChildFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //     locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        //   requestLocationPermission();



        return view;

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = latti;//String.valueOf(latti);
                longitude = longi;//String.valueOf(longi);
                //  currentLoction = new LatLng(lattitude,longitude);
                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                //        + "\n" + "Longitude = " + longitude);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = latti;//String.valueOf(latti);
                longitude = longi;//String.valueOf(longi);
                //  currentLoction = new LatLng(lattitude,longitude);
                //  textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                //       + "\n" + "Longitude = " + longitude);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = latti;//String.valueOf(latti);
                longitude = longi;//String.valueOf(longi);
                //currentLoction = new LatLng(lattitude,longitude);
                //  textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
                //  + "\n" + "Longitude = " + longitude);

            }else{

                Toast.makeText(getContext(),"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }


        }
    }

    public  void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onStart() {
        super.onStart();
    /*    locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            currentLoction = new LatLng(lattitude,longitude);
            SupportMapFragment mapFragment = (SupportMapFragment)
                    this.getChildFragmentManager()
                            .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /*@AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    // public void requestLocationPermission() {
    ///     String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,LocationManager.GPS_PROVIDER};
    //     if(EasyPermissions.hasPermissions(getContext(), perms)) {
    //         Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
     //  locationManager.requestLocationUpdates((LocationManager.GPS_PROVIDER,0,0,this);

         }
         else {
             EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
         }
     }*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap = googleMap;
        //floatingSearchView.setVisibility(View.GONE);
        // Add a marker in Sydney and move the camera
        //   LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        currentLoction = new LatLng(lattitude,longitude);
        MarkerOptions options = new MarkerOptions();
        options.position(currentLoction);
        options.title("Your Location");
        options.snippet("This is a Snippert");
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
        options.draggable(true);
        options.zIndex(0.5f);

        LatLng newlocation = new LatLng(21.990117,96.09122371532);
        MarkerOptions options2 = new MarkerOptions();
        options2.position(newlocation);
        options2.title("New Marker");
        options2.snippet("This is a Snippert2");
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
        options2.draggable(true);
        options2.zIndex(1f);


        mMap.addMarker(options);
        mMap.addMarker(options2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoction,18F));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);

    /*    googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));*/

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

    /*  @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           int id = item.getItemId();

           if (id == R.id.nav_receive) {
               toolbar.setVisibility(VISIBLE);
              setFragment(new ReceiverOrderFragment());
          } else if (id == R.id.nav_send) {
              toolbar.setVisibility(VISIBLE);
              setFragment(new ReceiverOrderFragment());
          } else if (id == R.id.nav_map) {
               toolbar.setVisibility(View.GONE);
              setFragment(new MapFragment());
          } else if (id == R.id.nav_categories) {
               toolbar.setVisibility(VISIBLE);
              setFragment(new CategoriesFragment());
              setFragment(new CategoriesFragment());
           }

          DrawerLayout drawer = findViewById(R.id.drawer_layout);
          drawer.closeDrawer(GravityCompat.START);
          return true;
      }

   //
   //    @Override
   //    public void onAttach(Activity activity) {
   //        mContext=(FragmentActivity) activity;
   //        super.onAttach(activity);
   //    }
   ///**/
    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.map, fragment).commit();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("MapFragment",marker.getTitle());
        return true;
    }



    @Override
    public void onMarkerDrag(Marker marker) {

        Log.d("MapFragmet",marker.getPosition().toString());
    }
    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d("MapFragmet",marker.getPosition().toString());
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLoction = new LatLng(location.getLatitude(),location.getLongitude());
        Log.d("Latitude:" ,Double.toString(location.getLatitude()));
        Log.d("Longitude:" ,Double.toString(location.getLongitude()));

       /* currentLoction = new LatLng(21.9892044,96.0743976228);
        currentLoction = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(currentLoction);
        options.title("Your Location");
        options.snippet("This is a Snippert");
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
        options.draggable(true);
        options.zIndex(0.5f);
       // mMap.addMarker(options);

        LatLng newlocation = new LatLng(21.9892044,96.0743976228);
        MarkerOptions options2 = new MarkerOptions();
        options2.position(newlocation);
        options2.title("New Marker");
        options2.snippet("This is a Snippert2");
        options2.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_map));
        options2.draggable(true);
        options2.zIndex(1f);


         mMap.addMarker(options);
        mMap.addMarker(options2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoction,18F));

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);*/

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

////
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menu) {
//        // Handle navigation view item clicks here.
//        int id = menu.getItemId();
//
//        if (id == R.id.nav_receive) {
//            floatingSearchView.setVisibility(View.GONE);
//            setFragment(new ReceiverOrderFragment());
//
//        } else if (id == R.id.nav_send) {
//            floatingSearchView.setVisibility(View.GONE);
//            setFragment(new ReceiverOrderFragment());
//
//        } else if (id == R.id.nav_map) {
//            floatingSearchView.setVisibility(VISIBLE);
//            setFragment(new ReceiverOrderFragment());
////            setFragment(new MapFragment());
//        }
//        return true;
//    }
//
//    private void setFragment(ReceiverOrderFragment receiverOrderFragment) {
//        getFragmentManager().beginTransaction().replace(R.id.frame_map, receiverOrderFragment).commit();
//    }
}
