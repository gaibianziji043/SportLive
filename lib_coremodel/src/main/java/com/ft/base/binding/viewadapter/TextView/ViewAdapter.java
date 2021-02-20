package com.ft.base.binding.viewadapter.TextView;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.ft.base.net.R;

public class ViewAdapter {
    @BindingAdapter("textStyleSelect")
    public static void textStyleSelect(TextView textView, int textSelect) {
        //1 :加粗
        if (textSelect == 1) {
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    @BindingAdapter("select")
    public static void setSelect(TextView textView, boolean isSelect) {
        textView.setSelected(isSelect);
    }

    @BindingAdapter("topicDrawableSelector")
    public static void setTopicDrawableSelector(TextView textView, boolean isSelect) {
        //textView.setBackground(textView.getContext().getResources().getDrawable(R.drawable.drawable_topic_left_bg_selector));
        if (isSelect) {
            textView.setBackgroundColor(textView.getContext().getResources().getColor(R.color.white));
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.color_f67133));
        } else {
            textView.setBackgroundColor(textView.getContext().getResources().getColor(R.color.color_f7f7f7));
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.color_666666));
        }
    }
}
