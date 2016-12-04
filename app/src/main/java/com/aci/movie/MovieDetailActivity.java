package com.aci.movie;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aci.movie.cache.OmdbMovieCache;
import com.aci.movie.omdb.OmdbMovie;
import com.aci.movie.omdb.OmdbMovieDetails;
import com.aci.movie.service.MovieService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MovieDetailActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MovieDetailActivity");
    @Inject
    MovieService movieService;
    @Bind(R.id.poster)
    ImageView posterView;
    @Bind(R.id.title)
    TextView titleView;
    @Bind(R.id.actors)
    TextView actorsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_movie_detail);
        OmdbMovie omdbMovie;
        omdbMovie = OmdbMovieCache.getInstance().getOmdbMovie();
        if (omdbMovie != null) {
            Toast.makeText(
                    MovieDetailActivity.this, "onCreate omdbMovie " + omdbMovie.getTitle(), Toast
                            .LENGTH_LONG).show();
            getDetails(omdbMovie);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    void getDetails(OmdbMovie omdbMovie) {
        movieService.getMovie(omdbMovie.getImdbId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OmdbMovieDetails>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(OmdbMovieDetails omdbMovieDetails) {
                        showMovie(omdbMovieDetails);
                    }
                });
    }


    void showMovie(OmdbMovieDetails omdbMovieDetails) {
        titleView.setText(omdbMovieDetails.getTitle());
        actorsView.setText(omdbMovieDetails.getActors());

        Glide.with(MovieDetailActivity.this)
                .load(omdbMovieDetails.getPosterUri().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterView);
    }
}
