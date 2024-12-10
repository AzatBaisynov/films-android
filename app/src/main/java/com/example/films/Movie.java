package com.example.films;

import com.google.gson.annotations.SerializedName;

public class Movie {
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
}