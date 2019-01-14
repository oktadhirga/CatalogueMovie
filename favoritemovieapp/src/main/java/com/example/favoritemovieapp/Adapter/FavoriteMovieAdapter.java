package com.example.favoritemovieapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.favoritemovieapp.R;

import static com.example.favoritemovieapp.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.example.favoritemovieapp.Database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static com.example.favoritemovieapp.Database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.example.favoritemovieapp.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.example.favoritemovieapp.Database.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.example.favoritemovieapp.Database.DatabaseContract.getColumnDouble;
import static com.example.favoritemovieapp.Database.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {
    public FavoriteMovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_favorite_movie, parent, false);
    }

    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvDesc = view.findViewById(R.id.tv_desc);
            TextView tvDateRelease = view.findViewById(R.id.tv_date_release);
            ImageView imgMovie = view.findViewById(R.id.img_movie);
            TextView tvVote = view.findViewById(R.id.tv_vote);

            tvTitle.setText(getColumnString(cursor, TITLE));
            tvDesc.setText(getColumnString(cursor, OVERVIEW));
            String release = "Release : " + getColumnString(cursor, RELEASE_DATE);
            tvDateRelease.setText(release);
            String vote = "Vote : " + String.valueOf(getColumnDouble(cursor, VOTE_AVERAGE));
            tvVote.setText(vote);
            Glide.with(view).load("http://image.tmdb.org/t/p/w92/" + getColumnString(cursor, POSTER_PATH)).into(imgMovie);
        }
    }
}
