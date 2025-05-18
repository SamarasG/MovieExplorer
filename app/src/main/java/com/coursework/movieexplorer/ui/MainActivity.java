package com.coursework.movieexplorer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.movieexplorer.R;
import com.coursework.movieexplorer.adapter.MovieAdapter;
import com.coursework.movieexplorer.model.Movie;
import com.coursework.movieexplorer.repository.MovieRepository;
import com.coursework.movieexplorer.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private MovieRepository movieRepository;

    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private final List<Movie> movieList = new ArrayList<>();

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && !isLastPage && totalItemCount <= (lastVisibleItem + 5)) {
                    currentPage++;
                    loadPopularMovies(currentPage);
                }
            }
        });
        movieRepository = new MovieRepository();

        if (NetworkUtil.isConnected(this)) {
            loadPopularMovies(currentPage);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void loadPopularMovies(int page) {
        isLoading = true;
        movieRepository.getPopularMovies(page, new MovieRepository.MovieCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                isLoading = false;
                if (movies.isEmpty()) {
                    isLastPage = true;
                    return;
                }
                movieList.addAll(movies);
                if (adapter == null) {
                    adapter = new MovieAdapter(movieList, MainActivity.this, movie -> {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyItemRangeInserted(movieList.size() - movies.size(), movies.size());
                }
            }

            @Override
            public void onError(String errorMessage) {
                isLoading = false;
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
