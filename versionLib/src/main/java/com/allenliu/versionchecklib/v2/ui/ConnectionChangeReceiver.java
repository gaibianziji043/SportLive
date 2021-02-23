package com.allenliu.versionchecklib.v2.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.allenliu.versionchecklib.utils.AllenEventBusUtil;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.eventbus.AllenEventType;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    DownloadBuilder builder;
    DownloadFailedActivity activity;
    Dialog downloadFailedDialog;
    public ConnectionChangeReceiver (DownloadBuilder builder, DownloadFailedActivity activity, Dialog downloadFailedDialog) {
        this.builder = builder;
        this.activity = activity;
        this.downloadFailedDialog = downloadFailedDialog;
    }

    private int status = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo  mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null != mobNetInfo && !mobNetInfo.isConnected() && null != wifiNetInfo && !wifiNetInfo.isConnected()) {
            status = -1;
        } else {
            if (status == -1) {
                status = 0;
                if ( null != builder && null != activity && null != downloadFailedDialog && downloadFailedDialog.isShowing()) {
                   /* if(builder.getDownloadFailedCommitClickListener()!=null){
                        builder.getDownloadFailedCommitClickListener().onCommitClick();
                    }
                    AllenEventBusUtil.sendEventBus(AllenEventType.START_DOWNLOAD_APK);
                    activity.finish();*/
                }
            }
        }
    }
}