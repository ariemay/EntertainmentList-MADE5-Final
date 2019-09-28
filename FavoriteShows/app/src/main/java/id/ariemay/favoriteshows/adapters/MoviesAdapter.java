package id.ariemay.favoriteshows.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ariemay.favoriteshows.MainActivity;
import id.ariemay.favoriteshows.MovieDetails;
import id.ariemay.favoriteshows.R;
import id.ariemay.favoriteshows.models.Movies;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Cursor cursor;
    private Context context;
    private ArrayList<Movies> movies = new ArrayList<>();

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movies> items) {
        movies.clear();
        movies.addAll(items);
        Log.i("SETMOVIES", movies.toString());
        notifyDataSetChanged();
    }

    public void addMovies(final Movies item) {
        movies.add(item);
        notifyDataSetChanged();
    }

    public void setListMovies(Cursor cursor) {
        this.cursor = cursor;
    }

    public void clearMovies() {
        movies.clear();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_items, viewGroup, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder viewHolder, final int i) {
        final Movies movie = getItem(i);
        Glide.with(context)
                .load(movie.getPoster_path())
                .apply(new RequestOptions().override(150, 150))
                .into(viewHolder.poster);

        viewHolder.date.setText(movie.getRelease_date());
        viewHolder.name.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private Movies getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movies(cursor);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView date;
        private ImageView poster;

        MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            poster = itemView.findViewById(R.id.movie_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Movies movie = getItem(position);
                    Intent intent = new Intent(context, MovieDetails.class);
                    intent.putExtra(MainActivity.EXTRA_DATA, movie);
                    context.startActivity(intent);
                    Log.d("CLICK", "onClick: " + position);
                    Log.d("CLICK", "movies: " + movie.toString());
                }
            });
        }
    }
}

