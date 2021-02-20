package com.ft.base.binding.viewadapter.smartrefresh;


import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ft.base.binding.command.BindingCommand;
import com.ft.base.smartrefresh.SmartRefreshLayout;
import com.ft.base.smartrefresh.api.RefreshLayout;
import com.ft.base.smartrefresh.listener.OnLoadMoreListener;
import com.ft.base.smartrefresh.listener.OnRefreshListener;

public class ViewAdapter {
    //下拉刷新命令
    @BindingAdapter({"onRefreshCommand"})
    public static void onRefreshCommand(SmartRefreshLayout smartRefreshLayout, final BindingCommand onRefreshCommand) {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (onRefreshCommand != null) {
                    onRefreshCommand.execute();
                }
            }
        });
    }

    //是否刷新中
    @BindingAdapter({"onLoadMoreCommand"})
    public static void setRefreshing(SmartRefreshLayout smartRefreshLayout, final BindingCommand onLoadMoreCommand) {
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (onLoadMoreCommand != null) {
                    onLoadMoreCommand.execute();
                }
            }
        });
    }

    //是否刷新中
    @BindingAdapter({"srlEnableLoadMore"})
    public static void setEnableLoadMore(SmartRefreshLayout smartRefreshLayout, final boolean hasLoadMore) {
        smartRefreshLayout.setEnableLoadMore(hasLoadMore);
    }

}
