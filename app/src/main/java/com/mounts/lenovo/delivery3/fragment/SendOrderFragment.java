package com.mounts.lenovo.delivery3.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.NewAdapter;
import com.mounts.lenovo.delivery3.holder.NewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendOrderFragment extends Fragment implements View.OnClickListener, NewHolder.OnItemClickListener {

    private RecyclerView recyclerView;
    NewAdapter adapter;
    private TextView txt1, txt2, txt3;
    private TextView btnTrack;

    public SendOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_order, container, false);
//        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView = view.findViewById(R.id.recyclerView);
        txt1 = view.findViewById(R.id.text1);
        txt2 = view.findViewById(R.id.text2);
        txt3 = view.findViewById(R.id.text3);
        btnTrack = view.findViewById(R.id.btnTrack);
        String[] stringArgs = new String[]{"Mg Mg, Ma Ma, Hla Hla"};
        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        btnTrack.setOnClickListener(this);
        adapter = new NewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
