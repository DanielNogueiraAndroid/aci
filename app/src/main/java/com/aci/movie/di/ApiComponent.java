package com.aci.movie.di;

import com.aci.movie.omdb.OmdbApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ciprian.grigor on 08/11/15.
 */
@Singleton
@Component(
        modules = ApiModule.class
)
public interface ApiComponent {
    OmdbApi getOmbdApi();
}
