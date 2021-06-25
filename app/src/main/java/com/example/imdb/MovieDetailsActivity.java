package com.example.imdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imdb.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    /**This class contains the logic for the more detailed page for each movie.
     * In addition to the title, description, and image of a movie, this page also can display the rating.
     */

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        image = (ImageView) findViewById(R.id.image);
        //By using getIntent and setIntent, we are able to pass information between activities about what movie to display
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage /2.0f);

        String imageUrl;
        String backupImage;

        //This code diplays the images and is very similar to the code in the MovieAdapter class
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            imageUrl = movie.getPosterPath();
            backupImage = "R.drawable.flicks_movie_placeholder";
            Glide.with(this).load(imageUrl).
                    placeholder(Drawable.createFromPath(backupImage))
                    .error(R.drawable.flicks_movie_placeholder).
                    into(image);
        }
        else{
            //if phone is in landscape
            imageUrl = movie.getBackdropPath();
            backupImage = "R.drawable.flicks_backdrop_placeholder";
            Glide.with(this).load(imageUrl).
                    placeholder(Drawable.createFromPath(backupImage))
                    .error(R.drawable.flicks_backdrop_placeholder).
                    into(image);
        }
    }
}