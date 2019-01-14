package com.dicoding.associate.cataloguemovie;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.associate.cataloguemovie.Adapter.FilmAdapterList;
import com.dicoding.associate.cataloguemovie.Listener.ItemClickSupport;
import com.dicoding.associate.cataloguemovie.Loader.MyAsyncTaskLoader;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingFragment extends Fragment {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String URL_FILM = "url_film";

    @BindView(R.id.rv_searching)
    RecyclerView rvSearching;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Toolbar toolbar;
    private FilmAdapterList adapter;
    private LoaderManager.LoaderCallbacks<ArrayList<FilmModel>> loaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<FilmModel>>() {
        @NonNull
        @Override
        public Loader<ArrayList<FilmModel>> onCreateLoader(int i, @Nullable Bundle bundle) {
            String url = "";
            if (bundle != null) url = bundle.getString(URL_FILM);
            adapter.setListFilm(null);
            progressBar.setVisibility(View.VISIBLE);
            return new MyAsyncTaskLoader(getContext(), url);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList<FilmModel>> loader, ArrayList<FilmModel> data) {
            adapter.setListFilm(data);
            progressBar.setVisibility(View.GONE);

            ItemClickSupport.addTo(rvSearching).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(recyclerView.getContext(), FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.EXTRA_FILM, data.get(position));
                recyclerView.getContext().startActivity(intent);
            });
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList<FilmModel>> loader) {
            adapter.setListFilm(null);
        }
    };


    public SearchingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_searching, container, false);
        ButterKnife.bind(this, rootView);

        progressBar.setVisibility(View.GONE);

        adapter = new FilmAdapterList(this.getContext());

        rvSearching.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        rvSearching.setHasFixedSize(true);
        rvSearching.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvSearching.setAdapter(adapter);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.option_menu_searching, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        AppBarLayout appBar = getActivity().findViewById(R.id.app_bar);
        appBar.setExpanded(true, true);

        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.option_search).getActionView();

            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getString(R.string.movie_title));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String movieTitle) {
                    String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + movieTitle;
                    Bundle bundle;
                    bundle = new Bundle();
                    bundle.putString(URL_FILM, url);
                    LoaderManager.getInstance(getActivity()).restartLoader(0, bundle, loaderCallbacks);
//                    getLoaderManager().restartLoader(0, bundle,loaderCallbacks);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }


    }


}
