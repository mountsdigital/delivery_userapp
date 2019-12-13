package com.mounts.lenovo.delivery3.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.SeeAllAdapter;
import com.mounts.lenovo.delivery3.fragment.CategoriesFragment;
//import com.mounts.lenovo.delivery3.fragment.MapFragment;
import com.mounts.lenovo.delivery3.fragment.MapFragmentTEST;
import com.mounts.lenovo.delivery3.fragment.ReceiverOrderFragment;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.FrameLayout;

import static android.view.View.VISIBLE;

public class Lists extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, SeeAllHolder.OnItemClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    SeeAllAdapter adapter;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Arrive", "Lists.java");
        setContentView(R.layout.activity_list);
        toolbar = findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);
//        TODO:recyclerview pr tae seeall sar lay twe co htae yan...
        recyclerView = findViewById(R.id.seeall_recyclerview);
        adapter = new SeeAllAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_receive) {
            toolbar.setVisibility(VISIBLE);
            setFragment(new ReceiverOrderFragment());
        } else if (id == R.id.nav_send) {
            toolbar.setVisibility(VISIBLE);
            setFragment(new ReceiverOrderFragment());
        } else if (id == R.id.nav_map) {
            toolbar.setVisibility(View.GONE);
            setFragment(new MapFragmentTEST());
        } else if (id == R.id.nav_categories) {
            toolbar.setVisibility(VISIBLE);
            setFragment(new CategoriesFragment());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onClick(View v) {

    }
}
