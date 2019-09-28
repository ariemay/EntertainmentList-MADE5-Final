package id.ariemay.favoriteshows;

import android.database.Cursor;

interface LoadMovieCallback {
    void postExecute(Cursor movies);
}