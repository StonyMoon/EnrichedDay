package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;


public class BangumiDetailBean {
    public BangumiDetailResult result;


    public class BangumiDetailResult {
        @SerializedName("bangumi_id")
        public int id;
        public String title;
        public String evaluate;
        public int favorites;
        public String staff;
        public String cover;


    }

}
