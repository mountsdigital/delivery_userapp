package com.mounts.lenovo.delivery3.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.activity.NextOrder;
import com.mounts.lenovo.delivery3.adapter.NewAdapter;
import com.mounts.lenovo.delivery3.holder.NewHolder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstHomeFragment extends Fragment implements View.OnClickListener, NewHolder.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ConstraintLayout myoTwin;
    private ConstraintLayout myoPyin;
    private ConstraintLayout service;
    Toolbar toolbar;
    private OnFragmentInteractionListener mListener;

    public FirstHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstHomeFragment newInstance(String param1, String param2) {
        FirstHomeFragment fragment = new FirstHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_first_home, container, false);
         myoTwin =view.findViewById(R.id.myoTwin);
         myoPyin =view.findViewById(R.id.myoPyin);
        service =view.findViewById(R.id.services);
         myoTwin.setOnClickListener(this);
         myoPyin.setOnClickListener(this);
         service.setOnClickListener(this);
        toolbar= view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


       // LinearLayout round = view.findViewById(R.id.roud_layout);
        recyclerView = view.findViewById(R.id.specialRecyclerView);
       NewAdapter adapter = new NewAdapter( this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.myoTwin :  Toast.makeText(getContext(),"Clicked Myo Twin",Toast.LENGTH_SHORT).show();
               // Intent intent = new Intent(getContext(), NextOrder.class);
                //intent.putExtra("myoTwin",getResources().getString(R.string.down_town));
                //startActivity(intent);
             //   toolbar.setVisibility(View.VISIBLE);
                setFragment(new SendOrderFragmentOne());


                break;
            case R.id.myoPyin :  Toast.makeText(getContext(),"Clicked Myo pyin",Toast.LENGTH_SHORT).show();
                break;
            case R.id.services :  Toast.makeText(getContext(),"Clicked Services",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void setFragment(Fragment fragment) {
        //FragmentManager fragmentManager=mContext.getSupportFragmentManager();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onItemClicked(View view) {

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
  /* ImageView mimageView=(ImageView)view. findViewById(R.id.round_image);
        Bitmap mbitmap=((BitmapDrawable) getResources().getDrawable(R.drawable.our_services)).getBitmap();
        Bitmap imageRounded=Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas=new Canvas(imageRounded);
        Paint mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint); // Round Image Corner 100 100 100 100
       mimageView.setImageBitmap(imageRounded);*/