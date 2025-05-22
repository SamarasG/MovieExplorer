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

/**
 *  a RecyclerView Adapter  to display the list of movies.
 * display movie poster-title-release date.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movieList;
    private final Context context;
    private final OnItemClickListener listener;

    /**
        constructor for the adapter to initicialize the movie list data and the listener to click them
     */
    public MovieAdapter(List<Movie> movieList, Context context, OnItemClickListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }

    /**
     *  when RecyclerView needs new ViewHolder.
     * and then Inflate the layout for each movie item.
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    /**
    Bind the data to each one viewholder by getting an item from each position in the list of movies
     and then set tittle and release data in the textview and the poster in the posterimage to serve it to glide
    so glide can place it in ImageView and set onclick listener for the item view
     */
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

    /**
     * ViewHolder class that has the 3 views for the movie item.
     * Avoids repeated findViewById calls for performance.
     */
    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView, releaseDateTextView;

        //link the variable to the coresponding field in the item movie.xml file
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.imageViewPoster);
            titleTextView = itemView.findViewById(R.id.textViewTitle);
            releaseDateTextView = itemView.findViewById(R.id.textViewReleaseDate);
        }
    }

}
