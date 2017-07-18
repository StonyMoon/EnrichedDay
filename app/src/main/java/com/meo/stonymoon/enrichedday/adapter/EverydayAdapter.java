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
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.meo.stonymoon.enrichedday.model.EverydayModel;
import com.meo.stonymoon.enrichedday.ui.PhotoActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiDetailActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BookDetailActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.ComicDetailActivity;

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
            List<String> urls = new ArrayList<>();
            urls.add("http://moe321-10010265.file.myqcloud.com/pixiv_illust_day_rank/cd1096a40b72bf5f6d3f60224a512d95.jpg");
            urls.add("http://moe321-10010265.file.myqcloud.com/pixiv_illust_day_rank/8c3a2071de7d897f554a57585464b92b.jpg");
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
                SliderHolder sliderHolder = new SliderHolder(view);
                return sliderHolder;
            case TYPE_TITLE:
                TitleHolder titleHolder = new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_title, parent, false));

                return titleHolder;
            case TYPE_TWO:
                final TwoHolder twoHolder = new TwoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_two, parent, false));
                twoHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = twoHolder.getAdapterPosition();
                        EverydayModel model = modelList.get(position);
                        if (model.getDetailType().equals("comic")) {
                            ComicBean.Comic c = model.getComic();
                            //Xrecycler刷新部分占了一个位置
                            Intent intent = new Intent(mContext, ComicDetailActivity.class);
                            intent.putExtra("title", c.name);
                            intent.putExtra("evaluate", c.description);
                            intent.putExtra("coverUrl", c.cover);
                            intent.putExtra("id", c.id);
                            mContext.startActivity(intent);
                        }
                    }
                });
                return twoHolder;
            case TYPE_THREE:
                final ThreeHolder threeHolder = new ThreeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_three, parent, false));
                threeHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = threeHolder.getAdapterPosition();
                        EverydayModel model = modelList.get(position);
                        if (model.getDetailType().equals("bangumi")) {
                            BangumiBean.Bangumi b = model.getBangumi();
                            Intent intent = new Intent(mContext, BangumiDetailActivity.class);
                            String id = b.url.split("/")[4];
                            intent.putExtra("bangumiId", id);
                            mContext.startActivity(intent);
                        } else if (model.getDetailType().equals("book")) {
                            BookBean.Book b = model.getBook();
                            //Xrecycler刷新部分占了一个位置
                            Intent intent = new Intent(mContext, BookDetailActivity.class);
                            intent.putExtra("imageUrl", b.images.imageUrl);
                            intent.putExtra("title", b.title);
                            intent.putExtra("average", b.rating.average);
                            intent.putExtra("numRaters", b.rating.numRaters);
                            intent.putExtra("pubdate", b.pubdate);
                            intent.putExtra("authorIntro", b.authorIntro);
                            intent.putExtra("summary", b.summary);
                            mContext.startActivity(intent);

                        } else if (model.getDetailType().equals("pixiv")) {
                            PixivBean.PictureBean b = model.getPixiv();
                            Intent intent = new Intent(mContext, PhotoActivity.class);
                            intent.putExtra("imageUrl", "http://kyoko.b0.upaiyun.com/pixiv-ranking/" + b.picUrl);
                            intent.putExtra("pixivUrl", b.pixivUrl);
                            mContext.startActivity(intent);
                        }

                    }
                });

                return threeHolder;

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

    /*
        设置布局管理器根据不同的类型行数来确定行数


     */
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


    class BookListener implements View.OnClickListener { //TODO
        ThreeHolder threeHolder;

        BookListener(ThreeHolder threeHolder) {
            this.threeHolder = threeHolder;
        }

        @Override
        public void onClick(View v) {
            int postion = threeHolder.getAdapterPosition();
            HashMap<String, String> detailMap = modelList.get(postion).detailMap;
            Intent intent = new Intent(mContext, BangumiDetailActivity.class);
            intent.putExtra("bangumiId", detailMap.get("bangumiId"));
            mContext.startActivity(intent);
        }
    }


    class PixivListener implements View.OnClickListener { //todo
        ThreeHolder threeHolder;

        PixivListener(ThreeHolder threeHolder) {
            this.threeHolder = threeHolder;
        }

        @Override
        public void onClick(View v) {
            int postion = threeHolder.getAdapterPosition();
            HashMap<String, String> detailMap = modelList.get(postion).detailMap;
            Intent intent = new Intent(mContext, BangumiDetailActivity.class);
            intent.putExtra("bangumiId", detailMap.get("bangumiId"));
            mContext.startActivity(intent);
        }
    }


    class ComicListener implements View.OnClickListener {
        RecyclerView.ViewHolder holder;

        ComicListener(RecyclerView.ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int postion = holder.getAdapterPosition();
            HashMap<String, String> detailMap = modelList.get(postion).detailMap;
            Intent intent = new Intent(mContext, ComicDetailActivity.class);
            intent.putExtra("title", detailMap.get("title"));
            intent.putExtra("evaluate", detailMap.get("evaluate"));
            intent.putExtra("coverUrl", detailMap.get("coverUrl"));
            intent.putExtra("id", detailMap.get("id"));
            mContext.startActivity(intent);

        }
    }

    class TitleListener implements View.OnClickListener { //TODO
        ThreeHolder threeHolder;

        TitleListener(ThreeHolder threeHolder) {
            this.threeHolder = threeHolder;
        }

        @Override
        public void onClick(View v) {
            int postion = threeHolder.getAdapterPosition();
            HashMap<String, String> detailMap = modelList.get(postion).detailMap;
            Intent intent = new Intent(mContext, BangumiDetailActivity.class);
            intent.putExtra("bangumiId", detailMap.get("bangumiId"));
            mContext.startActivity(intent);
        }
    }



}

