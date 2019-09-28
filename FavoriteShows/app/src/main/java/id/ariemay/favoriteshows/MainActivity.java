package id.ariemay.favoriteshows;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import id.ariemay.favoriteshows.adapters.MoviesAdapter;
import id.ariemay.favoriteshows.models.Movies;

import static id.ariemay.favoriteshows.databases.DBContract.MovieColumns.CONTENT_URI;
import static id.ariemay.favoriteshows.helpers.MappingHelper.mapCursorToArrayList;

public class MainActivity extends AppCompatActivity implements LoadMovieCallback {

    public static final String EXTRA_DATA = "extras";
    private MoviesAdapter adapter;
    private RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Favorite Shows");

        recyclerView = findViewById(R.id.movies_list);
        adapter = new MoviesAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        new getData().execute();
    }

    @Override
    public void postExecute(Cursor movies) {
        ArrayList<Movies> listMovies = mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            adapter.setMovies(listMovies);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            adapter.setMovies(new ArrayList<Movies>());
        }
    }

    private class getData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d("TEST", "doInBackground: *******");
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            Log.d("TEST", "onPostExecute: ********");
            adapter.setListMovies(movies);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }
}

