package com.ft.base.binding.viewadapter.LinearLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import com.ft.base.net.R;

public final class ViewAdapter {
    @BindingAdapter("addView")
    public static void  addView(LinearLayout linearLayout,int num){
        if (num ==0) {
            return;
        }

       /* ImageView firIv = (ImageView) LayoutInflater.from().inflate(R.layout.layout_fire_iv, linearLayout);

        for (int i = 0; i < num; i++) {
            linearLayout.addView(firIv);
        }*/
    }
}
