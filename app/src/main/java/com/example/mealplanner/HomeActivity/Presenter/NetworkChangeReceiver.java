package com.example.mealplanner.HomeActivity.Presenter;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import com.example.mealplanner.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

            if (ni != null && ni.isConnected()) {
                //showNetworkConnectedAlert(context);
            } else {
                showNetworkDisconnectedAlert(context);
            }
        }
    }

    private void showNetworkDisconnectedAlert(Context context) {
      AlertDialog dialog =  new AlertDialog.Builder(context)
                .setTitle("Network Disconnected")
                .setMessage("Your network connection is lost. Please check your settings.")
                .setPositiveButton("OK", null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);


    }

    private void showNetworkConnectedAlert(Context context) {
        AlertDialog dialog =   new AlertDialog.Builder(context)
                .setTitle("Network Connected")
                .setMessage("You are now connected to the network.")
                .setPositiveButton("OK", null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }
}
