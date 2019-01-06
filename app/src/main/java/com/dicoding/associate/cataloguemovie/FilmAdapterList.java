package com.dicoding.associate.cataloguemovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FilmAdapterList extends RecyclerView.Adapter<FilmAdapterList.ListViewHolder> {

    private Context context;
    private ArrayList<FilmItems> listFilm;

    public FilmAdapterList(Context context) {
        this.context = context;
    }

    public ArrayList<FilmItems> getListFilm() {
        return listFilm;
    }

    public void setListFilm(ArrayList<FilmItems> listFilm) {
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
        FilmItems items = getListFilm().get(position);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w92/" + items.getPosterPath())
                .apply(new RequestOptions().override(54, 70))
                .into(holder.imageFilm);
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

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFilm;
        TextView tvTitleFilm, tvDescFilm, tvDateRelease;
        public ListViewHolder(View itemView) {
            super(itemView);
            imageFilm = itemView.findViewById(R.id.image_film);
            tvTitleFilm = itemView.findViewById(R.id.tv_title_film);
            tvDescFilm = itemView.findViewById(R.id.tv_desc_film);
            tvDateRelease = itemView.findViewById(R.id.tv_date_release);
        }
    }
}
