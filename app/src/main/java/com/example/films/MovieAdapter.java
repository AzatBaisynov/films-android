package com.example.films;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false); // Убедитесь, что название файла верно
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.title);
        holder.movieYear.setText(movie.releaseDate != null && !movie.releaseDate.isEmpty() ? movie.releaseDate.split("-")[0] : "Неизвестно");
        holder.movieRating.setText("Рейтинг: " + String.valueOf(movie.voteAverage));

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath;
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground) // Убедитесь, что у вас есть placeholder
                .into(holder.posterImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("film_id", movie.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle, movieYear, movieRating;
        ImageView posterImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
            movieRating = itemView.findViewById(R.id.movieRating);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }
    }
}
