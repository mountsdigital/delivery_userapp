package com.mounts.lenovo.delivery3.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.NewAdapter;
import com.mounts.lenovo.delivery3.holder.NewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiverOrderFragment extends Fragment implements View.OnClickListener, NewHolder.OnItemClickListener {
    private RecyclerView recyclerView;
    NewAdapter adapter;
    private TextView txt1, txt2, txt3, btn;
    private Button btnTrack;


    public ReceiverOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receiver_order, container, false);

        Log.e("YouReachReceiverOrder", "success");
        recyclerView = view.findViewById(R.id.recyclerView);
        txt1 = view.findViewById(R.id.text1);
        txt2 = view.findViewById(R.id.text2);
        txt3 = view.findViewById(R.id.text3);
        btnTrack = view.findViewById(R.id.btnTrack);
        String[] stringArgs = new String[]{"Mg Mg, Ma Ma, Hla Hla"};
        adapter = new NewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        btnTrack.setOnClickListener(this);
        return view;

    }



    @Override
    public void onClick(View v) {

        Toast.makeText(v.getContext(),"Clicked",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemClicked(View view) {
        Toast.makeText(view.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
    }
}
