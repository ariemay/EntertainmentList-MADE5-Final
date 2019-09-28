package com.ariemay.entertainmentlistmade3.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ariemay.entertainmentlistmade3.databases.ShowHelper;
import com.ariemay.entertainmentlistmade3.fragments.MoviesFragment;

import java.util.Objects;

import static com.ariemay.entertainmentlistmade3.databases.DBContract.AUTHORITY;
import static com.ariemay.entertainmentlistmade3.databases.DBContract.CONTENT_URI_MOVIE;
import static com.ariemay.entertainmentlistmade3.databases.DBContract.TABLE_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private ShowHelper showHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
    }


    @Override
    public boolean onCreate() {
        showHelper = ShowHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        showHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = showHelper.queryProviderMovie();
                Log.d("URI_MATCHER_1", uri.toString());
                break;
            case MOVIE_ID:
                cursor = showHelper.queryByIdProviderMovie(uri.getLastPathSegment());
                Log.d("URI_MATCHER_2", uri.toString());
                break;
            default:
                cursor = null;
                Log.d("URI_MATCHER_2", uri.toString());
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        showHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = showHelper.insertProviderMovie(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MoviesFragment.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI_MOVIE + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        showHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = showHelper.deleteProviderMovie(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MoviesFragment.DataObserver(new Handler(), getContext()));

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        showHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            updated = showHelper.updateProviderMovie(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_MOVIE, new MoviesFragment.DataObserver(new Handler(), getContext()));

        return updated;
    }
}
