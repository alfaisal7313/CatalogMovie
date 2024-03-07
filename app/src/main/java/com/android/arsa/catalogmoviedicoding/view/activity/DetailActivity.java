package com.android.arsa.catalogmoviedicoding.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.android.arsa.catalogmoviedicoding.databinding.ActivityDetailBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.db.FavoriteHelper;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMovieBackdrop;
import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static boolean isFavorite = false;

    private ActivityDetailBinding viewBinding;
    private Movie movie;
    private FavoriteHelper helper;

    @SuppressLint({"RestrictedApi", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        helper = new FavoriteHelper(this).open();
        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                movie = new Movie(cursor);
                cursor.close();
            }
        } else {
            movie = getIntent().getParcelableExtra(EXTRA_DATA);
        }

        int data = helper.queryByIdProvider(String.valueOf(movie.getId())).getCount();
        if (data > 0) {
            viewBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_check);
            isFavorite = true;
        } else {
            viewBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_uncheck);
            isFavorite = false;
        }
        initComponent();
    }

    private void initComponent() {
        setupToolbar();
        viewBinding.titleDetail.setText(movie.getTitle());
        viewBinding.releaseDateDetail.setText(String.format("%s : %s", getResources().getString(R.string.release_date), movie.getRelease_date()));
        viewBinding.voteDetail.setText(String.format("%s/", movie.getVote_average()));
        if (movie.getOverview().length() <= 0) {
            viewBinding.overviewDetail.setText(getResources().getString(R.string.text_null));
        } else {
            viewBinding.overviewDetail.setText(movie.getOverview());
        }

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getApplicationContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(35f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE);
        circularProgressDrawable.start();

        Glide.with(this).load(loadMoviePoster(movie.getPoster_path()))
                .apply(new RequestOptions().placeholder(R.drawable.placholder)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(viewBinding.posterPathDetail);

        Glide.with(this).load(loadMovieBackdrop(movie.getBackdrop_path()))
                .apply(new RequestOptions().placeholder(circularProgressDrawable)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(viewBinding.backdropMovieDetail);

        viewBinding.btnFavorite.setOnClickListener(this);
        viewBinding.btnShare.setOnClickListener(this);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movie.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat
                    .getColor(this, android.R.color.transparent)));
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_favorite:
                if (!isFavorite) {
                    helper.dbInsert(movie);
                    viewBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_check);
                    isFavorite = true;
                    showMessage(getString(R.string.msg_success_fav));
                } else {
                    helper.dbDelete(movie);
                    viewBinding.btnFavorite.setImageResource(R.drawable.ic_favorite_uncheck);
                    isFavorite = false;
                    showMessage(getString(R.string.msg_remove_fav));
                }
                break;
            case R.id.btn_share:
                shareMovie();
                break;
        }
    }

    private void shareMovie() {
        Intent shareMovie = new Intent(Intent.ACTION_SEND);
        shareMovie.setType(getString(R.string.action_send_type));

        String titleMovie = viewBinding.titleDetail.getText().toString();
        String imgPoster = String.valueOf(Const.loadMovieBackdrop(movie.getPoster_path()));
        String overviewMovie = viewBinding.overviewDetail.getText().toString();
        String contentShare = String.format("%s\n\n%s\n%s\n\n%s", titleMovie,
                getString(R.string.image_movie), imgPoster, overviewMovie);

        shareMovie.putExtra(android.content.Intent.EXTRA_TEXT, contentShare);
        startActivity(shareMovie);
    }

    private void showMessage(String message) {
        Snackbar.make(viewBinding.vRoot, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }
}
