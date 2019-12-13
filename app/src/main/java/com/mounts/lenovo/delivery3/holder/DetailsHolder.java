package com.mounts.lenovo.delivery3.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;

public class DetailsHolder extends RecyclerView.ViewHolder {

    private DetailsHolder.OnItemClickListener listener;
    private Button btn;

    public DetailsHolder(@NonNull View itemView, DetailsHolder.OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;

    }

    public static DetailsHolder create(LayoutInflater inflater, ViewGroup viewGroup, DetailsHolder.OnItemClickListener listener) {
        View view = inflater.inflate(R.layout.details_list, viewGroup, false);
        return new DetailsHolder(view, listener);
    }

    public static void bindData() {
    }

    public interface OnItemClickListener {
    }
}