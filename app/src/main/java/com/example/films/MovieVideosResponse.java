package com.example.films;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieVideosResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<MovieVideo> results;
}

