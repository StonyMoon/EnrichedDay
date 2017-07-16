package com.meo.stonymoon.enrichedday.model;


import com.meo.stonymoon.enrichedday.bean.BangumiBean;
import com.meo.stonymoon.enrichedday.bean.BookBean;
import com.meo.stonymoon.enrichedday.bean.ComicBean;
import com.meo.stonymoon.enrichedday.bean.PixivBean;

import java.util.HashMap;

public class EverydayModel {
    public static final int TYPE_SLIDER = 0; //轮播图

    public static final int TYPE_TITLE = 1; //标题
    public static final int TYPE_TWO = 2; //两张图
    public static final int TYPE_THREE = 3; //三张图

    public int type;
    public String url;
    public String title;
    public HashMap<String, String> detailMap = new HashMap<>();

    private String detailType;//用来区分同样的布局不同的
    private BangumiBean.Bangumi bangumi;
    private BookBean.Book book;
    private PixivBean.PictureBean pixiv;
    private ComicBean.Comic comic;

    /*
     *占位用的model
    */
    public EverydayModel(int type, String url, String title) {
        this.type = type;
        this.url = url;
        this.title = title;
    }

    public EverydayModel(int type, String url, String title, String detailType, BangumiBean.Bangumi bangumi) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.detailType = detailType;
        this.bangumi = bangumi;

    }

    public EverydayModel(int type, String url, String title, String detailType, ComicBean.Comic comic) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.detailType = detailType;
        this.comic = comic;

    }

    public EverydayModel(int type, String url, String title, String detailType, BookBean.Book book) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.detailType = detailType;
        this.book = book;

    }

    public EverydayModel(int type, String url, String title, String detailType, PixivBean.PictureBean pixiv) {
        this.type = type;
        this.url = url;
        this.title = title;
        this.detailType = detailType;
        this.pixiv = pixiv;
    }

    public BangumiBean.Bangumi getBangumi() {
        return bangumi;
    }

    public ComicBean.Comic getComic() {
        return comic;
    }

    public BookBean.Book getBook() {
        return book;
    }

    public PixivBean.PictureBean getPixiv() {
        return pixiv;
    }

    public String getDetailType() {
        if (detailType == null)
            return "null";

        return detailType;
    }









}
