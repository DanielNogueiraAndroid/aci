package com.aci.movie.service.transformer;

import android.content.ContentValues;

import com.aci.movie.db.MovieItem;
import com.aci.movie.omdb.OmdbMovieDetails;

/**
 * Created by ciprian.grigor on 12/11/15.
 */
public class OmdbMovieToDb {

    public static ContentValues buildMovieItemDb(OmdbMovieDetails omdbMovie) {
        return new MovieItem.Builder().listId(1)
                .actors(omdbMovie.actors)
                .plot(omdbMovie.plot)
                .rating(omdbMovie.imdbRating)
                .poster(omdbMovie.posterUri.toString())
                .title(omdbMovie.title)
                .build();
    }
}
