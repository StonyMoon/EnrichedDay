package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2017/7/10.
 */

public class PixivBean {

    public List<PictureBean> pictureBean = new ArrayList<>();


    public class PictureBean {
        @SerializedName("titleurl")
        public String pixivUrl;
        @SerializedName("authorurl")
        public String authorUrl;
        @SerializedName("imgname")
        public String picUrl;

    }
}
