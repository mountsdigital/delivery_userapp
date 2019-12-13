package com.mounts.lenovo.delivery3.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.response.AddsOnServices;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

public class SectionListDataHolder extends RecyclerView.ViewHolder {
    //    public SectionListDataHolder(@NonNull View itemView) {
//        super(itemView);
//    }
    private OnItemClickListener listener;
    private ImageView itemImage;
    private TextView shopName, shopAddress;

    public SectionListDataHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        initHolder(itemView);
    }

    private void initHolder(View itemView) {
        itemImage = itemView.findViewById(R.id.itemImage);
        shopName = itemView.findViewById(R.id.shop_name);
        shopAddress = itemView.findViewById(R.id.shop_address);
    }

    public void bindData(final AddsOnServices addsOnServices) {

        Picasso.get().load(RetrofitService.BASE_URL + "/api/download_image/" + addsOnServices.logo).into(itemImage);
        shopName.setText(addsOnServices.name);
        shopAddress.setText(addsOnServices.address);
    }

    public static SectionListDataHolder create(LayoutInflater inflater, ViewGroup parent, OnItemClickListener listener) {

        View view = inflater.inflate(R.layout.list_single_card, parent, false);
        return new SectionListDataHolder(view, listener);
    }
}
