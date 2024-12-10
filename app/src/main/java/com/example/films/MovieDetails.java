package com.example.films;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetails {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("overview")
    public String overview;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("genres")
    public List<Genre> genres;

    public static class Genre {
        @SerializedName("id")
        public int id;

        @SerializedName("name")
        public String name;
    }
}
