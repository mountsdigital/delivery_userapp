package com.mounts.lenovo.delivery3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mounts.lenovo.delivery3.R;

public class Home extends AppCompatActivity {
    private Button btnAddOrder;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnAddOrder = findViewById(R.id.btnAddOrder);
//        Bundle bundle=getIntent().getExtras();
//        token=bundle.getString("Token");

//        Log.e("sessionIdHome_home",token);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Main.class);
//                intent.putExtra("Token", token);//sent token
                startActivity(intent);

//                startActivity(intent);
//                Intent intent=new Intent(Login.this,Home.class);
//                intent.putExtra("Token","This is token id.");
//                startActivity(intent);
            }
        });
    }

}
