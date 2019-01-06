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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String URL = "https://api.themoviedb.org/3/movie/upcoming?language=en-US&api_key=" + API_KEY;

    private RecyclerView rvUpcoming;
    private ProgressBar progressBar;
    private ArrayList<FilmItems> list = new ArrayList<>();
    private FilmAdapterCard adapter;


    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upcoming, container, false);

        rvUpcoming = rootView.findViewById(R.id.rv_upcoming);
        rvUpcoming.setHasFixedSize(true);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(this.getContext()));

        progressBar = rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter = new FilmAdapterCard(this.getContext());
        adapter.setListFilm(list);
        rvUpcoming.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);

        return rootView;
    }

    @NonNull
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, @Nullable Bundle args) {
        adapter.setListFilm(null);
        progressBar.setVisibility(View.VISIBLE);
        return new MyAsyncTaskLoader(this.getContext(), URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {
        progressBar.setVisibility(View.GONE);
        adapter.setListFilm(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FilmItems>> loader) {
        adapter.setListFilm(null);
    }
}
