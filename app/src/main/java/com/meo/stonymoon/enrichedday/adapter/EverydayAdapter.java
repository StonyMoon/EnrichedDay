package com.meo.stonymoon.enrichedday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ModelCache;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.model.EverydayModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_SLIDER;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_THREE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TITLE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TWO;


public class EverydayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EverydayModel> modelList;
    private Context mContext;

    private class SliderHolder extends RecyclerView.ViewHolder {
        public SliderLayout sliderLayout;

        public SliderHolder(View view) {
            super(view);//TODO siler未完成
            sliderLayout = (SliderLayout) view.findViewById(R.id.everyday_slider);

        }
    }

    private class TwoHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;

        public TwoHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.everyday_two_image);
            titleView = (TextView) view.findViewById(R.id.everyday_two_title);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleView;

        public ThreeHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.everyday_three_image);
            titleView = (TextView) view.findViewById(R.id.everyday_three_title);
        }

    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        public TextView titleView;

        public TitleHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.everyday_title_text);

        }

    }


    public EverydayAdapter(List<EverydayModel> list) {
        modelList = list;

    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        switch (viewType) {
            case TYPE_SLIDER:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_slider, parent, false);
                SliderHolder holder = new SliderHolder(view);
                return holder;
            case TYPE_TITLE:
                return new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_title, parent, false));
            case TYPE_TWO:
                return new TwoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_two, parent, false));
            case TYPE_THREE:
                return new ThreeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_three, parent, false));
            default:
                return new ThreeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bangumi, parent, false));


        }


//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                PixivBean.PictureBean b = pixivBeanList.get(position-1);
//                //Xrecycler刷新部分占了一个位置
//                Intent intent = new Intent(mContext, PhotoActivity.class);
//                intent.putExtra("imageUrl","http://kyoko.b0.upaiyun.com/pixiv-ranking/"+b.picUrl);
//                mContext.startActivity(intent);
//
//            }
//        });


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EverydayModel model = modelList.get(position);
        switch (model.type) {
            case TYPE_THREE:
                ThreeHolder threeHolder = (ThreeHolder) holder;
                Glide.with(mContext)
                        .load(model.url)
                        .into(threeHolder.imageView);
                threeHolder.titleView.setText(model.title);
                break;
            case TYPE_TWO:
                TwoHolder twoHolder = (TwoHolder) holder;
                Glide.with(mContext)
                        .load(model.url)
                        .into(twoHolder.imageView);
                twoHolder.titleView.setText(model.title);
                break;
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.titleView.setText(model.title);
                break;
            case TYPE_SLIDER:
                List<String> urls = new ArrayList<>();
                urls.add("http://moe321-10010265.file.myqcloud.com/pixiv_illust_day_rank/cd1096a40b72bf5f6d3f60224a512d95.jpg");
                urls.add("http://moe321-10010265.file.myqcloud.com/pixiv_illust_day_rank/8c3a2071de7d897f554a57585464b92b.jpg");
                SliderLayout sliderLayout = ((SliderHolder) holder).sliderLayout;
                for (String url : urls) {
                    TextSliderView customSliderView = new TextSliderView(mContext);
                    customSliderView
                            .image(url)
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    sliderLayout.addSlider(customSliderView);
                }
                sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(2000);
                //sliderLayout.setCustomIndicator(indicator);
                break;

        }


    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }


    @Override
    public int getItemViewType(int position) {
        int type = modelList.get(position).type;
        return type;

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //网格设置为6格
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_TITLE:
                            return 6;
                        case TYPE_TWO:
                            return 3;
                        case TYPE_THREE:
                            return 2;
                        case TYPE_SLIDER:
                            return 6;
                        default:
                            return 6;
                    }
                }
            });
        }

    }
}
