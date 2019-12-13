package com.mounts.lenovo.delivery3.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.Details;
import com.mounts.lenovo.delivery3.activity.Lists;
import com.mounts.lenovo.delivery3.adapter.ImageAdapter;
import com.mounts.lenovo.delivery3.adapter.RecyclerViewDataAdapter;
import com.mounts.lenovo.delivery3.adapter.SectionDataModel;
import com.mounts.lenovo.delivery3.adapter.SectionListDataAdapter;
import com.mounts.lenovo.delivery3.adapter.ServiceAdapter;
import com.mounts.lenovo.delivery3.adapter.SingleItemModel;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.holder.ServiceHolder;
import com.mounts.lenovo.delivery3.response.CategoryData;
import com.mounts.lenovo.delivery3.response.GetServiceList;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    private ImageView back_arrow;
    private RecyclerViewDataAdapter adapter;
    private SearchView searchView;
    private RecyclerView recyclerView, searchRecycler;
    private SectionListDataAdapter sectionAdapter;
    RetrofitService retrofitService;
    List<CategoryData> categoryData = new ArrayList<>();
    List<CategoryData> newCategoryData = new ArrayList<>();
    ImageAdapter imageAdapter;
    ViewPager viewPager;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("Categories", "Fragment");
        View view = inflater.inflate(R.layout.categories, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        back_arrow = view.findViewById(R.id.backarrow_categories);
        viewPager = view.findViewById(R.id.viewPager);
        searchView = view.findViewById(R.id.searchViewCategories);
        searchRecycler = view.findViewById(R.id.my_recycler_view);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        adapter = new RecyclerViewDataAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sectionAdapter = new SectionListDataAdapter(this);
        viewPager = view.findViewById(R.id.viewPager);
        imageAdapter = new ImageAdapter(getActivity());
        viewPager.setAdapter(imageAdapter);
        RetrofitService.getInstance().getApi().getServiceList().enqueue(new Callback<GetServiceList>() {
            @Override
            public void onResponse(Call<GetServiceList> call, Response<GetServiceList> response) {
                if (response.isSuccessful()) {
                    Log.e("Success", "Yes "+response.body().categoryData);
                  // response.body().categoryData
                }
            }

            @Override
            public void onFailure(Call<GetServiceList> call, Throwable t) {

            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(int id) {
        Log.e("category_id", String.valueOf(id));
        Intent intent = new Intent(getActivity(), Details.class);//TODO:"AnyName.class" that you would pass
        startActivity(intent);
    }
}
// For Horizontal Recycler view
// recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));