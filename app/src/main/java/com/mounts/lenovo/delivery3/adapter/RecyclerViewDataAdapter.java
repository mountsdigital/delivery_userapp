package com.mounts.lenovo.delivery3.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.holder.RecyclerViewDataHolder;
import com.mounts.lenovo.delivery3.response.CategoryData;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataHolder> {

    private List<CategoryData> categoryDataList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener listener;

    public RecyclerViewDataAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerViewDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.e("right here", "ok");
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return RecyclerViewDataHolder.create(inflater, viewGroup, listener);
    }

    public void onBindViewHolder(@NonNull RecyclerViewDataHolder holder, int position) {
        holder.bindData(categoryDataList.get(position));

    }

    @Override
    public int getItemCount() {
        return categoryDataList.size();
//        return (null != dataList ? dataList.size() : 0);
    }

    public void addData(List<CategoryData> categoryDataList) {
        if (this.categoryDataList.size() == 0) {
            this.categoryDataList = categoryDataList;
        } else categoryDataList.addAll(categoryDataList);
        notifyDataSetChanged();
    }

}
