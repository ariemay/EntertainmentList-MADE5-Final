package com.ariemay.entertainmentlistmade3.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ariemay.entertainmentlistmade3.databases.DBContract;

import org.json.JSONObject;

import static com.ariemay.entertainmentlistmade3.databases.DBContract.getColumnInt;
import static com.ariemay.entertainmentlistmade3.databases.DBContract.getColumnString;

public class Movies implements Parcelable {
    private int id;
    private String title;
    private int vote_average;
    private String overview;
    private String release_date;
    private String poster_path;
    private String backdrop_path;
    private JSONObject object;

    public Movies(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            int vote_average = object.getInt("vote_average");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            String poster_path = "https://image.tmdb.org/t/p/w185/" + object.getString("poster_path");
            String backdrop_path = "https://image.tmdb.org/t/p/w185/" + object.getString("backdrop_path");
            this.id = id;
            this.title = title;
            this.vote_average = vote_average;
            this.overview = overview;
            this.release_date = release_date;
            this.poster_path = poster_path;
            this.backdrop_path = backdrop_path;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() { return backdrop_path; }

    public void setBackdrop_path(String backdrop_path) { this.backdrop_path = backdrop_path; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.vote_average);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
    }

    public Movies() {
    }

    public Movies(int id, String title, int vote_average, String overview, String release_date, String poster_path, String backdrop_path) {
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public Movies(Cursor cursor) {
        this.id = getColumnInt(cursor, DBContract.MovieColumns.ID_MOVIE);
        this.title = getColumnString(cursor, DBContract.MovieColumns.TITLE_MOVIE);
        this.overview = getColumnString(cursor, DBContract.MovieColumns.OVERVIEW_MOVIE);
        this.release_date = getColumnString(cursor, DBContract.MovieColumns.RELEASE_DATE_MOVIE);
        this.vote_average = getColumnInt(cursor, DBContract.MovieColumns.VOTE_AVERAGE_MOVIE);
        this.poster_path = getColumnString(cursor, DBContract.MovieColumns.POSTER_PATH_MOVIE);
        this.backdrop_path = getColumnString(cursor, DBContract.MovieColumns.BACKDROP_PATH_MOVIE);
    }

    protected Movies(Parcel in) {
        this.title = in.readString();
        this.vote_average = in.readInt();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}