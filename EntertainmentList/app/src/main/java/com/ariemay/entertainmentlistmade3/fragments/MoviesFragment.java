package com.ariemay.entertainmentlistmade3.fragments;


import android.app.ProgressDialog;
import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ariemay.entertainmentlistmade3.LoadMovieCallback;
import com.ariemay.entertainmentlistmade3.R;
import com.ariemay.entertainmentlistmade3.adapters.MoviesAdapter;
import com.ariemay.entertainmentlistmade3.models.Movies;
import android.arch.lifecycle.ViewModelProviders;

import com.ariemay.entertainmentlistmade3.services.viewmodels.MoviesViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.ariemay.entertainmentlistmade3.databases.DBContract.CONTENT_URI_MOVIE;
import static com.ariemay.entertainmentlistmade3.helpers.MappingHelper.mapCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements LoadMovieCallback {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private ArrayList<Movies> listMovies = new ArrayList<>();
    private MoviesAdapter adapter;
    private Context context;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));

        if (listMovies.size() <= 0) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }

        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, getMovies);
        moviesViewModel.setMovies();

        RecyclerView rvFragmentMovies = view.findViewById(R.id.movies_list);
        rvFragmentMovies.setHasFixedSize(true);
        rvFragmentMovies.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MoviesAdapter(view.getContext());
        adapter.setMovies(listMovies);
        rvFragmentMovies.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    if (newText.length() == 0) {
                        moviesViewModel.setMovies();
                    } else {
                        moviesViewModel.searchMovies(newText);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> moviesItem) {
            if (moviesItem != null) {
                listMovies = moviesItem;
                adapter.setMovies(moviesItem);
            }
            progressDialog.dismiss();
        }
    };

    @Override
    public void postExecute(Cursor movies) {
        Log.d("CURSOR", "postExecute: " + movies.toString());
        ArrayList<Movies> listMovie = mapCursorToArrayList(movies);
        if (listMovie.size() > 0) {
            adapter.setMovies(listMovie);
        } else {
            adapter.setMovies(new ArrayList<Movies>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    private static class getDataAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;


        private getDataAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI_MOVIE, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }

    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getDataAsync(context, (LoadMovieCallback) context).execute();

        }
    }
}
