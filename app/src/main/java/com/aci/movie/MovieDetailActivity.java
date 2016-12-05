package com.aci.movie;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aci.movie.omdb.OmdbMovieDetails;
import com.aci.movie.service.MovieService;
import com.aci.movie.util.EspressoIdlingResource;
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

    public static final String EXTRA_ID = "extra_id";
    private static final Logger logger = LoggerFactory.getLogger("MovieDetailActivity");
    @Inject
    public
    MovieService movieService;

    @Bind(R.id.proggres_bar)
    public
    ProgressBar progressBarView;
    @Bind(R.id.scroll_view)
    public
    ScrollView scrollView;
    @Bind(R.id.poster_content)
    public
    ImageView posterView;
    @Bind(R.id.title_content)
    public
    TextView titleView;
    @Bind(R.id.released_content)
    public
    TextView releasedView;
    @Bind(R.id.runtime_content)
    public
    TextView runtimeView;
    @Bind(R.id.genre_content)
    public
    TextView genreView;
    @Bind(R.id.director_content)
    public
    TextView directorView;
    @Bind(R.id.writer_content)
    public
    TextView writerView;
    @Bind(R.id.actors_content)
    public
    TextView actorsView;
    @Bind(R.id.plot_content)
    public
    TextView plotView;
    @Bind(R.id.language_content)
    public
    TextView languageView;
    @Bind(R.id.country_content)
    public
    TextView countryView;
    @Bind(R.id.awards_content)
    public
    TextView awardsView;
    private int retry = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_movie_detail);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        String id = bundle.getString(EXTRA_ID);
        if (id == null) {
            return;
        }
        EspressoIdlingResource.increment();
        getDetails(id);
    }

    private void getDetails(String id) {
        movieService.getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OmdbMovieDetails>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        retry++;
                        if(retry >= 1){
                            toast(getString(R.string.connection_error));
                            progressBarView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNext(OmdbMovieDetails omdbMovieDetails) {
                        showMovie(omdbMovieDetails);
                        showContent();
                        EspressoIdlingResource.decrement();
                    }
                });
    }


    private void showContent() {
        if(progressBarView != null && scrollView != null){
                progressBarView.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void toast(String text) {
        Toast.makeText(
                MovieDetailActivity.this, text,
                Toast.LENGTH_SHORT).show();
    }


    private void showMovie(OmdbMovieDetails omdbMovieDetails) {

        titleView.setText(omdbMovieDetails.getTitle());
        releasedView.setText(omdbMovieDetails.getReleased());
        runtimeView.setText(omdbMovieDetails.getRuntime());
        genreView.setText(omdbMovieDetails.getGenre());
        directorView.setText(omdbMovieDetails.getDirector());
        writerView.setText(omdbMovieDetails.getWriter());
        actorsView.setText(omdbMovieDetails.getActors());
        plotView.setText(omdbMovieDetails.getPlot());
        languageView.setText(omdbMovieDetails.getLanguage());
        countryView.setText(omdbMovieDetails.getCountry());
        awardsView.setText(omdbMovieDetails.getAwards());

        Glide.with(MovieDetailActivity.this)
                .load(omdbMovieDetails.getPosterUri().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(posterView);

    }
}
