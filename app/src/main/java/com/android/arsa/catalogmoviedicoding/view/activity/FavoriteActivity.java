package com.android.arsa.catalogmoviedicoding.view.activity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.view.adapter.FavoriteAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;

    private FavoriteAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int gridCount = getResources().getInteger(R.integer.grid_column_count);
        rvFavorite.setLayoutManager(new GridLayoutManager(this, gridCount));
        rvFavorite.setHasFixedSize(true);

        adapter = new FavoriteAdapter(this);
        adapter.setCursor(cursor);
        rvFavorite.setAdapter(adapter);

        new LoadMovieData().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        new LoadMovieData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadMovieData extends AsyncTask<Void, Void, Cursor> {

        @SuppressLint("NewApi")
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null,
                    null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(cursor);

            cursor = data;
            adapter.setCursor(cursor);
            adapter.notifyDataSetChanged();

            if (cursor.getCount() == 0){
                Snackbar.make(rvFavorite, getResources().getString(R.string.message_favorite_null), Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
