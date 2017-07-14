package com.meo.stonymoon.enrichedday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.meo.stonymoon.enrichedday.ui.PhotoActivity;

import java.util.List;

import static java.security.AccessController.getContext;


public class PixivAdapter extends RecyclerView.Adapter<PixivAdapter.ViewHolder> {

    private List<PixivBean.PictureBean> pixivBeanList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.pixiv_image);
        }

    }

    public PixivAdapter(List<PixivBean.PictureBean> s) {
        pixivBeanList = s;
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pivix_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PixivBean.PictureBean b = pixivBeanList.get(position - 1);
                //Xrecycler刷新部分占了一个位置
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra("imageUrl", "http://kyoko.b0.upaiyun.com/pixiv-ranking/" + b.picUrl);
                mContext.startActivity(intent);

            }
        });

        return holder;

    }

    @Override
    public int getItemCount() {
        return pixivBeanList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        PixivBean.PictureBean pictureBean = pixivBeanList.get(position);
//        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
//        float width = (float)wm.getDefaultDisplay().getWidth();
//        float itemWidth = ((width-4)/2);
//        float scale = (itemWidth)/


        Glide.with(mContext)
                .load("http://kyoko.b0.upaiyun.com/pixiv-ranking/" + pictureBean.picUrl)
                .into(holder.imageView);

    }


}
