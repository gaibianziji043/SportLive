package com.allenliu.versionchecklib.v2.ui;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allenliu.versionchecklib.R;
import com.allenliu.versionchecklib.core.http.AllenHttp;
import com.allenliu.versionchecklib.utils.ALog;
import com.allenliu.versionchecklib.v2.eventbus.AllenEventType;
import com.allenliu.versionchecklib.v2.eventbus.CommonEvent;

import org.greenrobot.eventbus.EventBus;

public class DownloadingActivity extends AllenBaseActivity implements DialogInterface.OnCancelListener {
    public static final String PROGRESS = "progress";
    public static Dialog downloadingDialog;
    private int currentProgress = 0;
    protected boolean isDestroy = false;

    public static DownloadingActivity downloadingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ALog.e("loading activity create");

        showLoadingDialog();

        downloadingActivity = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    if (VersionService.status == 1) {
                        destroy();
                    }
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }

                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                }
            });
        }
    }

    public void onCancel(boolean isDownloadCompleted) {
        if (!isDownloadCompleted) {
            AllenHttp.getHttpClient().dispatcher().cancelAll();
            cancelHandler();
            checkForceUpdate();
        }
        finish();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        onCancel(false);
    }


    @Override
    public void receiveEvent(CommonEvent commonEvent) {
        super.receiveEvent(commonEvent);
        switch (commonEvent.getEventType()) {
            case AllenEventType.UPDATE_DOWNLOADING_PROGRESS:
                int progress = (int) commonEvent.getData();
                currentProgress = progress;
                updateProgress();
                break;
            case AllenEventType.DOWNLOAD_COMPLETE:
                onCancel(true);
                break;
            case AllenEventType.CLOSE_DOWNLOADING_ACTIVITY:
                destroy();
                EventBus.getDefault().removeStickyEvent(commonEvent);
                break;
        }
    }

    @Override
    public void showDefaultDialog() {
        View loadingView = LayoutInflater.from(this).inflate(R.layout.downloading_layout, null);
        downloadingDialog = new AlertDialog.Builder(this).setTitle("").setView(loadingView).create();
        downloadingDialog.setCancelable(false);


        downloadingDialog.setCanceledOnTouchOutside(false);
        ProgressBar pb = loadingView.findViewById(R.id.pb);
        TextView tvProgress = loadingView.findViewById(R.id.tv_progress);
        tvProgress.setText(String.format(getString(R.string.versionchecklib_progress), currentProgress));
        pb.setProgress(currentProgress);
        downloadingDialog.show();
    }

    @Override
    public void showCustomDialog() {
        if(getVersionBuilder()!=null) {
            downloadingDialog = getVersionBuilder().getCustomDownloadingDialogListener().getCustomDownloadingDialog(this, currentProgress, getVersionBuilder().getVersionBundle());
            downloadingDialog.setCancelable(false);
            View cancelView = downloadingDialog.findViewById(R.id.versionchecklib_loading_dialog_cancel);
            if (cancelView != null) {
                cancelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCancel(false);
                    }
                });
            }
            downloadingDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyWithOutDismiss();
        isDestroy = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isDestroy = false;
        if (downloadingDialog != null && !downloadingDialog.isShowing())
            downloadingDialog.show();
    }

    private void destroyWithOutDismiss() {
        if (downloadingDialog != null && downloadingDialog.isShowing()) {
            downloadingDialog.dismiss();
//            onCancel(false);
        }
    }

    public void destroy() {
        ALog.e("loading activity destroy");
        if (downloadingDialog != null && downloadingDialog.isShowing()) {
            downloadingDialog.dismiss();
//            onCancel(false);
        }
        finish();
    }

    private void updateProgress() {
        if (!isDestroy) {
            if (getVersionBuilder() != null && getVersionBuilder().getCustomDownloadingDialogListener() != null) {
                getVersionBuilder().getCustomDownloadingDialogListener().updateUI(downloadingDialog, currentProgress, getVersionBuilder().getVersionBundle());
            } else {
                ProgressBar pb = downloadingDialog.findViewById(R.id.pb);
                pb.setProgress(currentProgress);
                TextView tvProgress = downloadingDialog.findViewById(R.id.tv_progress);
                tvProgress.setText(String.format(getString(R.string.versionchecklib_progress), currentProgress));
                if (!downloadingDialog.isShowing())
                    downloadingDialog.show();
            }
        }
    }

    private void showLoadingDialog() {
        ALog.e("show loading");
        if (!isDestroy) {
            if (getVersionBuilder() != null && getVersionBuilder().getCustomDownloadingDialogListener() != null) {
                showCustomDialog();
            } else {
                showDefaultDialog();
            }
            downloadingDialog.setOnCancelListener(this);
        }
    }

}
