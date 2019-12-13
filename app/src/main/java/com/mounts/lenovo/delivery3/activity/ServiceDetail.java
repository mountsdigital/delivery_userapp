package com.mounts.lenovo.delivery3.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.Objects;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.adapter.DetailsAdapter;
import com.mounts.lenovo.delivery3.adapter.ImageAdapter;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.fragment.CategoriesFragment;
import com.mounts.lenovo.delivery3.holder.DetailsHolder;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;

public class ServiceDetail extends AppCompatActivity {

    private ImageView backarrow;
    private FragmentManager fragmentManager;
    private RelativeLayout relativeLayoutofReview;
    AlertDialog.Builder builder;
    private RatingBar ratingBar;
    private TextView tvRatingScale;
    private EditText etFeedback;
    private TextView cancel_btn, yes_btn;


    @SuppressLint("WrongConstant")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Arrive", "ServiceDetail.java");
        setContentView(R.layout.service_detail);
        backarrow = findViewById(R.id.backarrow_service_detail);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked", "backarrow");
                finish();
            }
        });
        relativeLayoutofReview = findViewById(R.id.relativeLayoutofReview);
        relativeLayoutofReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ServiceDetail.this);

                final Dialog dialog = new Dialog(ServiceDetail.this);
                dialog.setContentView(R.layout.ratingfordetailservice);
                dialog.setCancelable(true);
                ratingBar = (RatingBar) dialog.findViewById(R.id.ratingbar);
                tvRatingScale = dialog.findViewById(R.id.tvRatingScale);
                etFeedback = dialog.findViewById(R.id.etFeedback);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar rateBar, float rating, boolean fromUser) {
                        tvRatingScale.setText(String.valueOf(rating));
                        switch ((int) rateBar.getRating()) {
                            case 1:
                                tvRatingScale.setText("Very bad");
                                break;
                            case 2:
                                tvRatingScale.setText("Need some improvement");
                                break;
                            case 3:
                                tvRatingScale.setText("Good");
                                break;
                            case 4:
                                tvRatingScale.setText("Great");
                                break;
                            case 5:
                                tvRatingScale.setText("I love it");
                                break;
                            default:
                                tvRatingScale.setText("");
                        }
                    }
                });
                etFeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etFeedback.getText().toString().isEmpty()) {
                            Toast.makeText(ServiceDetail.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                        } else {
                            etFeedback.setText("");
                            ratingBar.setRating(0);
                            Toast.makeText(ServiceDetail.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancel_btn = dialog.findViewById(R.id.textView_cancel);
                yes_btn = dialog.findViewById(R.id.textView_yes);

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Clicked Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
                yes_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "Clicked Yes", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
//            "ServiceDetail1" mhar lo tr twe nae nae kyi loh ya tal nor,ma lo tog yin tog delete lite tog...
        });
    }

}
