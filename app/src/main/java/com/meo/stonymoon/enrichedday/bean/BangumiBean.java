package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2017/7/11.
 */

public class BangumiBean {
    public Result result;

    public class Result {
        @SerializedName("list")
        public List<Bangumi> bangumiList = new ArrayList<>();
    }

    //    @SerializedName("list")
//    public List<Bangumi> bangumiList = new ArrayList<>();
    public class Bangumi {
        public String cover;
        public int favorites;
        public String title;
        public String url;

    }


}
