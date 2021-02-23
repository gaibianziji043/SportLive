package com.fentao.tech.sportlive.ui.list;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fentao.tech.sportlive.ui.adapter.VideoRecyclerViewAdapter;

/**
 * @ProjectName: SportLive
 * @Package: com.fentao.tech.sportlive.ui.list
 * @ClassName: RecyclerViewAutoPlayFragment
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/22 16:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 16:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RecyclerViewAutoPlayFragment extends RecyclerViewFragment{


    @Override
    protected void initView() {
        super.initView();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){ //滚动停止
                    autoPlayVideo(recyclerView);
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    private void autoPlayVideo(RecyclerView recyclerView) {
        if(recyclerView == null) return;

        int count = recyclerView.getChildCount();
        for(int i = 0;i<count;i++){
            View itemView = recyclerView.getChildAt(i);

            if(itemView == null) continue;

            VideoRecyclerViewAdapter.VideoHolder holder = (VideoRecyclerViewAdapter.VideoHolder) itemView.getTag();

            Rect rect  = new Rect();
            holder.mPlayerContainer.getLocalVisibleRect(rect);
            int height = holder.mPlayerContainer.getHeight();
            if(rect.top == 0 && rect.bottom == height){
                startPlay(holder.mPosition);
                break;
            }


        }



    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                startPlay(0);
            }
        });
    }
}
