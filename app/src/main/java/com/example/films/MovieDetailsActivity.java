// File: MovieDetailsActivity.java
package com.example.films;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView movieTitle, movieOverview, releaseDate, movieRating;
    private WebView youtubeWebView;
    private int filmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details); // Убедитесь, что этот layout соответствует вашему XML

        // Инициализация элементов интерфейса
        moviePoster = findViewById(R.id.moviePoster);
        movieTitle = findViewById(R.id.movieTitle);
        movieOverview = findViewById(R.id.movieOverview);
        releaseDate = findViewById(R.id.releaseDate);
        movieRating = findViewById(R.id.movieRating);
        youtubeWebView = findViewById(R.id.youtubeWebView);

        // Настройка WebView
        setupWebView();

        // Получение ID фильма из Intent
        if (getIntent() != null && getIntent().hasExtra("film_id")) {
            filmId = getIntent().getIntExtra("film_id", -1);
            if (filmId != -1) {
                fetchMovieDetails(filmId);
                fetchMovieVideos(filmId);
            } else {
                Toast.makeText(this, "Неверный ID фильма", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "ID фильма не передан", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Настройка WebView для отображения YouTube видео
     */
    private void setupWebView() {
        WebSettings webSettings = youtubeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Включение JavaScript
        youtubeWebView.setWebViewClient(new WebViewClient()); // Открытие ссылок внутри WebView
    }

    /**
     * Загрузка деталей фильма по ID
     */
    private void fetchMovieDetails(int movieId) {
        TMDBApi api = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNjg0NDI4ZjNhODFkMTIzOWEwZjBlMzdlNDAwZjI0MyIsIm5iZiI6MTYxNzgwMzA0MS4wNDcwMDAyLCJzdWIiOiI2MDZkYjcyMWUxYWQ3OTAwNmVhOTk4ODYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.SsdBrIlPnAMjPl2R7i17Sy4o5zcoIixC2lJyI6dG7fE"; // Замените на ваш токен

        api.getMovieDetails(movieId, token).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetails movie = response.body();
                    updateUI(movie);
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Ошибка загрузки деталей фильма", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Загрузка видео фильма по ID
     */
    private void fetchMovieVideos(int movieId) {
        TMDBApi api = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNjg0NDI4ZjNhODFkMTIzOWEwZjBlMzdlNDAwZjI0MyIsIm5iZiI6MTYxNzgwMzA0MS4wNDcwMDAyLCJzdWIiOiI2MDZkYjcyMWUxYWQ3OTAwNmVhOTk4ODYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.SsdBrIlPnAMjPl2R7i17Sy4o5zcoIixC2lJyI6dG7fE"; // Замените на ваш токен

        api.getMovieVideos(movieId, token).enqueue(new Callback<MovieVideosResponse>() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieVideo> videos = response.body().results;

                    if (!videos.isEmpty()) {
                        // Поиск YouTube видео
                        for (MovieVideo video : videos) {
                            Log.d("MovieDetailsActivity", "Получен ключ видео: " + video.key);
                            if ("YouTube".equalsIgnoreCase(video.site) && "Trailer".equalsIgnoreCase(video.type)) {
                                loadYouTubeVideo(video.key);

                                break;
                            }
                        }
                    } else {
                        Toast.makeText(MovieDetailsActivity.this, "Видео не найдены", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MovieDetailsActivity.this, "Ошибка загрузки видео", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Обновление UI элементами деталей фильма
     */
    private void updateUI(MovieDetails movie) {
        movieTitle.setText(movie.title);
        movieOverview.setText(movie.overview);
        releaseDate.setText("Дата релиза: " + movie.releaseDate);
        movieRating.setText("Рейтинг: " + movie.voteAverage);

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath;
        Glide.with(this)
                .load(imageUrl)
                .into(moviePoster);
    }

    /**
     * Загрузка YouTube видео в WebView
     *
     * @param videoKey Ключ видео с YouTube
     */
    private void loadYouTubeVideo(String videoKey) {
        String html = "<html><body style=\"margin:0;padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/" + videoKey + "\" " +
                "frameborder=\"0\" allowfullscreen></iframe>" +
                "</body></html>";
        Log.d("MovieDetailsActivity", "Загружаем HTML для видео: " + html);
        youtubeWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}
