package com.example.films;

import com.google.gson.annotations.SerializedName;

public class MovieVideo {

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;

    @SerializedName("site")
    public String site;

    @SerializedName("type")
    public String type;

    @SerializedName("size")
    public int size;

    @SerializedName("official")
    public boolean official;
}