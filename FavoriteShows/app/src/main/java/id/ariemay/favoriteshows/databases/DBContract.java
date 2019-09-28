package id.ariemay.favoriteshows.databases;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DBContract {
    public static String AUTHORITY = "com.ariemay.entertainmentlistmade3";
    private static final String SCHEME_MOVIE = "content";
    public static String TABLE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static String ID_MOVIE = "id_movie";
        public static String TITLE_MOVIE = "title";
        public static String VOTE_AVERAGE_MOVIE = "vote_average";
        public static String OVERVIEW_MOVIE = "overview";
        public static String RELEASE_DATE_MOVIE = "release_date";
        public static String POSTER_PATH_MOVIE = "poster_path";
        public static String BACKDROP_PATH_MOVIE = "backdrop_path";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME_MOVIE)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }


    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
