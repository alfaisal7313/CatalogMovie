package com.android.arsa.catalogmoviedicoding.data.model;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.BaseColumns._ID;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_BACKDROP_PATH;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_OVERVIEW;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_POPULARITY;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_POSTER_PATH;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_RELEASE_DATE;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_TITLE;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.ColumnMovie.COLUMNS_VOTE_AVERAGE;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.getColumnInt;
import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private String vote_average;
    private String popularity;

//    public Movie() {
//    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, COLUMNS_TITLE);
        this.overview = getColumnString(cursor, COLUMNS_OVERVIEW);
        this.poster_path = getColumnString(cursor, COLUMNS_POSTER_PATH);
        this.backdrop_path = getColumnString(cursor, COLUMNS_BACKDROP_PATH);
        this.release_date = getColumnString(cursor, COLUMNS_RELEASE_DATE);
        this.vote_average = getColumnString(cursor, COLUMNS_VOTE_AVERAGE);
        this.popularity = getColumnString(cursor, COLUMNS_POPULARITY);
    }

    public Movie(JSONObject object){
        try {
            int id  = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");
            String r_date = object.getString("release_date");
            double vote_average = object.getDouble("vote_average");
            double popularity = object.getDouble("popularity");

//            String popular = new DecimalFormat("#.#").format(popularity);
            String vote_movie = new DecimalFormat("#.#").format(vote_average);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(r_date);
            @SuppressLint("SimpleDateFormat") String dateFormat = new SimpleDateFormat("EEEE, dd MM yyyy").format(date);

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster_path = poster_path;
            this.backdrop_path = backdrop_path;
            this.release_date = dateFormat;
            this.vote_average = vote_movie;
            this.popularity = String.valueOf(popularity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster_path);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.release_date);
        dest.writeString(this.vote_average);
        dest.writeString(this.popularity);
    }

    private Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.backdrop_path = in.readString();
        this.release_date = in.readString();
        this.vote_average = in.readString();
        this.popularity = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
