package com.aci.movie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aci.movie.omdb.OmdbMovie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ciprian.grigor on 04/11/15.
 */
public class MovieAdapter extends BaseAdapter {

    private static final Logger logger = LoggerFactory.getLogger("MovieAdapter");
    private final LayoutInflater inflater;

    private List<OmdbMovie> data;

    public MovieAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public OmdbMovie getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).imdbId.hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.movie_dropdown_item, parent, false);
        } else {
            view = convertView;
        }

        ((TextView) view).setText(getItem(position).toString());

        return view;
    }

    public void setMovieList(List<OmdbMovie> movieList) {
        data = movieList;
        this.notifyDataSetChanged();
    }

    public void clear() {
        if (data != null) {
            data.clear();
            this.notifyDataSetChanged();
        }
    }
}