package com.dicoding.associate.cataloguemovie.Database;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContract {

    static String TABLE_FAVORITE = "table_favorite";

    public static final class FavoriteColumns {
        public static String ID = "_id";
        public static String TITLE = "title";
        public static String VOTE_AVERAGE = "vote_average";
        public static String POSTER_PATH = "poster_path";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
    }

    private static final String AUTHORITY =
            "com.dicoding.associate.cataloguemovie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

}
