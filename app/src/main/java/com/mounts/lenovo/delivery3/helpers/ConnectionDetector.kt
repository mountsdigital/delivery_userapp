package com.mounts.lenovo.delivery3.helpers

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import android.location.LocationManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService





object ConnectionDetector {
        @JvmStatic
        fun networkStatus (context: Context) :Boolean{

        return  (ConnectionDetector.isWifiAvailable(context) || ConnectionDetector.isMobileNetworkAvailable(context));
        }
         @JvmStatic
         fun isWifiAvailable(context: Context): Boolean {
             val connecManager = context
                     .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

             val myNetworkInfo = connecManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                     //.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

              if (myNetworkInfo.isConnected) {return true}
              else {return false}



        }
    @JvmStatic
    fun isMobileNetworkAvailable(context: Context):Boolean{
        val myConnManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val myNetworkInfo = myConnManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) as NetworkInfo

       return if (myNetworkInfo.isConnected()) true;
        else false;
    }
    @JvmStatic
    fun isGpsStatusEnabled(context: Context):Boolean{
        val locationManager = context
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            true
        else
            false

    }
    @JvmStatic
    fun displayNoNetworkDialoag(activity:Activity){
        val builder =  AlertDialog.Builder(activity);

        builder.setMessage("No Connection")
                .setTitle("No Connection")
                .setCancelable(false)

        builder.setPositiveButton("EXIT"){dialog, which ->
                    dialog.cancel()
                    activity.finish()

                }
                        builder.setNegativeButton("Cancel"){
                            dialog, which ->
                            val intent = activity.intent
                            activity.finish()
                            activity.startActivity(intent)
                            dialog.cancel()
                        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}


