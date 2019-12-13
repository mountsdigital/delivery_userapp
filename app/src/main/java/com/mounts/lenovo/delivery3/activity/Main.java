package com.mounts.lenovo.delivery3.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.fragment.CategoriesFragment;
//import com.mounts.lenovo.delivery3.fragment.MapFragment;
import com.mounts.lenovo.delivery3.fragment.FirstHomeFragment;
import com.mounts.lenovo.delivery3.fragment.MapFragmentTEST;
import com.mounts.lenovo.delivery3.fragment.ReceiverOrderFragment;
import com.mounts.lenovo.delivery3.fragment.SendOrderFragmentOne;
import com.mounts.lenovo.delivery3.helpers.UiHelper;
import com.mounts.lenovo.delivery3.interfaces.IPositiveNegativeListener;

import static android.view.View.VISIBLE;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    public  static DrawerLayout drawerLayout;
    public static Toolbar toolbar;
    protected LocationManager locationManager;
    boolean gps_enabled = false;
    boolean doubleBackToExitPressedOnce = false;
    boolean network_enabled = false;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6161;
    private UiHelper uiHelper = new UiHelper();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("hey", "Main now!");
        setContentView(R.layout.activity_main2);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        toolbar.setTitle("Delivery");
        setStatusBarTransparent();
         drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
       // locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        try {
//            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } catch(Exception ex) {Log.e("GPs Error",ex.getMessage()); }
//
//        try {
//            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch(Exception ex) {Log.e("Network Error",ex.getMessage());}
//
//        if(!gps_enabled && !network_enabled){
//            buildAlertMessageNoGps();
//        }else {
            //getLocation();
            setFragment(new FirstHomeFragment());
        //}


    }



    public  void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  else  {
            super.onBackPressed();
            return;
        }
       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   // @SuppressLint("RestrictedApi")
    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_receive) {
            toolbar.setVisibility(VISIBLE);
            toolbar.setTitle("Delivery");
            Toast.makeText(this,"nav_receive",Toast.LENGTH_SHORT).show();
            Log.e("Error","nav_receive");
            setFragment(new ReceiverOrderFragment());
        }
        else if (id == R.id.nav_send) {
            toolbar.setVisibility(VISIBLE);
            toolbar.setTitle("Delivery");
            Log.e("Error","nav_send");
            Toast.makeText(this,"nav_receive",Toast.LENGTH_SHORT).show();

            setFragment(new ReceiverOrderFragment());
        }
        else if (id == R.id.nav_map) {
            toolbar.setVisibility(View.GONE);
            toolbar.setTitle("Delivery");
            Log.e("Error","nav_map");
            Toast.makeText(this,"nav_receive",Toast.LENGTH_SHORT).show();

            setFragment(new FirstHomeFragment());
        }
        else if (id == R.id.nav_categories) {
            toolbar.setVisibility(VISIBLE);
            toolbar.setTitle("Delivery");
            Log.e("Error","nav_categories");
            Toast.makeText(this,"nav_receive",Toast.LENGTH_SHORT).show();

            setFragment(new CategoriesFragment());
        }
        else if (id == R.id.send_order) {
            toolbar.setVisibility(VISIBLE);
            toolbar.setTitle("Delivery");
            Log.e("Error","order");
            Toast.makeText(this,"order",Toast.LENGTH_SHORT).show();

            setFragment(new SendOrderFragmentOne());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected  void setFragment(final Fragment fragment) {
     /*   FragmentTransaction fragmentTransaction = new FragmentTransaction() {
            @Override
            public int commit() {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment);
                return 0;
            }

            @Override
            public int commitAllowingStateLoss() {
                return 0;
            }

            @Override
            public void commitNow() {

            }

            @Override
            public void commitNowAllowingStateLoss() {

            }
        };*/
       // fragmentTransaction.replace(R.id.fragment_container, mFeedFragment);
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commitNowAllowingStateLoss();


        //FragmentTransaction.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.replace(R.id.frame, fragment);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
    }


    private void setStatusBarTransparent(){

        if(Build.VERSION.SDK_INT >=21){
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            Window window =getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
            ActionBar actionBar = getActionBar();
          //  actionBar.hide();

        }
    }
}
