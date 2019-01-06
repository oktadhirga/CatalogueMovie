package com.dicoding.associate.cataloguemovie;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallBack onItemClickCallBack;

    public CustomOnItemClickListener(int position, OnItemClickCallBack onItemClickCallBack) {
        this.position = position;
        this.onItemClickCallBack = onItemClickCallBack;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallBack.onItemClicked(view, position);

    }

    public interface OnItemClickCallBack {
        void onItemClicked(View view, int position);
    }
}
