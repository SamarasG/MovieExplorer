package com.coursework.movieexplorer.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coursework.movieexplorer.R;
import com.coursework.movieexplorer.adapter.MovieAdapter;
import com.coursework.movieexplorer.model.Movie;
import com.coursework.movieexplorer.repository.FavoritesRepository;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private MovieAdapter adapter;
    private FavoritesRepository favoritesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        favoritesRepository = new FavoritesRepository(this);
        loadFavorites();
    }

    private void loadFavorites() {
        List<Movie> favorites = favoritesRepository.getFavorites();

        if (favorites.isEmpty()) {
            Toast.makeText(this, "No favorite movies saved.", Toast.LENGTH_SHORT).show();
        }

        adapter = new MovieAdapter(favorites, this, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie) {

            }
        });
        recyclerViewFavorites.setAdapter(adapter);
    }
}
