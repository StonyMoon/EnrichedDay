package com.meo.stonymoon.enrichedday.ui.discovery.child;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meo.stonymoon.enrichedday.MyFragmentPagerAdapter;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.bean.ChapterBean;
import com.meo.stonymoon.enrichedday.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.meo.stonymoon.enrichedday.util.HandleResponseUtil.handleChapterResponse;

public class ComicChapterActivity extends AppCompatActivity {
    List<String> imageUrlList = new ArrayList<>();
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_chapter);
        pager = (ViewPager) findViewById(R.id.chapter_viewpager);
        String id = getIntent().getStringExtra("chapterId");
        loadPicture(id);
    }

    private void loadPicture(String id) {
        HttpUtil.sendOkHttpRequest("http://app.u17.com/v3/appV3_3/android/phone/comic/chapterNew?v=3320100&chapter_id=" + id + "&model=Lenovo+P1c72&come_from=Tg002&android_id=4558b957bedb8f36", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ChapterBean.ChapterReturnData data = handleChapterResponse(response.body().string());
                int len = data.imageList.size();
                for (int i = 0; i < len; i++) {
                    imageUrlList.add(data.imageList.get(i).url);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Fragment> mFragmentList = new ArrayList<>();
                        for (int i = 0; i < imageUrlList.size(); i++) {
                            ChapterImageFragment fragment = new ChapterImageFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("url", imageUrlList.get(i));
                            fragment.setArguments(bundle);
                            mFragmentList.add(fragment);

                        }
                        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList);
                        pager.setAdapter(adapter);
                        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
                        pager.setOffscreenPageLimit(2);
                        //viewPager.addOnPageChangeListener(this);
                        //mBinding.include.ivTitleGank.setSelected(true);
                        pager.setCurrentItem(0);


                    }
                });

            }
        });


    }


}
