package com.aci.movie;

import com.aci.movie.di.DaggerApiComponent;
import com.aci.movie.omdb.OmdbApi;
import com.aci.movie.omdb.OmdbMovieDetails;
import com.aci.movie.omdb.OmdbSearchMovies;
import com.aci.movie.util.RxLog;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

/**
 * Created by ciprian.grigor on 07/11/15.
 */
public class OmbdApiTest {

    OmdbApi api;

    @Before
    public void setUp() {
        api = DaggerApiComponent.create().getOmbdApi();
    }


    @Test
    public void searchByTitle() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("star").compose(RxLog.insertLog()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
    }

    @Test
    public void getByTitle() {
        TestSubscriber<OmdbMovieDetails> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: Episode VII - The Force Awakens").compose(RxLog.insertLog()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

    }


    @Test
    public void getByTitleNotFound() {
        TestSubscriber<OmdbMovieDetails> sub = new TestSubscriber<>();

        api.getByTitle("Star Wars: Episode XX").compose(RxLog.insertLog()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);

    }

    @Test
    public void searchByTitleNotFound() {
        TestSubscriber<OmdbSearchMovies> sub = new TestSubscriber<>();

        api.searchByTitle("Star Wars: Episode XX").compose(RxLog.insertLog()).subscribe(sub);

        sub.awaitTerminalEvent();
        sub.assertNoErrors();
        sub.assertCompleted();
        sub.assertUnsubscribed();
        sub.assertValueCount(1);
    }
}
