package com.coursework.movieexplorer.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.coursework.movieexplorer.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    private static final String PREFS_NAME = "favorites_prefs";
    private static final String FAVORITES_KEY = "favorite_movies";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public FavoritesRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveFavorite(Movie movie) {
        List<Movie> currentFavorites = getFavorites();
        currentFavorites.add(movie);
        String json = gson.toJson(currentFavorites);
        sharedPreferences.edit().putString(FAVORITES_KEY, json).apply();
    }

    public void removeFavorite(Movie movie) {
        List<Movie> currentFavorites = getFavorites();
        currentFavorites.removeIf(m -> m.getId() == movie.getId());
        String json = gson.toJson(currentFavorites);
        sharedPreferences.edit().putString(FAVORITES_KEY, json).apply();
    }

    public List<Movie> getFavorites() {
        String json = sharedPreferences.getString(FAVORITES_KEY, null);
        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<List<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public boolean isFavorite(Movie movie) {
        for (Movie m : getFavorites()) {
            if (m.getId() == movie.getId()) return true;
        }
        return false;
    }
}
