package com.mounts.lenovo.delivery3.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.adapter.DetailsAdapter;
import com.mounts.lenovo.delivery3.adapter.SectionListDataAdapter;
import com.mounts.lenovo.delivery3.adapter.SeeAllAdapter;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.holder.DetailsHolder;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder;
import com.mounts.lenovo.delivery3.response.GetServiceDetails;
import com.mounts.lenovo.delivery3.response.GetServiceList;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Details extends AppCompatActivity implements OnItemClickListener {

    private SearchView searchView;
    private TextView categoryName;
    private RecyclerView recyclerView;
    DetailsAdapter detailsAdapter;
    RetrofitService retrofitService;
    private String token;
    private String sessionId;
    private ImageView back_arrow;
    private SectionListDataAdapter adapter;
    private int categoryId;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Arrive", "Details.java");
        setContentView(R.layout.details);

        searchView = findViewById(R.id.searchViewDetails);
        back_arrow = findViewById(R.id.backarrow_details);
        categoryName = findViewById(R.id.categoryName_details);

        adapter = new SectionListDataAdapter(this);
        recyclerView = findViewById(R.id.my_recycler_view_details);
//        detailsAdapter = new DetailsAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(detailsAdapter);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RetrofitService.getInstance().getApi().getServiceDetail().enqueue(new Callback<GetServiceDetails>() {
            @Override
            public void onResponse(Call<GetServiceDetails> call, Response<GetServiceDetails> response) {

            }

            @Override
            public void onFailure(Call<GetServiceDetails> call, Throwable t) {

            }
        });
//        detail(sessionid);
    }



    @Override
    public void onItemClick(int id) {

    }
}
