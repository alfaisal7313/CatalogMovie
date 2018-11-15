package com.android.arsa.catalogmoviedicoding.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;

import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.TABLE_MOVIE;

@SuppressWarnings("UnusedReturnValue")
public class FavoriteHelper {
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public FavoriteHelper(Context mContext) {
        this.mContext = mContext;
    }

    public FavoriteHelper open() {
        DatabaseHelper mDbHelper = new DatabaseHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabase.close();
    }

    private ContentValues values(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(ColumnMovie._ID, movie.getId());
        values.put(ColumnMovie.COLUMNS_TITLE, movie.getTitle());
        values.put(ColumnMovie.COLUMNS_OVERVIEW, movie.getOverview());
        values.put(ColumnMovie.COLUMNS_POSTER_PATH, movie.getPoster_path());
        values.put(ColumnMovie.COLUMNS_BACKDROP_PATH, movie.getBackdrop_path());
        values.put(ColumnMovie.COLUMNS_RELEASE_DATE, movie.getRelease_date());
        values.put(ColumnMovie.COLUMNS_VOTE_AVERAGE, movie.getVote_average());
        values.put(ColumnMovie.COLUMNS_POPULARITY, movie.getPopularity());
        return values;
    }

//    public ArrayList<Movie> query() {
//        ArrayList<Movie> arrayList = new ArrayList<>();
//        String sql = "SELECT * FROM " + TABLE_MOVIE + " ORDER BY " + ColumnMovie._ID + " ASC";
//        Cursor cursor = mDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        Movie movie;
//        if (cursor.getCount() > 0) {
//            do {
//                movie = new Movie();
//                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ColumnMovie._ID)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_TITLE)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_OVERVIEW)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_POSTER_PATH)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_BACKDROP_PATH)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_RELEASE_DATE)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_VOTE_AVERAGE)));
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ColumnMovie.COLUMNS_POPULARITY)));
//
//                arrayList.add(movie);
//                cursor.moveToNext();
//            } while (!cursor.isAfterLast());
//        }
//        cursor.close();
//        return arrayList;
//    }

    @SuppressWarnings("UnusedReturnValue")
    public long dbInsert(Movie movie) {
        return mDatabase.insert(TABLE_MOVIE, null, values(movie));
    }

//    public int update(Movie movie) {
//        return mDatabase.update(TABLE_MOVIE, values(movie), ColumnMovie._ID +
//                "  = '" + String.valueOf(movie.getId()) + "'", null);
//    }

    @SuppressWarnings("UnusedReturnValue")
    public int dbDelete(Movie movie) {
        return mDatabase.delete(TABLE_MOVIE, ColumnMovie._ID + " = '" + movie.getId() + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return mDatabase.query(TABLE_MOVIE
                , null
                , ColumnMovie._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return mDatabase.query(TABLE_MOVIE
                , null
                , null
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return mDatabase.insert(TABLE_MOVIE, null, values);
    }

    public int updateProvide(String id, ContentValues values) {
        return mDatabase.update(TABLE_MOVIE, values, ColumnMovie._ID + " = ?", new String[]{id});
    }

    public int deleteProvide(String id) {
        return mDatabase.delete(TABLE_MOVIE, ColumnMovie._ID + " = ?", new String[]{id});
    }
}
