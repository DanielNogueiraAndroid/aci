package com.aci.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.aci.movie.cache.OmdbMovieCache;
import com.aci.movie.omdb.OmdbMovie;
import com.aci.movie.omdb.OmdbSearchMovies;
import com.aci.movie.rxbinding.RxListPopupWindow;
import com.aci.movie.service.MovieService;
import com.aci.movie.ui.MoviesRecycler;
import com.aci.movie.util.RxLog;
import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxTextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    private static final Logger logger = LoggerFactory.getLogger("MainActivity");
    @Inject
    MovieService movieService;

    @Bind(R.id.searchText)
    EditText searchText;

    @Bind(R.id.movies_list)
    MoviesRecycler moviesRecycler;

    @Bind(R.id.empty_recycler)
    View emptyRecyclerView;
    ListPopupWindow popup;

    private MoviePopupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.component(this).inject(this);
        setContentView(R.layout.activity_main);
        popup = new ListPopupWindow(this);
        popup.setAnchorView(toolbar);

        adapter = new MoviePopupAdapter(this);

        popup.setAdapter(adapter);

        RxListPopupWindow.itemClickEvents(popup)
                .map(AdapterViewItemClickEvent::position)
                .map(adapter::getItem)
                .onBackpressureDrop(item -> RxLog.log("drop", item))
                .subscribe(new Subscriber<OmdbMovie>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(
                        MainActivity.this, " onError omdbMovie ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(OmdbMovie omdbMovie) {
                Toast.makeText(
                        MainActivity.this, "onNext omdbMovie "+ omdbMovie.getTitle(),Toast
                        .LENGTH_SHORT).show();
                calldetailActivity(omdbMovie);
            }
        });

    }

    private void calldetailActivity(OmdbMovie omdbMovie) {
        OmdbMovieCache.getInstance().setOmdbMovie(omdbMovie);
        Intent intentMovieDetail = new Intent(this, MovieDetailActivity.class);
        startActivity(intentMovieDetail);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Observable<CharSequence> searchObs = RxTextView.textChanges(searchText);

        searchObs.debounce(250, TimeUnit.MILLISECONDS)
                .filter(charSequence -> charSequence.length() > 1)
                .map(CharSequence::toString)
                .switchMap(movieService::searchMovie)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(this::setMovies, this::handleError);

        searchObs.filter(charSequence -> charSequence.length() <= 1)
                .subscribe(charSequence -> popup.dismiss());

    }

    private void handleError(Throwable throwable) {
        logger.error("searching error", throwable);
        Snackbar.make(searchText, throwable.toString(), Snackbar.LENGTH_LONG).show();

    }

    void setMovies(OmdbSearchMovies movies) {
        if (movies.errorMessage == null) {
            adapter.setMovieList(movies.movies);
            popup.show();
        } else {
            popup.dismiss();
            Snackbar.make(searchText, movies.errorMessage, Snackbar.LENGTH_LONG).show();
        }
    }
}
