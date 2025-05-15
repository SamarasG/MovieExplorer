package com.coursework.movieexplorer.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.coursework.movieexplorer.api.Api;
import com.coursework.movieexplorer.api.RetrofitClient;
import com.coursework.movieexplorer.model.Movie;
import com.coursework.movieexplorer.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private static final String API_KEY = "60b6eb9ba837b8eae6443f06ecafbbc6";
    private static final String TAG = "MovieRepository";

    private final Api api;

    public MovieRepository() {
        api = RetrofitClient.getApi();
    }

    // Fetch popular movies
    public void getPopularMovies(int page, final MovieCallback callback) {
        Call<MovieResponse> call = api.getPopularMovies(API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onError("Failed to load movies: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                callback.onError("Network failure: " + t.getMessage());
                Log.e(TAG, "API call failed", t);
            }
        });
    }

    // Callback interface
    public interface MovieCallback {
        void onSuccess(List<Movie> movies);
        void onError(String errorMessage);
    }
}
