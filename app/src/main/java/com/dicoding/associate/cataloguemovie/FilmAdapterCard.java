package com.dicoding.associate.cataloguemovie;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FilmAdapterCard extends RecyclerView.Adapter<FilmAdapterCard.CardViewViewHolder> {

    private Context context;
    private ArrayList<FilmItems> listFilm;

    public FilmAdapterCard(Context context) {
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
    public FilmAdapterCard.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_items_cardview, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapterCard.CardViewViewHolder holder, int position) {
        final FilmItems items = getListFilm().get(position);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w154/" + items.getPosterPath())
                .apply(new RequestOptions().override(154, 200))
                .into(holder.imageFilm);
        holder.tvTitleFilm.setText(items.getTitle());
        holder.tvDescFilm.setText(items.getDesc());
        holder.tvDateRelease.setText(setDateFormat(items.getDateRelease()));

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
//                Toast.makeText(context, "Detail " + items.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.EXTRA_FILM, items);
                view.getContext().startActivity(intent);
            }
        }));


        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallBack() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share " + items.getTitle(), Toast.LENGTH_SHORT).show();
            }
        }));

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
        if (rawDate != null) dateRelease = new SimpleDateFormat("MMM dd, yyyy").format(rawDate);
        return dateRelease;
    }

    @Override
    public int getItemCount() {
        if (listFilm == null) return 0;
        return getListFilm().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFilm;
        TextView tvTitleFilm, tvDescFilm, tvDateRelease;
        Button btnDetail, btnShare;
        public CardViewViewHolder(View itemView) {
            super(itemView);
            imageFilm = itemView.findViewById(R.id.image_film);
            tvTitleFilm = itemView.findViewById(R.id.tv_title_film);
            tvDescFilm = itemView.findViewById(R.id.tv_desc_film);
            tvDateRelease = itemView.findViewById(R.id.tv_date_release);
            btnDetail = itemView.findViewById(R.id.btn_detail);
            btnShare = itemView.findViewById(R.id.btn_share);
        }
    }
}
