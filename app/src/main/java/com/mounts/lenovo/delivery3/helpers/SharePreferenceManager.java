package com.mounts.lenovo.delivery3.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.mounts.lenovo.delivery3.model.MyoTwin_Order;

public class SharePreferenceManager {
    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharePreferenceManager mInstance;
    private Context mCtx;

    private SharePreferenceManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharePreferenceManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharePreferenceManager(mCtx);
        }
        return mInstance;
    }
    public void saveOrder(MyoTwin_Order order) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user_City", order.getUser_City());
        editor.putString("order_Name", order.getOrder_Name());
        editor.putInt("weight", order.getWeight());
        editor.putString("receiver_Name", order.getReceiver_Name());
        editor.putString("receiver_Phone", order.getReceiver_Phone());
        editor.putString("receiver_Address", order.getReceiver_Address());
        editor.putString("receiver_City", order.getReceiver_City());
        editor.putBoolean("pre_Paid", order.getPre_Paid());
        editor.putBoolean("post_Paid", order.getPost_Paid());
        editor.putString("session_Id", order.getSession_Id());
        editor.putInt("same_city", order.getSame_city());

        // editor.putString("name", response.getName());
        // editor.putString("school", user.getSchool());

        editor.apply();

    }

    public MyoTwin_Order getOrder(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new MyoTwin_Order(
                sharedPreferences.getString("user_City", null),
                sharedPreferences.getString("order_Name", null),
                sharedPreferences.getInt("weight", 0),
                sharedPreferences.getString("receiver_Name", null),
                sharedPreferences.getString("receiver_Phone", null),
                sharedPreferences.getString("receiver_Address", null),
                sharedPreferences.getString("receiver_City", null),
                sharedPreferences.getBoolean("pre_Paid", true),
                sharedPreferences.getBoolean("post_Paid",false ),
                sharedPreferences.getString("session_Id", null),
                sharedPreferences.getInt("same_city", 1)
               );
    }
    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
