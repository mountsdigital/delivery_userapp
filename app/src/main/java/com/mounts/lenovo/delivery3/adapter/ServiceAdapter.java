package com.mounts.lenovo.delivery3.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.fragment.CategoriesFragment;
import com.mounts.lenovo.delivery3.holder.ServiceHolder;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceHolder> {

    ServiceHolder.OnItemClickListener listener;

    public ServiceAdapter(CategoriesFragment listener) {

        this.listener = (ServiceHolder.OnItemClickListener) listener;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return ServiceHolder.create(inflater, viewGroup, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder serviceHolder, int i) {

        ServiceHolder.bindData();

    }

    @Override
    public int getItemCount() {

        return 1;
    }
}
