package com.dicoding.associate.cataloguemovie;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.associate.cataloguemovie.Adapter.FilmAdapterList;
import com.dicoding.associate.cataloguemovie.Database.FavoriteHelper;
import com.dicoding.associate.cataloguemovie.Listener.ItemClickSupport;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    FilmAdapterList adapter;
    FavoriteHelper favoriteHelper;
    Toolbar toolbar;
    ArrayList<FilmModel> filmModels;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);

        progressBar.setVisibility(View.GONE);

        adapter = new FilmAdapterList(this.getContext());
        favoriteHelper = new FavoriteHelper(this.getContext());

        rvFavorite.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFavorite.setAdapter(adapter);

        setHasOptionsMenu(true);

        favoriteHelper.open();
        filmModels = favoriteHelper.getAllData();
        favoriteHelper.close();

        adapter.setListFilm(filmModels);
        adapter.notifyDataSetChanged();

        if (filmModels.size() == 0) {
            Toast.makeText(getContext(), getString(R.string.no_favorite_movies), Toast.LENGTH_SHORT).show();
        }

        clickSupport(filmModels);
        return rootView;
    }

    private void clickSupport(ArrayList<FilmModel> data) {
        ItemClickSupport.addTo(rvFavorite).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(recyclerView.getContext(), FilmDetailActivity.class);
            intent.putExtra(FilmDetailActivity.EXTRA_FILM, data.get(position));
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 101) {
            assert getFragmentManager() != null;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
}
