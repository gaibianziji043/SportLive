package com.ft.base.smartrefresh.impl;

import android.annotation.SuppressLint;
import android.view.View;

import com.ft.base.smartrefresh.api.RefreshHeader;
import com.ft.base.smartrefresh.internal.InternalAbstract;


/**
 * 刷新头部包装
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader/*, InvocationHandler*/ {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
