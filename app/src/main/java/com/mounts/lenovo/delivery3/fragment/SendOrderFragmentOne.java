package com.mounts.lenovo.delivery3.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.Main;
import com.mounts.lenovo.delivery3.activity.NewClass;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.helpers.SharePreferenceManager;
import com.mounts.lenovo.delivery3.model.MyoTwin_Order;
import com.mounts.lenovo.delivery3.response.AddOrderInfo;
import com.mounts.lenovo.delivery3.response.GetTown;
import com.mounts.lenovo.delivery3.response.ReceiverCity;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;
import com.mounts.lenovo.delivery3.service.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendOrderFragmentOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendOrderFragmentOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendOrderFragmentOne extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RetrofitService service;
    private ApiInterface api;
    private EditText edtorderName, edtEstimatedWeight, edtName, edtPhone, edtAddress;
    private Spinner citySpinner;
    private Spinner prePostSpinner;
    ArrayAdapter<CharSequence> adapter;

    public String orderName, receiverName, receiverPhone, receiverAddress, receiverCity;
    private Button btnNext;
    private int estimatedWeight;
    private boolean isPrePaid, isPostPaid;
    private String token;
    private List<ReceiverCity> cities;
    private Toolbar toolbar;
    public  static DrawerLayout drawerLayout;
    private String prePostSelectedValue;
    private String citySelectedValue;
    private  boolean error = true;

    private OnFragmentInteractionListener mListener;

    public SendOrderFragmentOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendOrderFragmentOne.
     */
    // TODO: Rename and change types and number of parameters
    public static SendOrderFragmentOne newInstance(String param1, String param2) {
        SendOrderFragmentOne fragment = new SendOrderFragmentOne();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Main.toolbar.setVisibility(View.VISIBLE);
        Main.toolbar.setTitle("မြို့တွင်း Order");
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_orders, container, false);


        edtorderName = view.findViewById(R.id.orderName);
        edtEstimatedWeight = view.findViewById(R.id.edtEstimtedWeight);
        edtName = view.findViewById(R.id.receiver_name);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);
        btnNext = view.findViewById(R.id.btnNext);
        citySpinner = (Spinner)view.findViewById(R.id.city_spinner);
        prePostSpinner = (Spinner) view.findViewById(R.id.pre_Post_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.pre_post_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        prePostSpinner.setAdapter(adapter);
        getCity();
        prePostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Pre/Post", parent.getItemAtPosition(position).toString());
                prePostSelectedValue = parent.getItemAtPosition(position).toString();

                if(prePostSelectedValue.equals("Prepaid") ){
                    isPostPaid =  false;
                    isPrePaid =  true;

                }else if(prePostSelectedValue.equals("Post paid")){
                    isPostPaid =  true;
                    isPrePaid =  false;
                }
                Log.e("Pre/",String.valueOf(isPrePaid));
                Log.e("Post/",String.valueOf(isPostPaid));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            prePostSelectedValue = "Prepaid";
                if(prePostSelectedValue == "Prepaid"){
                    isPostPaid =  false;
                    isPrePaid =  true;

                }else if(prePostSelectedValue == "Post paid"){
                    isPostPaid =  true;
                    isPrePaid =  false;
                }
                Log.e("Pre/",String.valueOf(isPrePaid));
                Log.e("Post/",String.valueOf(isPostPaid));
            }
        });
        citySpinner.setOnItemSelectedListener(this);


        cities = new ArrayList<>();



        btnNext.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void getCity() {

        RetrofitService.getInstance().getApi().getOfficeTown().enqueue(new Callback<GetTown>() {
            @Override
            public void onResponse(Call<GetTown> call, Response<GetTown> response) {
                if (response.isSuccessful()) {
                    //     Token.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xOTIuMTY4LjEwMC4xOTk6ODAwMFwvYXBpXC91c2VyXC9yZWdpc3RlciIsImlhdCI6MTU3MTczMDk2NywibmJmIjoxNTcxNzMwOTY3LCJqdGkiOiJyVjBFVXpsVzU0RkswQnRFIiwic3ViIjoxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.SU_KyTsWznj1gDLtWoKnjcaFNwYoeDSopW8oi2sDcvg");
                    Log.e("citySpinner", response.body().towns.toString());             //String.valueOf(response.body().cities.size())
                     cities.addAll(response.body().towns);

                    List<String> towns = new ArrayList<>();

                    for (ReceiverCity town : cities) {

                      towns.add(town.getName());
                    }


                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, towns);
                   spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                   citySpinner.setAdapter(spinnerAdapter);
//                    TODO: arrayadpater activity mhar use tae har nae change p use ya mhar...

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public boolean checkError(){

        if (edtorderName.getText().toString().isEmpty()){
            edtorderName.setError("Enter Order Name");
            error = false;
        } else orderName = edtorderName.getText().toString();

        if (edtEstimatedWeight.getText().toString().isEmpty()){
            edtEstimatedWeight.setError("Enter Package Weight");
            error = false;
        }
        else estimatedWeight = Integer.parseInt(edtEstimatedWeight.getText().toString());

        if (edtName.getText().toString().isEmpty()){
            edtName.setError("Enter Receiver Name");
            error = false;
        } else receiverName = edtName.getText().toString();

        if (edtPhone.getText().toString().isEmpty()){
            edtPhone.setError("Enter Receiver Phone");
            error = false;
        } else receiverPhone = edtPhone.getText().toString();

        if (edtAddress.getText().toString().isEmpty()){
            edtAddress.setError("Enter Receiver Address");
            error = false;
        } else receiverAddress = edtAddress.getText().toString();
        if(orderName != null && estimatedWeight != 0 && receiverName != null &&
        receiverPhone != null && receiverAddress != null ) {
            SharePreferenceManager.getInstance(getContext()).saveOrder(new MyoTwin_Order(
                    receiverCity,
                    orderName,
                    estimatedWeight,
                    receiverName,
                    receiverPhone,
                    receiverAddress,
                    receiverCity,
                    isPrePaid,
                    isPostPaid,
                    Token.token,
                    1));
            error = true;

        }else {
            error = false;
        }

        return error;
    }
    @Override
    public void onClick(View v) {
        Log.e("onclick", "ok");

            if(checkError()){
            MapFragmentTEST mapFragmentTEST = new MapFragmentTEST();
            Bundle args = new Bundle();
            args.putString("YourKey", "YourValue");
//            args.putString("OrderName", "YourValue");
//            args.putString("Weight", "YourValue");
//            args.putString("pre_Post", "YourValue");
//            args.putString("YourKey", "YourValue");
//            args.putString("YourKey", "YourValue");
//            args.putString("YourKey", "YourValue");
//            args.putString("YourKey", "YourValue");
            mapFragmentTEST.setArguments(args);
            setFragment(mapFragmentTEST);

            }



//submit
     /*   RetrofitService.getInstance().getApi().
                fullOrderInformation(citySelectedValue, orderName,
                        estimatedWeight, receiverName, receiverPhone,
                        receiverAddress, receiverCity, isPrePaid,
                        isPostPaid, token)
                .enqueue(new Callback<AddOrderInfo>() {
            @Override
            public void onResponse(Call<AddOrderInfo> call, Response<AddOrderInfo> response) {
                if (response.isSuccessful()) {
                    Log.e("make_order", "OK");
//                    Intent intent = new Intent(getContext(), NewClass.class);
//                    startActivity(intent);
                        setFragment(new FirstHomeFragment());
//                    TODO: get token from header

                } else {
                    Log.e("make_order", "fail");
                }
            }

            @Override
            public void onFailure(Call<AddOrderInfo> call, Throwable t) {

                Log.e("response", "fail"+t.getMessage());

            }
        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//       // Log.e("selecetd",parent.getItemAtPosition(position).toString());
//        if(view.getId() == R.id.pre_Post_spinner) {
//            Log.e("Pre/Post", parent.getItemAtPosition(position).toString());
//            prePostSelectedValue = parent.getItemAtPosition(position).toString();
//
//            if(prePostSelectedValue == "Prepaid"){
//                isPostPaid =  false;
//                isPrePaid =  true;
//
//            }else if(prePostSelectedValue == "Post paid"){
//                isPostPaid =  true;
//
        Log.e("City", parent.getItemAtPosition(position).toString());
            receiverCity = parent.getItemAtPosition(position).toString();


    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment).commit();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
