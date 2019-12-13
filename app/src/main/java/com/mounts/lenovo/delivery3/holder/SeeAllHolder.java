package com.mounts.lenovo.delivery3.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;

public class SeeAllHolder extends RecyclerView.ViewHolder {

    private SeeAllHolder.OnItemClickListener listener;
    private Button btn;

    public SeeAllHolder(@NonNull View itemView, SeeAllHolder.OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;

    }

    public static SeeAllHolder create(LayoutInflater inflater, ViewGroup viewGroup, SeeAllHolder.OnItemClickListener listener) {

        View view = inflater.inflate(R.layout.see_all_list, viewGroup, false);
        return new SeeAllHolder(view, listener);

    }

    public static void bindData() {
    }

    public interface OnItemClickListener {
    }
}
