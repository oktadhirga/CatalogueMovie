package com.dicoding.associate.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilmDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FILM = "extra_film";
    Toolbar toolbar;
    private TextView tvDetailRelease, tvDetailDesc;
    private ImageView ivDetailPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDetailRelease = findViewById(R.id.tv_detail_release);
        tvDetailDesc = findViewById(R.id.tv_detail_desc);
        ivDetailPoster = findViewById(R.id.iv_detail_poster);

        FilmItems film = getIntent().getParcelableExtra(EXTRA_FILM);

        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + film.getPosterPath()).into(ivDetailPoster);

        String release = String.format(getResources().getString(R.string.release), setDateFormat(film.getDateRelease()));
        tvDetailRelease.setText(release);
        tvDetailDesc.setText(film.getDesc());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(film.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private String setDateFormat(String date) {
        //Set Release Date
        Date rawDate = null;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            rawDate = formatDate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newFormat = new SimpleDateFormat("MMM dd, yyyy");
        String dateRelease = "";
        if (rawDate != null) dateRelease = newFormat.format(rawDate);
        return dateRelease;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
