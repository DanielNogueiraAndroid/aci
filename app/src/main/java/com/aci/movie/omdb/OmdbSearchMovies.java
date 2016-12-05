package com.aci.movie.omdb;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * {
 *      Search: [
 *          {...},
 *          {...}
 *      ]
 * }
 */
public class OmdbSearchMovies extends OmdbResponse {

    @SerializedName("Search")
    public final List<OmdbMovie> movies = Collections.emptyList();

    public OmdbSearchMovies(Throwable throwable) {
        errorMessage = throwable.toString();
        success = false;
    }

    @Override
    public String toString() {
        return "OmdbSearchMovies{" +
                "movies=" + movies +
                "} " + super.toString();
    }
}
