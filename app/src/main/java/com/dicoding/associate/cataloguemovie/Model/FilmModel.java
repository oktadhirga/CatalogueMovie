package com.dicoding.associate.cataloguemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class FilmModel implements Parcelable {

    public static final Parcelable.Creator<FilmModel> CREATOR = new Parcelable.Creator<FilmModel>() {
        @Override
        public FilmModel createFromParcel(Parcel source) {
            return new FilmModel(source);
        }

        @Override
        public FilmModel[] newArray(int size) {
            return new FilmModel[size];
        }
    };

    private String id;
    private String posterPath;
    private String title;
    private String desc;
    private String dateRelease;
    private double vote;


    public FilmModel(JSONObject film) {

        try {
            this.posterPath = film.getString("poster_path");
            this.title = film.getString("title");
            this.desc = film.getString("overview");
            this.dateRelease = film.getString("release_date");
            this.id = film.getString("id");
            this.vote = film.getDouble("vote_average");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FilmModel(Parcel in) {
        this.posterPath = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.dateRelease = in.readString();
        this.id = in.readString();
        this.vote = in.readDouble();
    }

    public FilmModel() {

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

    public String getId() {
        return id;
    }

    public double getVote() {
        return vote;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }

    public void setVote(double vote) {
        this.vote = vote;
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
        dest.writeString(this.id);
        dest.writeDouble(this.vote);
    }

}
