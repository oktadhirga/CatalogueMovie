package com.dicoding.associate.cataloguemovie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class FilmItems implements Parcelable {

    public static final Parcelable.Creator<FilmItems> CREATOR = new Parcelable.Creator<FilmItems>() {
        @Override
        public FilmItems createFromParcel(Parcel source) {
            return new FilmItems(source);
        }

        @Override
        public FilmItems[] newArray(int size) {
            return new FilmItems[size];
        }
    };
    private String posterPath;
    private String title;
    private String desc;
    private String dateRelease;


    public FilmItems(JSONObject film) {

        try {
            this.posterPath = film.getString("poster_path");
            this.title = film.getString("title");
            this.desc = film.getString("overview");
            this.dateRelease = film.getString("release_date");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected FilmItems(Parcel in) {
        this.posterPath = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.dateRelease = in.readString();
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.dateRelease);
    }
}
