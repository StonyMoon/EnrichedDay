package com.meo.stonymoon.enrichedday.util;

import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BangumiDetailBean;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.bean.ChapterBean;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.bean.ComicDetailBean;
import com.meo.stonymoon.enrichedday.bean.PixivBean;
import com.google.gson.Gson;
import com.meo.stonymoon.enrichedday.bean.SliderBean;


public class HandleResponseUtil {
    public static PixivBean handlePixivResponse(String response) {

        Gson gson = new Gson();
        PixivBean pixivBean = null;
        response = "{pictureBean:" + response + "}";
        try {
            pixivBean = gson.fromJson(response, PixivBean.class);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return pixivBean;

    }


    public static BangumiBean handleBangumiRecommendResponse(String response) {

        Gson gson = new Gson();
        BangumiBean bean = null;
        try {
            bean = gson.fromJson(response, BangumiBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public static ComicBean handleComicBeanRecommendResponse(String response) {

        Gson gson = new Gson();
        ComicBean bean = null;
        try {
            bean = gson.fromJson(response, ComicBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }


    public static BangumiDetailBean handleBangumiDetailResponse(String response) {
        Gson gson = new Gson();
        BangumiDetailBean bean = null;
        response = response.substring(19, response.length() - 2);

        try {
            bean = gson.fromJson(response, BangumiDetailBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public static ComicDetailBean handleComicDetailResponse(String response) {
        Gson gson = new Gson();
        ComicDetailBean bean = null;

        try {
            bean = gson.fromJson(response, ComicDetailBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public static ChapterBean.ChapterReturnData handleChapterResponse(String response) {
        Gson gson = new Gson();
        ChapterBean bean = null;

        try {
            bean = gson.fromJson(response, ChapterBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean.data.returnData;

    }

    public static BookBean handleBookResponse(String response) {
        Gson gson = new Gson();
        BookBean bean = null;

        try {
            bean = gson.fromJson(response, BookBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;

    }

    public static SliderBean handleSliderResponse(String response) {
        Gson gson = new Gson();
        SliderBean bean = null;
        response = response.substring(response.indexOf('{'), response.length() - 1);
        try {
            bean = gson.fromJson(response, SliderBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }



}
