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

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private Context context;
    private List<TVShow> tvShows;

    public TVShowAdapter(Context context, List<TVShow> tvShows) {
        this.context = context;
        this.tvShows = tvShows;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tv_show_item, parent, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        TVShow tvShow = tvShows.get(position);
        holder.tvShowName.setText(tvShow.name);
        holder.tvShowYear.setText(tvShow.first_air_date.split("-")[0]);
        holder.tvShowRating.setText(String.valueOf(tvShow.vote_average));

        String imageUrl = "https://image.tmdb.org/t/p/w500" + tvShow.poster_path;
        Glide.with(context).load(imageUrl).into(holder.posterImageView);

        holder.itemView.setOnClickListener(v -> {
            // Передаём film_id в MovieDetailsActivity
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("film_id", tvShow.id); // Передаём ID фильма
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public static class TVShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvShowName, tvShowYear, tvShowRating;
        ImageView posterImageView;

        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShowName = itemView.findViewById(R.id.tvShowName);
            tvShowYear = itemView.findViewById(R.id.tvShowYear);
            tvShowRating = itemView.findViewById(R.id.tvShowRating);
            posterImageView = itemView.findViewById(R.id.posterImageView);
        }


    }
}
