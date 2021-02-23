package com.allenliu.versionchecklib.v2.callback;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.allenliu.versionchecklib.v2.builder.UIData;



public interface CustomDownloadFailedListener {
    Dialog getCustomDownloadFailed(Context context,UIData versionBundle);

}
