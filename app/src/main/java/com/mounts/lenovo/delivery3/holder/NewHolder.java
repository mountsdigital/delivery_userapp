package com.mounts.lenovo.delivery3.holder;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.mounts.lenovo.delivery3.R;

public class NewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemClickListener listener;
    private TextView txt1, txt2, txt3;
     public  static Button btn;
     public Context context;
   public static View itemView;

    public NewHolder(@NonNull  View itemView, OnItemClickListener listener) {
        super(itemView);
       //this.context = context;
        this.itemView = itemView;
        this.listener = listener;
        this.context = context;
        txt1 = itemView.findViewById(R.id.text1);
        txt2 = itemView.findViewById(R.id.text2);
        txt3 = itemView.findViewById(R.id.text3);
        btn = itemView.findViewById(R.id.btnTrack);

     //   btn.setOnClickListener(this);


    }

    public static NewHolder create(LayoutInflater inflater, ViewGroup viewGroup, NewHolder.OnItemClickListener listener) {

        View view = inflater.inflate(R.layout.layout_fragment_layout, viewGroup, false);
        Log.e("holder", "success");

        return new NewHolder(view, listener);


    }

    public static void bindData(final int position){//final ContentItem item, final OnItemClickListener listener) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Tracked"+position,Toast.LENGTH_SHORT).show();
                Log.e("OnClicked","ItemClicked"+position);
            }
        });

    }

    @Override
    public void onClick(View v) {
       // Toast.makeText(context,"Clicled",Toast.LENGTH_SHORT).show();
        Log.e("OnClicked","ItemClicked");
    //    Toast.makeText(v.getContext(),"Tracked",Toast.LENGTH_SHORT).show();
    }

    public interface OnItemClickListener {
    void onItemClicked(View view);

    }

}
