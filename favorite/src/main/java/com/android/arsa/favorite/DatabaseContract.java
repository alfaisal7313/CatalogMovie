package com.android.arsa.favorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_MOVIE = "favorit";

    public static class ColumnMovie implements BaseColumns {
        public static String COLUMNS_TITLE = "title";
        public static String COLUMNS_OVERVIEW = "overview";
        public static String COLUMNS_POSTER_PATH = "poster_path";
        public static String COLUMNS_BACKDROP_PATH = "backdrop_path";
        public static String COLUMNS_RELEASE_DATE = "release_date";
        public static String COLUMNS_VOTE_AVERAGE = "vote_average";
        public static String COLUMNS_POPULARITY = "popularity";
    }

    static String CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "(" +
            ColumnMovie._ID + " INTEGER PRIMARY KEY," +
            ColumnMovie.COLUMNS_TITLE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_OVERVIEW + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_POSTER_PATH + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_BACKDROP_PATH + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_RELEASE_DATE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_VOTE_AVERAGE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_POPULARITY + " TEXT NOT NULL);";

    static String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_MOVIE;


    public static final String AUTHORITY = "com.android.arsa.catalogmoviedicoding";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String tableName) {
        return cursor.getString(cursor.getColumnIndex(tableName));
    }

    public static int getColumnInt(Cursor cursor, String tableName) {
        return cursor.getInt(cursor.getColumnIndex(tableName));
    }

    public long getColumnLong(Cursor cursor, String tableName) {
        return cursor.getLong(cursor.getColumnIndex(tableName));
    }
}
