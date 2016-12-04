package com.aci.movie.service;

import com.aci.movie.omdb.OmdbApi;
import com.aci.movie.omdb.OmdbMovieDetails;
import com.aci.movie.omdb.OmdbSearchMovies;
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

    @Inject
    public MovieService(OmdbApi api) {
        this.api = api;
    }

    public Observable<OmdbSearchMovies> searchMovie(String title) {
        return api.searchByTitle(title).compose(RxLog.insertLog())
                .retry(3)
                .onErrorReturn(OmdbSearchMovies::new)
                .subscribeOn(Schedulers.io());
    }

    public Observable<OmdbMovieDetails> getMovie(String id) {
        return api.getById(id).compose(RxLog.insertLog())
                .retry(3)
                .subscribeOn(Schedulers.io());
    }

}
