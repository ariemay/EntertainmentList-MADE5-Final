package com.ariemay.entertainmentlistmade3.services.Constants;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Data {
    public static final String API_KEY = "a70d71f9ad0d31149ab8518a4aa68b98";
    public static final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    public static final String MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
    public static final String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=";
    public static final String SEARCH_TV_URL = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY +"&language=en-US&query=";
    public static final String TV_URL = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
    public static final String NEW_MOVIES_TODAY = "https://api.themoviedb.org/3/discover/movie?api_key="
            + API_KEY + "&primary_release_date.gte=" + TODAY_DATE() + "&primary_release_date.lte=" + TODAY_DATE();

    private static String TODAY_DATE() {
        Date nowDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(nowDate);
    }
}

