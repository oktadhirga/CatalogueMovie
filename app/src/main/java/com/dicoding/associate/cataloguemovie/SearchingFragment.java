package com.dicoding.associate.cataloguemovie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchingFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    private static final String API_KEY = BuildConfig.API_KEY;
    private static final String URL_FILM = "url_film";

    private RecyclerView rvSearching;
    private FilmAdapterList adapter;
    private EditText edtSearch;
    private Button btnSearch;
    private ProgressBar progressBar;

    public SearchingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_searching, container, false);

        edtSearch = rootView.findViewById(R.id.edt_search);
        btnSearch = rootView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        progressBar = rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter = new FilmAdapterList(this.getContext());

        rvSearching = rootView.findViewById(R.id.rv_searching);
        rvSearching.setHasFixedSize(true);
        rvSearching.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvSearching.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            String judul = edtSearch.getText().toString();
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY +"&language=en-US&query=" + judul;
            if (TextUtils.isEmpty(judul)) {
                edtSearch.setError("Field harus diisi");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(URL_FILM, url);
                getLoaderManager().restartLoader(0, bundle,this);
            }
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, @Nullable Bundle bundle) {
        String url = "";
        if (bundle != null) url = bundle.getString(URL_FILM);
        adapter.setListFilm(null);
        progressBar.setVisibility(View.VISIBLE);
        return new MyAsyncTaskLoader(this.getContext(), url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<FilmItems>> loader, final ArrayList<FilmItems> data) {
        adapter.setListFilm(data);
        progressBar.setVisibility(View.GONE);

        ItemClickSupport.addTo(rvSearching).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(recyclerView.getContext(), FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.EXTRA_FILM, data.get(position));
                recyclerView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<FilmItems>> loader) {
        adapter.setListFilm(null);
    }
}
