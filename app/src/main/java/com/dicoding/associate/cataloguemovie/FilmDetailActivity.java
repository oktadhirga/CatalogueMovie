package com.dicoding.associate.cataloguemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.associate.cataloguemovie.Database.FavoriteHelper;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.CONTENT_URI;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.ID;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;

public class FilmDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";
    FavoriteHelper favoriteHelper;
    FilmModel film;
    int result = 100;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_release)
    TextView tvDetailRelease;
    @BindView(R.id.tv_detail_desc)
    TextView tvDetailDesc;
    @BindView(R.id.iv_detail_poster)
    ImageView ivDetailPoster;
    @BindView(R.id.tv_detail_vote)
    TextView tvDetailVote;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        film = getIntent().getParcelableExtra(EXTRA_FILM);

        // Check isFavorite;
        favoriteHelper = new FavoriteHelper(FilmDetailActivity.this);

        favoriteHelper.open();
        isFavorite = favoriteHelper.isFavorite(film.getId());

        Glide.with(this).load("http://image.tmdb.org/t/p/w154/" + film.getPosterPath()).into(ivDetailPoster);

        tvDetailTitle.setText(film.getTitle());
        String release = setDateFormat(film.getDateRelease());
        tvDetailRelease.setText(release);

        String vote = getString(R.string.vote) + String.valueOf(film.getVote());
        tvDetailVote.setText(vote);

        ratingBar.setRating((float) film.getVote() / 2);

        tvDetailDesc.setText(film.getDesc());


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(film.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message;
        if (item.getItemId() == R.id.option_favorite) {
            if (isFavorite) {
                getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + film.getId()), null, null);
                isFavorite = false;
                message = film.getTitle() + " " + getString(R.string.was_deleted);
                result = 101;
            } else {
                ContentValues values = new ContentValues();
                values.put(ID, film.getId());
                values.put(POSTER_PATH, film.getPosterPath());
                values.put(TITLE, film.getTitle());
                values.put(OVERVIEW, film.getDesc());
                values.put(RELEASE_DATE, film.getDateRelease());
                values.put(VOTE_AVERAGE, film.getVote());
                getContentResolver().insert(CONTENT_URI, values);
                isFavorite = true;
                message = film.getTitle() + " " + getString(R.string.was_added);
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isFavorite) {
            menu.findItem(R.id.option_favorite).setIcon(R.drawable.ic_favorite);
        } else {
            menu.findItem(R.id.option_favorite).setIcon(R.drawable.ic_favorite_border);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private String setDateFormat(String date) {
        //Set Release Date
        Date rawDate = null;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            rawDate = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String dateRelease = "";
        if (rawDate != null) dateRelease = newFormat.format(rawDate);
        return dateRelease;
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        setResult(result, data);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favoriteHelper != null) {
            favoriteHelper.close();
        }
    }
}
