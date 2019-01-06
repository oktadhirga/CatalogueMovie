package com.dicoding.associate.cataloguemovie;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<FilmItems>> {

    private ArrayList<FilmItems> mData;
    private boolean mHasResult = false;
    private String url;

    public MyAsyncTaskLoader(final Context context, String url) {
        super(context);
        this.url = url;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()) {
            forceLoad();
        } else {
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(ArrayList<FilmItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResource(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResource(ArrayList<FilmItems> mData) {
    }

    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<FilmItems> filmItems  = new ArrayList<>();

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i=0; i < results.length(); i++) {
                        JSONObject film = results.getJSONObject(i);
                        FilmItems items = new FilmItems(film);
                        filmItems.add(items);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return filmItems;
    }
}
