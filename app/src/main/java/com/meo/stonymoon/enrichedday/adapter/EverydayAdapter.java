package com.meo.stonymoon.enrichedday.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.meo.stonymoon.enrichedday.MainActivity;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.meo.stonymoon.enrichedday.bean.SliderBean;
import com.meo.stonymoon.enrichedday.model.EverydayModel;
import com.meo.stonymoon.enrichedday.ui.PhotoActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.DiscoveryFragment;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiDetailActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BookDetailActivity;
import com.meo.stonymoon.enrichedday.ui.discovery.child.ComicDetailActivity;
import com.meo.stonymoon.enrichedday.util.DateUtil;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_SLIDER;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_THREE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TITLE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TWO;


public class EverydayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<EverydayModel> modelList;
    private Context mContext;

    private class SliderHolder extends RecyclerView.ViewHolder {
        private SliderLayout sliderLayout;
        private ImageButton pixiv;
        private ImageButton random;
        private ImageView comic;
        private TextView dayText;


        private SliderHolder(View view) {
            super(view);
            sliderLayout = (SliderLayout) view.findViewById(R.id.everyday_slider);
            pixiv = (ImageButton) view.findViewById(R.id.everyday_pixiv_button);
            random = (ImageButton) view.findViewById(R.id.everyday_random_button);
            comic = (ImageView) view.findViewById(R.id.everyday_comic_button);
            dayText = (TextView) view.findViewById(R.id.daily_text);


            HttpUtil.sendOkHttpRequest("http://api.bilibili.com/x/web-show/res/loc?callback=jQuery17205969745067413896_1482805801285&jsonp=jsonp&pf=0&id=23&_=1482805801599", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final SliderBean sliderBean = HandleResponseUtil.handleSliderResponse(response.body().string());
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (SliderBean.Data data : sliderBean.data) {
                                TextSliderView customSliderView = new TextSliderView(mContext);
                                customSliderView
                                        .image(data.pic)
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                sliderLayout.addSlider(customSliderView);
                            }
                            sliderLayout.setCustomAnimation(new DescriptionAnimation());
                            sliderLayout.setDuration(2000);
                        }
                    });
                }
            });


        }
    }

    private class TwoHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;

        private TwoHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.everyday_two_image);
            titleView = (TextView) view.findViewById(R.id.everyday_two_title);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;

        private ThreeHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.everyday_three_image);
            titleView = (TextView) view.findViewById(R.id.everyday_three_title);
        }

    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private LinearLayout titleLayout;

        private TitleHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.everyday_title_text);
            titleLayout = (LinearLayout) view.findViewById(R.id.everyday_title);
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
                SliderListener sliderListener = new SliderListener();
                sliderHolder.pixiv.setOnClickListener(sliderListener);
                sliderHolder.random.setOnClickListener(sliderListener);
                sliderHolder.comic.setOnClickListener(sliderListener);

                return sliderHolder;
            case TYPE_TITLE:
                final TitleHolder titleHolder = new TitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everyday_title, parent, false));
                titleHolder.titleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = modelList.get(titleHolder.getAdapterPosition()).title;
                        //只能调用mainActivity里的方法来实现翻页了= =
                        MainActivity mainActivity = ((MainActivity) mContext);
                        switch (title) {
                            case "推荐番剧":
                                mainActivity.selectDiscoveryFragmentPage(1);
                                break;
                            case "推荐漫画":
                                mainActivity.selectDiscoveryFragmentPage(2);
                                break;
                            case "推荐小说":
                                mainActivity.selectDiscoveryFragmentPage(3);
                                break;
                            case "P站日榜":
                                ((MainActivity) mContext).selectPage(2);
                                break;
                        }

                    }
                });

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




    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EverydayModel model = modelList.get(position);
        switch (model.type) {
            case TYPE_THREE:
                ThreeHolder threeHolder = (ThreeHolder) holder;
                Picasso.with(mContext)
                        .load(model.url)
                        .into(threeHolder.imageView);
                threeHolder.titleView.setText(model.title);
                break;
            case TYPE_TWO:
                TwoHolder twoHolder = (TwoHolder) holder;
                Picasso.with(mContext)
                        .load(model.url)
                        .into(twoHolder.imageView);
                twoHolder.titleView.setText(model.title);
                break;
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.titleView.setText(model.title);
                break;
            case TYPE_SLIDER:
                SliderHolder sliderHolder = (SliderHolder) holder;
                sliderHolder.dayText.setText(DateUtil.getDay());
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


    class SliderListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.everyday_random_button:
                    ((MainActivity) mContext).selectPage(0);
                    break;
                case R.id.everyday_pixiv_button:
                    ((MainActivity) mContext).selectPage(2);
                    break;
                case R.id.everyday_comic_button:
                    ((MainActivity) mContext).selectDiscoveryFragmentPage(2);


            }
        }
    }

    class TitleListener implements View.OnClickListener {
        String title;

        TitleListener(String title) {
            this.title = title;
        }

        @Override
        public void onClick(View v) {
            //拿到DiscoveryFragment实现翻页
            DiscoveryFragment discoveryFragment = (DiscoveryFragment) ((MainActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.discovery_fragment);
            switch (title) {
                case "推荐番剧":
                    discoveryFragment.selectPage(1);
                    break;
                case "推荐漫画":
                    discoveryFragment.selectPage(2);
                    break;
                case "推荐小说":
                    discoveryFragment.selectPage(3);
                    break;
                case "P站日榜":
                    ((MainActivity) mContext).selectPage(2);
                    break;
            }
        }



    }


}

