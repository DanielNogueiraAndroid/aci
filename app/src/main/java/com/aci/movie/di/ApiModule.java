package com.aci.movie.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.aci.movie.BuildConfig;
import com.aci.movie.omdb.OmdbApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by ciprian.grigor on 08/11/15.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    OmdbApi provideOmdbApi(OkHttpClient okHttpClient, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        return retrofit.create(OmdbApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.interceptors().add(logging);
        }
        return client;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

}
