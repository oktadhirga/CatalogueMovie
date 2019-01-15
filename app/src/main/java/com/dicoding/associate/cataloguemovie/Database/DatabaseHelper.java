package com.dicoding.associate.cataloguemovie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.ID;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSON = 1;
    private static String DATABASE_NAME = "dbmovie";
    private static String CREATE_TABLE = "create table " + TABLE_FAVORITE +
            " (" + ID + " integer primary key autoincrement, " +
            TITLE + " text not null, " +
            VOTE_AVERAGE + " real not null, " +
            POSTER_PATH + " text not null, " +
            OVERVIEW + " text not null, " +
            RELEASE_DATE + " text not null );";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSON);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }


}
