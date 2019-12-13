package com.mounts.lenovo.delivery3.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mounts.lenovo.delivery3.api.ApiInterface;
import com.mounts.lenovo.delivery3.service.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {
    //    public static final String BASE_URL = "http://192.168.100.173:8000";
//
//    public Retrofit getService() {
//        Retrofit build = new Retrofit
//                .Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return build;
//    }
    private ApiInterface api;//http://192.168.100.199:8000
    // 178.128.23.146
    public static final String BASE_URL = "http://178.128.23.146/";
    private static RetrofitService instance;
    public static synchronized RetrofitService getInstance() {
        if (instance == null) {
            instance = new RetrofitService();
        }

        return instance;
    }

    public RetrofitService() {
        buildRetrofit();
    }
    private void buildRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + Token.token)
                      //  .addHeader("Accept :"," application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        api =  retrofit.create(ApiInterface.class );
    }
    public ApiInterface getApi() {
        return api;
    }
}
