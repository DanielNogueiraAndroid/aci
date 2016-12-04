package com.aci.movie.omdb;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * The Open Movie Database API
 */
public interface OmdbApi {

    @GET("/?plot=full")
    Observable<OmdbMovieDetails> getById(@Query(value = "i", encoded = true) String id);

    @GET("/")
    Observable<OmdbSearchMovies> searchByTitle(@Query(value = "s", encoded = true) String title);
}
