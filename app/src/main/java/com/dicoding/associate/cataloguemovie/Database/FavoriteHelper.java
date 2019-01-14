package com.dicoding.associate.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.dicoding.associate.cataloguemovie.Model.FilmModel;

import java.util.ArrayList;

import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.ID;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.POSTER_PATH;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.VOTE_AVERAGE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static String DATABASE_TABLE = TABLE_FAVORITE;

    protected Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<FilmModel> getAllData() {
        Cursor cursor = database.query(TABLE_FAVORITE, null, null, null,
                null, null, TITLE + " ASC", null);
        cursor.moveToFirst();
        ArrayList<FilmModel> arrayList = new ArrayList<>();
        FilmModel filmModel;
        if (cursor.getCount() > 0) {
            do {
                filmModel = new FilmModel();
                filmModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                filmModel.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                filmModel.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                filmModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                filmModel.setDateRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                filmModel.setVote(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                arrayList.add(filmModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean isFavorite(int id) {
        Cursor cursor = database.query(TABLE_FAVORITE, null, ID + " = ?", new String[] {String.valueOf(id)},
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public long insert(FilmModel filmModel) {
        ContentValues values = new ContentValues();
        values.put(ID, filmModel.getId());
        values.put(POSTER_PATH, filmModel.getPosterPath());
        values.put(TITLE, filmModel.getTitle());
        values.put(OVERVIEW, filmModel.getDesc());
        values.put(RELEASE_DATE, filmModel.getDateRelease());
        values.put(VOTE_AVERAGE, filmModel.getVote());
        return database.insert(TABLE_FAVORITE, null, values);
    }

    public int delete(int id){
        return database.delete(TABLE_FAVORITE, ID + " = '" + id + "'", null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE,
                null, null, null, null, null, TITLE + " ASC");
    }
    
}
