package com.ariemay.entertainmentlistmade3;


import android.database.Cursor;


public interface LoadMovieCallback {
    void postExecute(Cursor movies);
}