package com.aci.movie.di;

import android.content.Context;

import com.aci.movie.MovieApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide external libs dependencies with application scope
 * <p>
 * Created by ciprian.grigor on 05/10/15.
 */
@Module
public class ApplicationModule {

    private final MovieApplication app;

    public ApplicationModule(MovieApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }
}
