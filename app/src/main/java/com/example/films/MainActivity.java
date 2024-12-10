package com.example.films;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchMovies();
    }

    private void fetchMovies() {
        TMDBApi api = RetrofitClient.getRetrofitInstance().create(TMDBApi.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNjg0NDI4ZjNhODFkMTIzOWEwZjBlMzdlNDAwZjI0MyIsIm5iZiI6MTYxNzgwMzA0MS4wNDcwMDAyLCJzdWIiOiI2MDZkYjcyMWUxYWQ3OTAwNmVhOTk4ODYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.SsdBrIlPnAMjPl2R7i17Sy4o5zcoIixC2lJyI6dG7fE";

        api.getPopularMovies(token).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().results;
                    movieAdapter = new MovieAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
