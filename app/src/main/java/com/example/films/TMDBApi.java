package com.example.films;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface TMDBApi {
    @GET("movie/popular?&language=ru-Ru")
    Call<TVShowResponse> getTopRatedTVShows(@Header("Authorization") String token);

    @GET("movie/popular?&language=ru-Ru")
    Call<MovieResponse> getPopularMovies(@Header("Authorization") String token);

    @GET("movie/{movie_id}?language=ru-Ru")
    Call<MovieDetails> getMovieDetails(@Path("movie_id") int movieId, @Header("Authorization") String token);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideosResponse> getMovieVideos(@Path("movie_id") int movieId, @Header("Authorization") String token);

}