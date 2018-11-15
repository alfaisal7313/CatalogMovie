package com.android.arsa.catalogmoviedicoding.data.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String TABLE_MOVIE = "favorit";

    public static class ColumnMovie implements BaseColumns {
       public static final String COLUMNS_TITLE = "title";
        public static final String COLUMNS_OVERVIEW = "overview";
       public static final String COLUMNS_POSTER_PATH = "poster_path";
       public static final String COLUMNS_BACKDROP_PATH = "backdrop_path";
       public static final String COLUMNS_RELEASE_DATE = "release_date";
       public static final String COLUMNS_VOTE_AVERAGE = "vote_average";
       public static final String COLUMNS_POPULARITY = "popularity";
    }

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_MOVIE + "(" +
            ColumnMovie._ID + " INTEGER PRIMARY KEY," +
            ColumnMovie.COLUMNS_TITLE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_OVERVIEW + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_POSTER_PATH + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_BACKDROP_PATH + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_RELEASE_DATE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_VOTE_AVERAGE + " TEXT NOT NULL," +
            ColumnMovie.COLUMNS_POPULARITY + " TEXT NOT NULL);";

    static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_MOVIE;


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
}
