package com.mounts.lenovo.delivery3.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.SectionListDataAdapter;
import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.response.CategoryData;

public class RecyclerViewDataHolder extends RecyclerView.ViewHolder implements OnItemClickListener {
    private TextView categoryName, seeAll;
    private RecyclerView recyclerViewCate;
    private OnItemClickListener listener;

    public RecyclerViewDataHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        initHolder(itemView);
    }

    public static RecyclerViewDataHolder create(LayoutInflater inflater, ViewGroup viewGroup, OnItemClickListener listener) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new RecyclerViewDataHolder(view , listener);
    }

    private void initHolder(View itemView) {
        categoryName = itemView.findViewById(R.id.categoryName);
        seeAll = itemView.findViewById(R.id.seeAll_1);
        recyclerViewCate = itemView.findViewById(R.id.recycler_view_list);
    }

    public void bindData(final CategoryData data) {
        categoryName.setText(data.cateName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        SectionListDataAdapter adapter = new SectionListDataAdapter(this);

        recyclerViewCate.setAdapter(adapter);
        recyclerViewCate.setLayoutManager(layoutManager);
        adapter.addData(data.addsOnServices);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.cateId);
            }
        });
    }

    @Override
    public void onItemClick(int id) {

    }
}
