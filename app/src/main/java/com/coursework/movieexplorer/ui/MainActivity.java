package com.coursework.movieexplorer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.movieexplorer.R;
import com.coursework.movieexplorer.adapter.MovieAdapter;
import com.coursework.movieexplorer.model.Movie;
import com.coursework.movieexplorer.repository.MovieRepository;
import com.coursework.movieexplorer.util.NetworkUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieRepository movieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        findViewById(R.id.buttonFavorites).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieRepository = new MovieRepository();

        if (NetworkUtil.isConnected(this)) {
            loadPopularMovies();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void loadPopularMovies() {
        movieRepository.getPopularMovies(1, new MovieRepository.MovieCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MovieAdapter(movies, MainActivity.this, new MovieAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Movie movie) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
