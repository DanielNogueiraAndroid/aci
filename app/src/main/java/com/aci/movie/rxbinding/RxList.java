package com.aci.movie.rxbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;

import rx.Observable;

/**
 * Created by ciprian.grigor on 13/11/15.
 */
public class RxList {

    /**
     * Create an observable of item click events on {@code view}.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
     * to free this reference.
     */
    @CheckResult
    @NonNull
    public static Observable<AdapterViewItemClickEvent> itemClickEvents(@NonNull ListView list) {
        return Observable.create(new ListItemClickEventOnSubscribe(list));
    }
}
