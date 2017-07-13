package com.meo.stonymoon.enrichedday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class BookBean {
    public List<Book> books = new ArrayList<>();


    public class Book {
        public BookRating rating;
        public String pubdate;
        public BookImage images;
        public String title;
        public String summary;
        @SerializedName("author_intro")
        public String authorIntro;

    }

    public class BookRating {
        public String average;
        public int numRaters;


    }

    public class BookImage {
        @SerializedName("large")
        public String imageUrl;

    }


}
