package com.mounts.lenovo.delivery3.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.holder.NewHolder;

public class NewAdapter extends RecyclerView.Adapter<NewHolder> implements NewHolder.OnItemClickListener {
    private String [] mDataset;
    NewHolder.OnItemClickListener listener;

    public NewAdapter(NewHolder.OnItemClickListener listener){
        this.mDataset = mDataset;
        this.listener = listener;


    }

    @NonNull
    @Override
    public NewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return NewHolder.create(inflater,viewGroup,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewHolder newHolder, int i) {

        NewHolder.bindData(i);

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    @Override
    public void onItemClicked(View view) {
        Toast.makeText(view.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
    }
}
