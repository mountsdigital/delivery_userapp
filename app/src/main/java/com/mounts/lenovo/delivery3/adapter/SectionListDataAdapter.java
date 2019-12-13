package com.mounts.lenovo.delivery3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.api.OnItemClickListener;
import com.mounts.lenovo.delivery3.holder.RecyclerViewDataHolder;
import com.mounts.lenovo.delivery3.holder.SectionListDataHolder;
import com.mounts.lenovo.delivery3.response.AddsOnServices;

import java.util.ArrayList;
import java.util.List;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataHolder> {

    private List<AddsOnServices> addsOnServices = new ArrayList<>();
    private OnItemClickListener listener;
    private Context mContext;

    public SectionListDataAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public SectionListDataAdapter(RecyclerViewDataHolder recyclerViewDataHolder) {
//    }

    @Override
    public SectionListDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return SectionListDataHolder.create(inflater, parent, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionListDataHolder holder, int position) {
        holder.bindData(addsOnServices.get(position));
    }

    @Override
    public int getItemCount() {
        return addsOnServices.size();
//        return (null != itemsList ? itemsList.size() : 0);
    }

    public void addData(List<AddsOnServices> addsOnServices) {
        this.addsOnServices = addsOnServices;
    }
}
//    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
//
//        protected TextView tvTitle;
//
//        protected ImageView itemImage;
//
//        public SingleItemRowHolder(LayoutInflater view) {
//            super(view);
//            this.tvTitle = (TextView) view.findViewById(R.id.textView01);
//            this.tvTitle = (TextView) view.findViewById(R.id.textView02);
//            this.tvTitle = (TextView) view.findViewById(R.id.textView03);
//            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
////            this.tvTitle = view.findViewById(R.id.seeAll_1);
//
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), tvTitle.getText(), Toast.LENGTH_SHORT).show();
//                    Log.e("clicked", "photo or text");
//                }
//            });
//        }
//    }


//public class MedicineItemAdapter extends RecyclerView.Adapter<MedicineItemHolder> {
//
//    private Lists<Medicine> medicines = new ArrayList<>();
//    private OnItemClickListener listener;
//
//    public MedicineItemAdapter(OnItemClickListener listener){
//        this.listener = listener;
//    }
//    @NonNull
//    @Override
//    public MedicineItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        return MedicineItemHolder.create(inflater , parent ,listener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MedicineItemHolder holder, int position) {
//
//        holder.bindData(medicines.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return medicines.size();
//    }
//
//    public void addData(Lists<Medicine> medicines){
//        this.medicines = medicines;
//    }
//}