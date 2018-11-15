package com.android.arsa.catalogmoviedicoding.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class AsyncMovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private ArrayList<Movie> mMovie;
    private boolean mHasResult = false;

    private final String url;

    public AsyncMovieLoader(@NonNull Context context, String url) {
        super(context);
        onContentChanged();
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mMovie);
        }
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movie> data) {
        mMovie = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStartLoading();
        if (mHasResult) {
            mMovie = null;
            mHasResult = false;
        }
    }

    @Nullable
    @Override
    public ArrayList<Movie> loadInBackground() {
        final ArrayList<Movie> movieList = new ArrayList<>();
        SyncHttpClient client = new SyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultMovie = new String(responseBody);
                    JSONArray results = new JSONObject(resultMovie).getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie movieItem = new Movie(movie);
                        movieList.add(movieItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieList;
    }
}
