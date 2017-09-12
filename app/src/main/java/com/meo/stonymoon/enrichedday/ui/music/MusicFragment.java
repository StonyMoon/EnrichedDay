package com.meo.stonymoon.enrichedday.ui.music;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.meo.stonymoon.enrichedday.MainActivity;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.BangumiDetailBean;
import com.meo.stonymoon.enrichedday.bean.ComicDetailBean;
import com.meo.stonymoon.enrichedday.ui.discovery.child.BangumiDetailActivity;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.meo.stonymoon.enrichedday.util.ShakeListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MusicFragment extends Fragment {
    private ShakeListener mShakeListener = null;
    private boolean isFirstStart = true;
    private ImageView coverImage;
    private TextView titleText;
    private LinearLayout randomLayout;
    private ImageView loadingTopImage;
    private ImageView loadingBottomImage;
    private ImageView bg;


    public MusicFragment() {

    }

    /*
     * 拿到传感器
     *
     */
    @Override
    public void onStart() {
        super.onStart();
        if (isFirstStart || ((MainActivity) getContext()).getSelectPage() != 0) {
            stopShakeListener();
            isFirstStart = false;

        } else {
            startShakeListener();
        }
    }

    /*
     * 注销传感器
     */
    @Override
    public void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        stopShakeListener();
        super.onPause();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        bg = (ImageView) view.findViewById(R.id.music_bg_view);
        coverImage = (ImageView) view.findViewById(R.id.recommend_cover_image);
        titleText = (TextView) view.findViewById(R.id.recommend_title);
        randomLayout = (LinearLayout) view.findViewById(R.id.music_random_layout);
        loadingTopImage = (ImageView) view.findViewById(R.id.music_loading_top_image);
        loadingBottomImage = (ImageView) view.findViewById(R.id.music_loading_bottom_image);
        mShakeListener = new ShakeListener(getContext());
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                stopShakeListener();//防止再次响应
                startLoading();
            }

        });





        return view;
    }

    public void startShakeListener() {
        if (mShakeListener != null)
            mShakeListener.start();

    }

    public void stopShakeListener() {
        if (mShakeListener != null) {
            mShakeListener.stop();
        }

    }

    private void getRandomBangumi() {


        final int seed = (int) (System.currentTimeMillis() % 6000);
        HttpUtil.sendOkHttpRequest("http://bangumi.bilibili.com/jsonp/seasoninfo/" + seed + ".ver?callback=seasonListCallback&jsonp=jsonp&_=1499651889555", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BangumiDetailBean bean = HandleResponseUtil.handleBangumiDetailResponse(response.body().string());
                if (bean.result == null) {
                    getRandomBangumi();
                    return;
                }
                Intent intent = new Intent(getContext(), BangumiDetailActivity.class);
                intent.putExtra("bangumiId", "" + seed);
                startActivity(intent);
            }
        });


    }

    //漫画质量太差简直用不了
    private void getRandomComic() {
        int seed = 90000 + (int) (System.currentTimeMillis() % 10000);
        HttpUtil.sendOkHttpRequest("http://app.u17.com/v3/appV3_3/android/phone/comic/detail_static_new?v=3320100&comicid=" + seed + "&model=Lenovo+P1c72&come_from=Tg002&android_id=4558b957bedb8f36", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final ComicDetailBean bean = HandleResponseUtil.handleComicDetailResponse(response.body().string());
                if (bean.data.returnData == null) {
                    getRandomComic();
                    return;
                }
                setLayout(bean.data.returnData.cover, bean.data.returnData.name);

            }
        });


    }


    private void getRandomReccomend() {
        long seed = System.currentTimeMillis();
        //int type = (int)(seed % 1);//TODO
        int type = 0;
        switch (type) {
            case 0:
                getRandomBangumi();
                break;
            case 1:
                getRandomComic();
                break;

        }

    }

    private void setLayout(final String url, final String title) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                randomLayout.setVisibility(View.VISIBLE);
                titleText.setText(title);
                Picasso.with(getContext()).load(url).into(coverImage);
                //重新监听摇动
                startShakeListener();
            }
        });

    }

    private void startLoading() {
        Glide.with(this.getContext()).load(R.drawable.music_bg)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(180, 180)
                .into(bg);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                loadingTopImage,
                "translationY",
                -300);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                loadingBottomImage,
                "translationY",
                300);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(
                loadingTopImage,
                "translationY",
                0);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(
                loadingBottomImage,
                "translationY",
                0);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);

        set.play(animator1).with(animator2)
                .before(animator3).before(animator4);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                getRandomBangumi();
            }
        });
        set.start();

    }


}
