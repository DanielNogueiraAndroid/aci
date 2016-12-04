package com.aci.movie.cache;

import com.aci.movie.omdb.OmdbMovie;

/**
 * Created by dn on 04/12/16.
 */

public final class OmdbMovieCache {

    private static final OmdbMovieCache INSTANCE = new OmdbMovieCache();
    OmdbMovie omdbMovie;

    public static OmdbMovieCache getInstance() {
        return INSTANCE;
    }

    public OmdbMovie getOmdbMovie() {
        return omdbMovie;
    }

    public void setOmdbMovie(OmdbMovie omdbMovie) {
        this.omdbMovie = omdbMovie;
    }

}