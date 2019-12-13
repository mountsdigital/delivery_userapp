package com.mounts.lenovo.delivery3.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Api;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.response.GetTown;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    private ApiInterface api;
    private RetrofitService service;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getTownList();


        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    private void getTownList() {
        RetrofitService.getInstance().getApi().getOfficeTown().enqueue(new Callback<GetTown>() {
            @Override
            public void onResponse(Call<GetTown> call, Response<GetTown> response) {
                if(response.isSuccessful()){
                    Log.e("size",String.valueOf(response.body().towns.size()));
                }
                else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<GetTown> call, Throwable t) {

            }
        });
    }

}
