package com.mounts.lenovo.delivery3.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.RecyclerViewDataAdapter;
import com.mounts.lenovo.delivery3.adapter.SeeAllAdapter;
import com.mounts.lenovo.delivery3.holder.NewHolder;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeeAllFragment extends Fragment implements View.OnClickListener, SeeAllHolder.OnItemClickListener{
    private RecyclerView recyclerView;
    SeeAllAdapter adapter;

    public SeeAllFragment() {
        // Required empty public constructor
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_all_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.seeall_recyclerview);

        adapter = new SeeAllAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {

    }

}
