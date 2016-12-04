package com.aci.movie.omdb;

/**
 * Created by ciprian.grigor on 09/11/15.
 */

import com.google.gson.annotations.SerializedName;

public class OmdbMovieDetails extends OmdbMovie {

    @SerializedName("Rated")
    public String rated;

    @SerializedName("Runtime")
    public String runtime;

    @SerializedName("Director")
    public String director;

    @SerializedName("Writer")
    public String writer;

    @SerializedName("Actors")
    public String actors;

    @SerializedName("Plot")
    public String plot;

    public String imdbRating;

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    @Override
    public String toString() {
        return "OmdbMovieDetails{" +
                "actors='" + actors + '\'' +
                ", rated='" + rated + '\'' +
                ", runtime='" + runtime + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", plot='" + plot + '\'' +
                "} " + super.toString();
    }
}
