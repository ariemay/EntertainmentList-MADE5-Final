package com.ariemay.entertainmentlistmade3.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ariemay.entertainmentlistmade3.R;
import com.ariemay.entertainmentlistmade3.databases.DBHelper;
import com.ariemay.entertainmentlistmade3.models.Movies;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.ariemay.entertainmentlistmade3.databases.DBContract.TABLE_MOVIE;

public class StackConf implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Movies> movieList = new ArrayList<>();
    private Context context;
    private int widgetId;

    StackConf(Context applicationContext, Intent intent) {
        context = applicationContext;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        movieList.clear();
        getFavoriteMovies(context);
    }

    private void getFavoriteMovies(Context context) {
        DBHelper databaseHelper = new DBHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movies movies = new Movies(cursor);
                movieList.add(movies);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Movies movies = movieList.get(position);
        String posterUrl = movies.getPoster_path();
        try {
            Bitmap preview = Glide.with(context)
                    .asBitmap()
                    .load(posterUrl)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.posterwidget, preview);
            remoteViews.setTextViewText(R.id.namewidget, movies.getTitle());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
