package com.dicoding.associate.cataloguemovie;


import android.content.Intent;
import android.database.Cursor;
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
import com.dicoding.associate.cataloguemovie.Listener.ItemClickSupport;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.ID;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    FilmAdapterList adapter;
    Toolbar toolbar;

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

        rvFavorite.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFavorite.setAdapter(adapter);

        setHasOptionsMenu(true);

        Cursor favoriteList = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);

        assert favoriteList != null;
        if (favoriteList.getCount() == 0) {
            Toast.makeText(getContext(), getString(R.string.no_favorite_movies), Toast.LENGTH_SHORT).show();
        }

        //convert Cursor to Array List
        ArrayList<FilmModel> filmModels = convertCursor(favoriteList);

        adapter.setListFilm(filmModels);
        adapter.notifyDataSetChanged();

        clickSupport(filmModels);
        return rootView;
    }

    private ArrayList<FilmModel> convertCursor(Cursor cursor) {
        cursor.moveToFirst();
        ArrayList<FilmModel> arrayList = new ArrayList<>();
        FilmModel filmModel;
        if (cursor.getCount() > 0) {
            do {
                filmModel = new FilmModel();
                filmModel.setId(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                filmModel.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                filmModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                filmModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                filmModel.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                filmModel.setVote(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(filmModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();

        return arrayList;
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
