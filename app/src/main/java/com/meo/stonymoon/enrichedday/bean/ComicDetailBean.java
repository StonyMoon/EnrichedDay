package com.meo.stonymoon.enrichedday.bean;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ComicDetailBean {
    public comicDetailData data;


    public class comicDetailData {
        public ReturnComicDetailData returnData;

    }

    public class ReturnComicDetailData {
        @SerializedName("chapter_list")
        public List<ComicChapter> comicChapterList = new ArrayList<>();

    }

    public class ComicChapter {
        public String name;
        @SerializedName("chapter_id")
        public String chapterId;

    }

}
