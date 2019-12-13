package com.mounts.lenovo.delivery3.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.mounts.lenovo.delivery3.R;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.response.LoginResponse;
import com.mounts.lenovo.delivery3.retrofit.RetrofitService;
import com.mounts.lenovo.delivery3.service.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginAcivity";
    private TextInputLayout phoneNumber, password;
    private Button signIn;
    private TextView signUp;
    private String phone;
    private String password1;
    private String token;
    private ProgressBar progressBar;
    SharedPreferences pref;// 0 - for private mode
    SharedPreferences.Editor editor;
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        Log.e("login_page", "success");

//        Bundle bundle = getIntent().getExtras();
//        token = bundle.getString("Token");


//        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
//        editor = pref.edit();
        initLogin();
    }

    private void initLogin() {
        Log.e("initLogin", "success");
        retrofitService = new RetrofitService();
        phoneNumber = findViewById(R.id.phoneNumberLogin);
        password = findViewById(R.id.passwordLogin);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);

        if (token != null) {
//            Token.setToken(token);
//            Intent intent = new Intent(getApplicationContext(), Home.class);
//            intent.putExtra("Token", token);//sent token
//            startActivity(intent);
            finish();
        } else {
            signIn.setOnClickListener(this);
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Register.class);
                    Log.e("to register", "success");
                    startActivity(intent);
                }
            });
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        if (v == signIn) {
//            progressBar.setVisibility(View.GONE);
            phone = phoneNumber.getEditText().getText().toString();
            password1 = password.getEditText().getText().toString();

            Log.e("phone_num", phone);

            Log.e("pass", password1);
            if (phone.isEmpty()) {
                phoneNumber.setError("Phone is Empty");
            } else if (password1.isEmpty()) {
                password.setError("Password require!");
            } else {
//                TODO:just intent to home class
//                Intent intent = new android.content.Intent(getApplicationContext(), Home.class);
//                intent.putExtra("Token", token);//sent token
//                startActivity(intent);
//                TODO:don't forget to delete when got the token from server
                userLogin(phone, password1);
            }

        } else if (v == signUp) {
            userRegister();
        }
    }

    private void userRegister() {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
        Log.e("token", "to sign in");
        Toast.makeText(Login.this, "To Sign In...", Toast.LENGTH_LONG).show();
    }

    private void userLogin(String phone, String pass1) {
        Log.e("token", "Done");
        RetrofitService.getInstance().getApi().login(phone, pass1).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
//                    intent.putExtra("Token", token);
                    Log.e("Login", "true");
                    Log.e("Token", response.body().sessionId);

                    Token.token = response.body().sessionId;
                    //Token.token=token;

//                        editor.putString("token", token1);
//                        editor.apply();
//                        editor.commit();

////                    intent = new Intent(getApplicationContext(), Main.class);
//////                    intent.putExtra("Token", response.body().sessionId);
////                    startActivity(intent);

//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getBaseContext() , Main.class);
                    startActivity(intent1);
                    finish();

                } else {

                    Toast.makeText(Login.this, "Login fail", Toast.LENGTH_LONG).show();
//                    Log.e("error message", response.body().error_message);
                    Log.e("Sign In Fail", "Yes");
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Throwable", t.toString());
                Toast.makeText(Login.this, "Sign In Fail...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);

            }
        });
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
