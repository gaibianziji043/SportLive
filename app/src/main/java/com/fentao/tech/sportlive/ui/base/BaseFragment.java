package com.fentao.tech.sportlive.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dueeeke.videoplayer.player.VideoViewManager;

/**
 * @ProjectName: SportLive
 * @Package: com.fentao.tech.sportlive.ui.base
 * @ClassName: BaseFragment
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/22 11:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 11:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private boolean mIsInitData;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(getLayoutResId(),container,false);
            initView();
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isLazyLoad()){
            fetchData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    private void fetchData() {
        if(mIsInitData){
            return;
        }
        initData();
        mIsInitData = true;
    }

    public <T extends View> T findViewById(@IdRes int id){
        return mRootView.findViewById(id);
    }


    /**
     * @method
     * @description 子类实现
     * @date: 2021/2/22 11:22
     * @author: allen
     * @param
     * @return
     */
    protected void initData() {

    }

    protected boolean isLazyLoad() {
        return false;
    }

    /**
     * @param
     * @return
     * @method
     * @description 抽象方法由子类实现
     * @date: 2021/2/22 11:18
     * @author: allen
     */
    protected void initView() {

    }

    protected abstract int getLayoutResId();


    /**
     * 子类可通过此方法直接拿到VideoViewManager
     */
    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }

}
