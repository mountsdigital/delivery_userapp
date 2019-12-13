package com.mounts.lenovo.delivery3.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.response.AddOrderInfo;
import com.mounts.lenovo.delivery3.response.GetTown;
import com.mounts.lenovo.delivery3.response.ReceiverCity;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements View.OnClickListener {

    private RetrofitService service;
    private ApiInterface api;
    private EditText edtorderName, edtestimatedWeight, edtName, edtPhone, edtAddress;
    private Spinner citySpinner;
    private CheckBox prePaid, postPaid;
    private String orderName, estimatedWeight, receiverName, receiverPhone, receiverAddress, receiverCity;
    private Button btnNext;
    private boolean isPrePaid, isPostPaid;
    private String token;
    private List<ReceiverCity> cities;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Log.e("Reach init()", "yes");



        edtorderName = view.findViewById(R.id.orderName);
        edtestimatedWeight = view.findViewById(R.id.edtEstimtedWeight);
        edtName = view.findViewById(R.id.receiver_name);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);

        citySpinner = view.findViewById(R.id.pre_Post_spinner);
        btnNext = view.findViewById(R.id.btnNext);

        //prePaid = view.findViewById(R.id.prepaidCheckbox);
      //  postPaid = view.findViewById(R.id.postpaidCheckbox);

        cities = new ArrayList<>();

        getCity();

        prePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postPaid.setChecked(false);
                isPrePaid = prePaid.isChecked();
                isPostPaid = postPaid.isChecked();

                Log.e("prePaid", String.valueOf(prePaid.isChecked()));
            }
        });

        postPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prePaid.setChecked(false);
                isPrePaid = prePaid.isChecked();
                isPostPaid = postPaid.isChecked();

                Log.e("postpaid", String.valueOf(postPaid.isChecked()));
            }
        });


        btnNext.setOnClickListener(this);

    }

    private void getCity() {

        RetrofitService.getInstance().getApi().getOfficeTown().enqueue(new Callback<GetTown>() {
            @Override
            public void onResponse(Call<GetTown> call, Response<GetTown> response) {
                if (response.isSuccessful()) {
                    Log.e("citySpinner", String.valueOf(response.body().towns.size()));
                    cities.addAll(response.body().towns);

                    List<String> towns = new ArrayList<>();

                    for (ReceiverCity town : cities) {

                        towns.add(town.name);
                    }

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, towns);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    citySpinner.setAdapter(spinnerAdapter);
                } else {
                    Log.e("getCity", "fail");
                }
            }

            @Override
            public void onFailure(Call<GetTown> call, Throwable t) {

                Log.e("Onfailure", t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.e("onclick", "ok");

        if (edtorderName.getText().toString().isEmpty())
            edtorderName.setError("Enter Order Name");
        else orderName = edtorderName.getText().toString();

        if (edtestimatedWeight.getText().toString().isEmpty())
            edtestimatedWeight.setError("Enter Package Weight");
        else estimatedWeight = edtestimatedWeight.getText().toString();

        if (edtName.getText().toString().isEmpty())
            edtName.setError("Enter Receiver Name");
        else receiverName = edtName.getText().toString();

        if (edtPhone.getText().toString().isEmpty())
            edtPhone.setError("Enter Receiver Phone");
        else receiverPhone = edtPhone.getText().toString();

        if (edtAddress.getText().toString().isEmpty())
            edtAddress.setError("Enter Receiver Address");
        else receiverAddress = edtAddress.getText().toString();
        receiverCity = citySpinner.getSelectedItem().toString();

        Log.e("selectedItem", receiverCity);

//        api.fullOrderInformation("Yangon", orderName,
//        estimatedWeight, receiverName, receiverPhone,
//        receiverAddress, receiverCity, isPrePaid, isPostPaid,
//        token,0).enqueue(new Callback<AddOrderInfo>() {
//            @Override
//            public void onResponse(Call<AddOrderInfo> call, Response<AddOrderInfo> response) {
//                if (response.isSuccessful()) {
//                    Log.e("make_order", "OK");
////                    TODO: get token from header
//
//                } else {
//                    Log.e("make_order", "fail");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddOrderInfo> call, Throwable t) {
//
//                Log.e("response", "fail");
//
//            }
//        });
    }
}