package com.coursework.movieexplorer.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.coursework.movieexplorer.R;
import com.coursework.movieexplorer.model.Movie;
import com.coursework.movieexplorer.repository.FavoritesRepository;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie_extra";

    private ImageView imageViewPoster;
    private TextView textViewTitle, textViewReleaseDate, textViewRating, textViewOverview;
    private Button buttonFavorite;

    private Movie movie;
    private FavoritesRepository favoritesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewRating = findViewById(R.id.textViewRating);
        textViewOverview = findViewById(R.id.textViewOverview);
        buttonFavorite = findViewById(R.id.buttonFavorite);

        favoritesRepository = new FavoritesRepository(this);

        movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        if (movie == null) {
            Toast.makeText(this, "Movie data not available", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateMovieDetails(movie);

        updateFavoriteButton();

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritesRepository.isFavorite(movie)) {
                    favoritesRepository.removeFavorite(movie);
                    Toast.makeText(DetailActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                } else {
                    favoritesRepository.saveFavorite(movie);
                    Toast.makeText(DetailActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                updateFavoriteButton();
            }
        });
    }

    private void populateMovieDetails(Movie movie) {
        textViewTitle.setText(movie.getTitle());
        textViewReleaseDate.setText("Release Date: " + movie.getReleaseDate());
        textViewRating.setText("Rating: " + movie.getVoteAverage());
        textViewOverview.setText(movie.getOverview());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(imageViewPoster);
    }

    private void updateFavoriteButton() {
        if (favoritesRepository.isFavorite(movie)) {
            buttonFavorite.setText("Remove from Favorites");
        } else {
            buttonFavorite.setText("Add to Favorites");
        }
    }
}
