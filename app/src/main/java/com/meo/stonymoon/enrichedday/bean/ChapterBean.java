package com.meo.stonymoon.enrichedday.bean;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ChapterBean {
    public ChapterData data;


    public class ChapterData {
        public ChapterReturnData returnData;

    }

    public class ChapterReturnData {
        @SerializedName("image_list")
        public List<ChapterImage> imageList = new ArrayList<>();

    }

    public class ChapterImage {
        @SerializedName("location")
        public String url;

    }
}
