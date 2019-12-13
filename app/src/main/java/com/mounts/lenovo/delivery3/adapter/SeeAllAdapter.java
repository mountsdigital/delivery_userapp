package com.mounts.lenovo.delivery3.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.fragment.SeeAllFragment;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllHolder> {

    SeeAllHolder.OnItemClickListener listener;

    public SeeAllAdapter(SeeAllHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SeeAllHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return SeeAllHolder.create(inflater, parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
