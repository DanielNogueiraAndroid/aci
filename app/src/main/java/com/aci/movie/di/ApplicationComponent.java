package com.aci.movie.di;

import com.aci.movie.MainActivity;
import com.aci.movie.MovieDetailActivity;
import com.aci.movie.MovieAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application component that is used to inject application scope dependencies
 * <p>
 * Created by ciprian.grigor on 05/10/15.
 */

@Singleton
@Component(
        modules = {ApplicationModule.class, ApiModule.class}
)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(MovieDetailActivity movieDetailActivityActivity);

    void inject(MovieAdapter moviePopupAdapter);
}
