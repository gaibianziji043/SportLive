package com.allenliu.versionchecklib.callback;

import java.io.File;



public interface APKDownloadListener {
     void onDownloading(int progress);
    void onDownloadSuccess(File file);
    void onDownloadFail();
}
