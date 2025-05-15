package com.coursework.movieexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coursework.movieexplorer.R;
import com.coursework.movieexplorer.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<Movie> movieList;
    private final Context context;

    private final OnItemClickListener listener;

    public MovieAdapter(List<Movie> movieList, Context context, OnItemClickListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.titleTextView.setText(movie.getTitle());
        holder.releaseDateTextView.setText(movie.getReleaseDate());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder) // Optional placeholder image
                .into(holder.posterImageView);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView, releaseDateTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.imageViewPoster);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            releaseDateTextView = itemView.findViewById(R.id.textViewReleaseDate);
        }
    }

}
