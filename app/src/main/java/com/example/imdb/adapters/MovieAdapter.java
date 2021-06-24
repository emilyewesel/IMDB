package com.example.imdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imdb.MovieDetailsActivity;
import com.example.imdb.R;
import com.example.imdb.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;
    public static final String TAG = "MovieAdapter";


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

    //Inflating a layout and returning the holder
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        Log.d(TAG, "onCreateViewhOlder");
        return new ViewHolder(movieView);
    }

    //populate the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //get the movie at the postion specified
        //bind the movie data into the VM
        Log.d(TAG, "onBindViewhOlder" + position);

        Movie movie = movies.get(position);
        Log.d(TAG, "onBindViewhOlder" + movie.toString());

        holder.bind(movie);

    }

    //return the total count of items in a list
    @Override
    public int getItemCount() {
        Log.d(TAG, "onCount" + movies.size());

        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder (@NonNull View itemView){
            super (itemView);

            itemView.setOnClickListener(this);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            String backupImage;
            //if phone is in portrait
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                imageUrl = movie.getPosterPath();
                backupImage = "R.drawable.flicks_movie_placeholder";
                Glide.with(context).load(imageUrl).
                        placeholder(Drawable.createFromPath(backupImage))
                        .error(R.drawable.flicks_movie_placeholder).
                        into(ivPoster);
            }
            else{
                //if phone is in landscape
                imageUrl = movie.getBackdropPath();
                backupImage = "R.drawable.flicks_backdrop_placeholder";
                Glide.with(context).load(imageUrl).
                        placeholder(Drawable.createFromPath(backupImage))
                        .error(R.drawable.flicks_backdrop_placeholder).
                        into(ivPoster);
            }


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position >=0 && position < movies.size()){
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }

        }
    }
}
