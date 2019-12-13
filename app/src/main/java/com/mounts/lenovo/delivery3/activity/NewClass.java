package com.mounts.lenovo.delivery3.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.holder.SeeAllHolder;

public class NewClass extends AppCompatActivity implements View.OnClickListener, SeeAllHolder.OnItemClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_recycler_view);
    }

    @Override
    public void onClick(View v) {

    }
}
