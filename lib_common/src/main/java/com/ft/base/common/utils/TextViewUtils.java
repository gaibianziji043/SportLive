package com.ft.base.common.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class TextViewUtils extends AppCompatTextView {
    public TextViewUtils(Context context) {
        super(context);
    }

    public TextViewUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewUtils(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
