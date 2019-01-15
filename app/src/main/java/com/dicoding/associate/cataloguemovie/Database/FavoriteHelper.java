package com.dicoding.associate.cataloguemovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.ID;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.FavoriteColumns.TITLE;
import static com.dicoding.associate.cataloguemovie.Database.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {

    private static String DATABASE_TABLE = TABLE_FAVORITE;

    protected Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public boolean isFavorite(String id) {
        Cursor cursor = database.query(TABLE_FAVORITE, null, ID + " = ?", new String[] {id},
                null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE,
                null, null, null, null, null, TITLE + " ASC");
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null, ID + " = ?", new String[]{id},
                null, null, null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteProvider(String id) {
        return database.delete(TABLE_FAVORITE, ID + " = ?", new String[] {id});
    }
    
}
