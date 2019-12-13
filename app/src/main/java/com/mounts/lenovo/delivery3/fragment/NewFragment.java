package com.mounts.lenovo.delivery3.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.NewAdapter;
import com.mounts.lenovo.delivery3.holder.NewHolder;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment implements NewHolder.OnItemClickListener {
    private RecyclerView recyclerView;
    private RetrofitService service;
    NewAdapter adapter;

    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initFragment(view);
        return view;

    }

    private void initFragment(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        service = new RetrofitService();
        adapter = new NewAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClicked(View view) {

    }
}
