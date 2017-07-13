package com.meo.stonymoon.enrichedday.model;


public class EverydayModel {
    public static final int TYPE_SLIDER = 0; //轮播图

    public static final int TYPE_TITLE = 1; //标题
    public static final int TYPE_TWO = 2; //两张图
    public static final int TYPE_THREE = 3; //三张图

    public int type;
    public String url;
    public String title;

    public EverydayModel(int type, String url, String title) {
        this.type = type;
        this.url = url;
        this.title = title;


    }


}
