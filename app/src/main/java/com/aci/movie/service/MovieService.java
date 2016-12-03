package com.aci.movie.service;

import com.squareup.sqlbrite.BriteDatabase;
import com.aci.movie.db.MovieItem;
import com.aci.movie.omdb.OmdbApi;
import com.aci.movie.omdb.OmdbMovie;
import com.aci.movie.omdb.OmdbSearchMovies;
import com.aci.movie.service.transformer.OmdbMovieToDb;
import com.aci.movie.util.RxLog;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by ciprian.grigor on 11/11/15.
 */
@Singleton
public class MovieService {

    private OmdbApi api;
    private BriteDatabase db;

    @Inject
    public MovieService(OmdbApi api, BriteDatabase db) {
        this.api = api;
        this.db = db;
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return api.searchByTitle(title).compose(RxLog.insertLog())
                .retry(3)
                .onErrorReturn(OmdbSearchMovies::new)
                .subscribeOn(Schedulers.io());
    }

    public Observable<Long> saveMovie(OmdbMovie item) {
        return api.getByTitle(item.title)
                .map(OmdbMovieToDb::buildMovieItemDb)
                .map(cv -> db.insert(MovieItem.TABLE, cv))
                .subscribeOn(Schedulers.io());
    }
}
