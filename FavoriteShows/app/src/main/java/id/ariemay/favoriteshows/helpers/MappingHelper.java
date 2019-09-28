package id.ariemay.favoriteshows.helpers;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import id.ariemay.favoriteshows.databases.DBContract;
import id.ariemay.favoriteshows.models.Movies;


public class MappingHelper {

    public static ArrayList<Movies> mapCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movies> movieList = new ArrayList<>();
            while (movieCursor.moveToFirst()) {
                int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.ID_MOVIE));
                String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.TITLE_MOVIE));
                String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.OVERVIEW_MOVIE));
                int vote_average = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.VOTE_AVERAGE_MOVIE));
                String release_date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.RELEASE_DATE_MOVIE));
                String poster_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.POSTER_PATH_MOVIE));
                String backdrop_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DBContract.MovieColumns.BACKDROP_PATH_MOVIE));
                movieList.add(new Movies(id, title, vote_average, overview, release_date, poster_path, backdrop_path));
            }
            Log.d("CURSOR", "mapCursorToArrayList: " + movieCursor);
            movieCursor.close();
            return movieList;
    }
}