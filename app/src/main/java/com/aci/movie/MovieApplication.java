package com.aci.movie;

import android.app.Application;
import android.content.Context;

import com.aci.movie.di.ApplicationComponent;
import com.aci.movie.di.ApplicationModule;
import com.aci.movie.di.DaggerApplicationComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Movie RX Application
 * <p>
 * Created by ciprian.grigor on 01/10/15.
 */
public class MovieApplication extends Application {

    private static final Logger logger = LoggerFactory.getLogger("MovieApplication");

    private ApplicationComponent component;

    public static ApplicationComponent component(Context context) {
        return ((MovieApplication) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("create");
        this.component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        logger.info("lowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        logger.info("trimMemory level {}", level);
    }
}

