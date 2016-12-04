package com.aci.movie.omdb;

import com.google.gson.annotations.SerializedName;


public class OmdbGetMovie extends OmdbResponse {

    @SerializedName("Get")
    public OmdbMovieDetails omdbMovieDetails = new OmdbMovieDetails();

    public OmdbGetMovie(Throwable throwable) {
        errorMessage = throwable.toString();
        success = false;
    }

    @Override
    public String toString() {
        return "OmdbGetMovie{" +
                "omdbMovieDetails =" + omdbMovieDetails +
                "} " + super.toString();
    }
}
