package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


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
        public List<EpisodeBean> episodes = new ArrayList<>();

    }

    public class EpisodeBean {
        public String cover;
        @SerializedName("episode_id")
        public String episodeId;
        @SerializedName("index_title")
        public String title;
        public String page;



    }


}
