package com.meo.stonymoon.enrichedday.ui.discovery.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.meo.stonymoon.enrichedday.MainActivity;
import com.meo.stonymoon.enrichedday.R;
import com.meo.stonymoon.enrichedday.adapter.EverydayAdapter;
import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.meo.stonymoon.enrichedday.model.EverydayModel;
import com.meo.stonymoon.enrichedday.ui.discovery.DiscoveryFragment;
import com.meo.stonymoon.enrichedday.util.HandleResponseUtil;
import com.meo.stonymoon.enrichedday.util.HttpUtil;
import com.meo.stonymoon.enrichedday.util.MyItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_SLIDER;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_THREE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TITLE;
import static com.meo.stonymoon.enrichedday.model.EverydayModel.TYPE_TWO;



public class RecommendFragment extends Fragment {
    /*
     * 让这个碎片持有一个引用
     */

    RecyclerView recyclerView;
    List<EverydayModel> modelList = new ArrayList<>();
    EverydayAdapter adapter = new EverydayAdapter(modelList);
    public RecommendFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.everyday_recyclerview);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 6);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new MyItemDecoration(2, 5));
        recyclerView.setAdapter(adapter);
        initData();

        return view;
    }

    /*
     * 这里思路是先用无关图片占位，然后加载完毕以后刷新占位即可
     * 如果更新界面需要重新更新数字
     *
     */
    private void initData() {
        modelList.add(new EverydayModel(TYPE_SLIDER, "", ""));
        modelList.add(new EverydayModel(TYPE_TITLE, "2", "推荐番剧"));
        for (int i = 0; i < 6; i++) {
            modelList.add(new EverydayModel(TYPE_THREE, "https://i.pximg.net/c/240x480/img-master/img/2017/07/13/00/04/52/63837665_p0_master1200.jpg", ""));
        }
        modelList.add(new EverydayModel(TYPE_TITLE, "3", "推荐漫画"));
        for (int i = 0; i < 2; i++) {
            modelList.add(new EverydayModel(TYPE_TWO, "https://i.pximg.net/c/240x480/img-master/img/2017/07/13/00/04/52/63837665_p0_master1200.jpg", ""));
        }

        modelList.add(new EverydayModel(TYPE_TITLE, "4", "推荐小说"));
        for (int i = 0; i < 6; i++) {
            modelList.add(new EverydayModel(TYPE_THREE, "https://i.pximg.net/c/240x480/img-master/img/2017/07/13/00/04/52/63837665_p0_master1200.jpg", ""));
        }
        modelList.add(new EverydayModel(TYPE_TITLE, "5", "P站日榜"));
        for (int i = 0; i < 6; i++) {
            modelList.add(new EverydayModel(TYPE_THREE, "https://i.pximg.net/c/240x480/img-master/img/2017/07/13/00/04/52/63837665_p0_master1200.jpg", ""));
        }



        loadBangumi();
        loadComic();
        loadBook();

        loadPixiv();
        adapter.notifyDataSetChanged();

    }

    private void loadBangumi() {

        HttpUtil.sendOkHttpRequest("https://bangumi.bilibili.com/web_api/season/index_global?page=1&page_size=20&version=0&is_finish=0&start_year=0&tag_id=&index_type=1&index_sort=0&quarter=0", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonBody = response.body().string();
                BangumiBean bangumiBean = HandleResponseUtil.handleBangumiRecommendResponse(jsonBody);
                for (int i = 0; i < 6; i++) {
                    BangumiBean.Bangumi bangumi = bangumiBean.result.bangumiList.get(i);
                    EverydayModel bangumiModel = new EverydayModel(TYPE_THREE, bangumi.cover, bangumi.title, "bangumi", bangumi);
                    bangumiModel.detailMap.put("bangumiId", bangumi.url.split("/")[4]);
                    modelList.set(2 + i, bangumiModel);
                }
                notifyDataChanged();

            }
        });
    }


    private void loadComic() {
        HttpUtil.sendOkHttpRequest("http://app.u17.com/v3/appV3_3/android/phone/list/commonComicList?argValue=9&argName=topic&argCon=0&page=1&v=3320100&model=Lenovo+P1c72&come_from=Tg002&android_id=4558b957bedb8f36", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonBody = response.body().string();
                ComicBean comicBean = HandleResponseUtil.handleComicBeanRecommendResponse(jsonBody);

                for (int i = 0; i < 2; i++) {
                    ComicBean.Comic comic = comicBean.data.returnData.comics.get(i);
                    modelList.set(9 + i, new EverydayModel(TYPE_TWO, comic.cover, comic.name, "comic", comic));
                }
                notifyDataChanged();

            }
        });


    }

    private void loadBook() {
        HttpUtil.sendOkHttpRequest("https://api.douban.com/v2/book/search?tag=轻小说&count=20&start=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonBody = response.body().string();
                BookBean book = HandleResponseUtil.handleBookResponse(jsonBody);
                for (int i = 0; i < 6; i++) {
                    BookBean.Book b = book.books.get(i);
                    //12为上面的行数总和
                    modelList.set(12 + i, new EverydayModel(TYPE_THREE, b.images.imageUrl, b.title, "book", b));
                }
                notifyDataChanged();


            }
        });


    }

    private void loadPixiv() {

        HttpUtil.sendOkHttpRequest("http://www.moe123.net/module/pixiv", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String jsonBody = response.body().string();
                PixivBean pb = HandleResponseUtil.handlePixivResponse(jsonBody);
                for (int i = 0; i < 6; i++) {
                    PixivBean.PictureBean b = pb.pictureBean.get(i);
                    //19为上面的行数总和
                    modelList.set(19 + i, new EverydayModel(TYPE_THREE, "http://kyoko.b0.upaiyun.com/pixiv-ranking/" + b.picUrl, "", "pixiv", b));
                }
                notifyDataChanged();
            }
        });
    }

    private void notifyDataChanged() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });


    }


}
