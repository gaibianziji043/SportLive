package com.ft.base.common.utils;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class TimeUtils {
    private UpdateTimeRunnable updateTimeRunnable;
    private Handler handler;
    private long time = 0;

    private static TimeUtils instance;

    public static TimeUtils getInstance() {
        if (null == instance) {
            synchronized (TimeUtils.class) {
                if (null == instance) {
                    instance = new TimeUtils();
                }
            }
        }
        return instance;
    }

    public TimeUtils() {
        handler = new Handler();
    }

    /**
     * 开始启动时间
     */
    public void startTime() {
        startTime(3600,null);
    }

    public void startTime(long time) {
        this.time = time;
    }

    public void startTime(long time, TextView textView) {
        try {
            this.time = time;
            if (updateTimeRunnable != null) {
                handler.removeCallbacks(updateTimeRunnable);
            }
            if (textView != null) {
                textView.setVisibility(View.VISIBLE);
            }
            updateTimeRunnable = new UpdateTimeRunnable(textView);
            handler.post(updateTimeRunnable);
            if (listener != null) {
                listener.onVideoCallStart();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止计时时间
     */
    public void stopTime() {
        if (updateTimeRunnable != null) {
            handler.removeCallbacks(updateTimeRunnable);
            handler.removeCallbacksAndMessages(null);
        }
        if (listener != null) {
            listener.onVideoCallEnd();
        }
        if (time >= 0) {
            time = 0;
        }
    }


    private class UpdateTimeRunnable implements Runnable {
        private TextView timeView;

        public UpdateTimeRunnable(TextView timeView) {
            this.timeView = timeView;
        }

        @Override
        public void run() {
            time--;
            if (time < 0){
                if (timeView != null)
                    timeView.setText("00:00:00");
                return;
            }
            if (timeView != null)
                timeView.setText(formatMiss(time));

            if (listener != null) {
                if (time >= 0)
                listener.onVideoCalling(time);
            }
            handler.postDelayed(this, 1000);
        }
    }


    public String formatMiss(long miss){
        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss =(miss % 3600) % 60 > 9?(miss % 3600) % 60 +"":"0"+(miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }

    OnVideoCallTimeListener listener;

    public void setOnVideoCallTimeListener(OnVideoCallTimeListener listener) {
        this.listener = listener;
    }

    public interface OnVideoCallTimeListener {
        void onVideoCallStart();

        void onVideoCalling(long time);

        void onVideoCallEnd();
    }
}
