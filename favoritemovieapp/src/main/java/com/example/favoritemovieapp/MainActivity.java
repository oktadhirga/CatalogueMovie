package com.example.favoritemovieapp;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.favoritemovieapp.Adapter.FavoriteMovieAdapter;

import static com.example.favoritemovieapp.Database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int LOAD_NOTES_ID = 110;
    ListView lvFavorite;
    private FavoriteMovieAdapter favoriteMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Favorite Movie");

        lvFavorite = findViewById(R.id.lv_favorite);

        favoriteMovieAdapter = new FavoriteMovieAdapter(this, null, true);
        lvFavorite.setAdapter(favoriteMovieAdapter);

        LoaderManager.getInstance(this).initLoader(LOAD_NOTES_ID, null, this);
//        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderManager.getInstance(this).restartLoader(LOAD_NOTES_ID, null, this);
//        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        favoriteMovieAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        favoriteMovieAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoaderManager.getInstance(this).destroyLoader(LOAD_NOTES_ID);
//        getSupportLoaderManager().destroyLoader((LOAD_NOTES_ID));
    }


}
