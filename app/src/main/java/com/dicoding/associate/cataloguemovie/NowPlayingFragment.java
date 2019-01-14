package com.dicoding.associate.cataloguemovie;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.associate.cataloguemovie.Adapter.FilmAdapterCard;
import com.dicoding.associate.cataloguemovie.Loader.MyAsyncTaskLoader;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmModel>> {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String URL = "https://api.themoviedb.org/3/movie/now_playing?language=en-US&api_key=" + API_KEY;

    private ProgressBar progressBar;
    private ArrayList<FilmModel> list = new ArrayList<>();
    private FilmAdapterCard adapter;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);

        RecyclerView rvNowPlaying = rootView.findViewById(R.id.rv_now_playing);
        rvNowPlaying.setHasFixedSize(true);
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(this.getContext()));

        progressBar = rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter = new FilmAdapterCard(this.getContext());
        adapter.setListFilm(list);
        rvNowPlaying.setAdapter(adapter);
        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
//        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<FilmModel>> onCreateLoader(int id, @Nullable Bundle args) {
        adapter.setListFilm(null);
        progressBar.setVisibility(View.VISIBLE);
        return new MyAsyncTaskLoader(this.getContext(), URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FilmModel>> loader, ArrayList<FilmModel> data) {
        progressBar.setVisibility(View.GONE);
        adapter.setListFilm(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FilmModel>> loader) {
        adapter.setListFilm(null);
    }
}
