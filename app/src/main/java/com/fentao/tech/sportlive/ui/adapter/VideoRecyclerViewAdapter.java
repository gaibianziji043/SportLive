package com.fentao.tech.sportlive.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.component.PrepareView;
import com.fentao.tech.sportlive.R;
import com.fentao.tech.sportlive.bean.VideoBean;
import com.fentao.tech.sportlive.ui.adapter.listener.OnItemChildClickListener;
import com.fentao.tech.sportlive.ui.adapter.listener.OnItemClickListener;

import java.util.List;

/**
 * @ProjectName: SportLive
 * @Package: com.fentao.tech.sportlive.ui.adapter
 * @ClassName: VideoRecyclerViewAdapter
 * @Description: java类作用描述
 * @Author: allen
 * @CreateDate: 2021/2/22 13:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/22 13:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder> {

    private List<VideoBean> videos;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;

    public VideoRecyclerViewAdapter(List<VideoBean> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);

        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        VideoBean videoBean = videos.get(position);

        Glide.with(holder.mThumb.getContext())
                .load(videoBean.getThumb())
                .placeholder(android.R.color.darker_gray)
                .into(holder.mThumb);

        holder.mTitle.setText(videoBean.getTitle());

        holder.mPosition = position;

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addData(List<VideoBean> videoList) {
        int size = videos.size();
        videos.addAll(videoList);
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        notifyItemRangeChanged(size, videos.size());
    }

    public class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public int mPosition;
        public FrameLayout mPlayerContainer;
        public TextView mTitle;
        public ImageView mThumb;
        public PrepareView mPrepareView;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            mPlayerContainer = itemView.findViewById(R.id.player_container);
            mTitle = itemView.findViewById(R.id.tv_title);
            mPrepareView = itemView.findViewById(R.id.prepare_view);
            mThumb = mPrepareView.findViewById(R.id.thumb);

            if(mOnItemChildClickListener != null){
                mPlayerContainer.setOnClickListener(this);
            }

            if(mOnItemClickListener != null){
                itemView.setOnClickListener(this);
            }
            //通过tag将ViewHolder和itemView绑定
            itemView.setTag(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.player_container){
                if(mOnItemChildClickListener != null){
                    mOnItemChildClickListener.onItemChildClick(mPosition);
                }
            }else{
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(mPosition);
                }
            }

        }
    }


    public void setmOnItemChildClickListener(OnItemChildClickListener mOnItemChildClickListener) {
        this.mOnItemChildClickListener = mOnItemChildClickListener;
    }

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
