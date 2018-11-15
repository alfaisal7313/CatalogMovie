package com.android.arsa.favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.android.arsa.favorite.DatabaseContract.ColumnMovie.COLUMNS_BACKDROP_PATH;
import static com.android.arsa.favorite.DatabaseContract.ColumnMovie.COLUMNS_OVERVIEW;
import static com.android.arsa.favorite.DatabaseContract.ColumnMovie.COLUMNS_TITLE;
import static com.android.arsa.favorite.DatabaseContract.getColumnString;

public class CustomAdapter extends CursorAdapter {


    public CustomAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_poster, viewGroup, false);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @SuppressLint("CheckResult")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleMovie = null, overviewMovie;
        ImageButton btnFavorite;
        ImageView backdropMovie;

        if (cursor != null) {
            titleMovie = view.findViewById(R.id.title_fav);
            overviewMovie = view.findViewById(R.id.subtitle_fav);
            backdropMovie = view.findViewById(R.id.img_backdrop_fav);

            titleMovie.setText(getColumnString(cursor, COLUMNS_TITLE));
            overviewMovie.setText(getColumnString(cursor, COLUMNS_OVERVIEW));

            String urlImage = context.getString(R.string.url_image) + getColumnString(cursor, COLUMNS_BACKDROP_PATH);

            Glide.with(context).load(urlImage)
                    .into(backdropMovie);
        }

    }
}
