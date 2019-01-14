package com.dicoding.associate.cataloguemovie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.associate.cataloguemovie.Model.FilmModel;
import com.dicoding.associate.cataloguemovie.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class FilmAdapterList extends RecyclerView.Adapter<FilmAdapterList.ListViewHolder> {

    private Context context;
    private ArrayList<FilmModel> listFilm;

    public FilmAdapterList(Context context) {
        this.context = context;
    }

    private ArrayList<FilmModel> getListFilm() {
        return listFilm;
    }

    public void setListFilm(ArrayList<FilmModel> listFilm) {
        this.listFilm = listFilm;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmAdapterList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapterList.ListViewHolder holder, int position) {
        FilmModel items = getListFilm().get(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w92/" + items.getPosterPath())
                .into(holder.imgMovie);
        holder.tvTitleFilm.setText(items.getTitle());
        holder.tvDescFilm.setText(items.getDesc());
        holder.tvDateRelease.setText(setDateFormat(items.getDateRelease()));

    }

    private String setDateFormat(String date) {
        //Set Release Date
        Date rawDate = null;
        try {
            rawDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dateRelease = "";
        if (rawDate != null) dateRelease = new SimpleDateFormat("MMM yyyy").format(rawDate);
        return dateRelease;
    }

    @Override
    public int getItemCount() {
        if (listFilm == null) return 0;
        return getListFilm().size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleFilm, tvDescFilm, tvDateRelease;
        CircleImageView imgMovie;
        ListViewHolder(View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            tvTitleFilm = itemView.findViewById(R.id.tv_title_film);
            tvDescFilm = itemView.findViewById(R.id.tv_desc_film);
            tvDateRelease = itemView.findViewById(R.id.tv_date_release);
        }
    }
}
