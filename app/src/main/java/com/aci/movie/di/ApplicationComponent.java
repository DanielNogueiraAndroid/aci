package com.aci.movie.di;

import com.aci.movie.MainActivity;
import com.aci.movie.MoviePopupAdapter;
import com.aci.movie.db.DbModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application component that is used to inject application scope dependencies
 * <p>
 * Created by ciprian.grigor on 05/10/15.
 */

@Singleton
@Component(
        modules = {ApplicationModule.class, ApiModule.class, DbModule.class}
)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(MoviePopupAdapter moviePopupAdapter);
}
