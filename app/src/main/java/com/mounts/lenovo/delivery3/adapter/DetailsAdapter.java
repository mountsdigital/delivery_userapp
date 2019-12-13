package com.mounts.lenovo.delivery3.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.holder.DetailsHolder;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsHolder> {
    DetailsHolder.OnItemClickListener listener;

    public DetailsAdapter(DetailsHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetailsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return DetailsHolder.create(inflater, parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
