package com.android.arsa.catalogmoviedicoding.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.db.FavoriteHelper;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMovieBackdrop;
import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_DATA = "EXTRA_DATA";
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    private static boolean isFavorite = false;

    @BindViews({R.id.title_detail, R.id.release_date_detail, R.id.vote_detail, R.id.overview_detail})
    List<TextView> textViews;
    @BindViews({R.id.poster_path_detail, R.id.backdrop_movie_detail})
    List<ImageView> imageViews;
    @BindView(R.id.btn_favorite)
    ImageButton btnFavorite;
    @BindView(R.id.btn_share)
    ImageButton btnShare;
    @BindView(R.id.v_root)
    RelativeLayout vRoot;

    private Movie movie;
    private FavoriteHelper helper;

    @SuppressLint({"RestrictedApi", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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
            btnFavorite.setImageResource(R.drawable.ic_favorite_check);
            isFavorite = true;
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_uncheck);
            isFavorite = false;
        }
        initComponent();
    }

    private void initComponent() {
        setupToolbar();
        textViews.get(0).setText(movie.getTitle());
        textViews.get(1).setText(String.format("%s : %s", getResources().getString(R.string.release_date), movie.getRelease_date()));
        textViews.get(2).setText(String.format("%s/", movie.getVote_average()));
        if (movie.getOverview().length() <= 0) {
            textViews.get(3).setText(getResources().getString(R.string.text_null));
        } else {
            textViews.get(3).setText(movie.getOverview());
        }

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getApplicationContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(35f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE);
        circularProgressDrawable.start();

        Glide.with(this).load(loadMoviePoster(movie.getPoster_path()))
                .apply(new RequestOptions().placeholder(R.drawable.placholder)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(imageViews.get(0));

        Glide.with(this).load(loadMovieBackdrop(movie.getBackdrop_path()))
                .apply(new RequestOptions().placeholder(circularProgressDrawable)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(imageViews.get(1));

        btnFavorite.setOnClickListener(this);
        btnShare.setOnClickListener(this);
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
                    btnFavorite.setImageResource(R.drawable.ic_favorite_check);
                    isFavorite = true;
                    showMessage(getString(R.string.msg_success_fav));
                } else {
                    helper.dbDelete(movie);
                    btnFavorite.setImageResource(R.drawable.ic_favorite_uncheck);
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

        String titleMovie = textViews.get(0).getText().toString();
        String imgPoster = String.valueOf(Const.loadMovieBackdrop(movie.getPoster_path()));
        String overviewMovie = textViews.get(3).getText().toString();
        String contentShare = String.format("%s\n\n%s\n%s\n\n%s", titleMovie,
                getString(R.string.image_movie), imgPoster, overviewMovie);

        shareMovie.putExtra(android.content.Intent.EXTRA_TEXT, contentShare);
        startActivity(shareMovie);
    }

    private void showMessage(String message) {
        Snackbar.make(vRoot, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
    }
}
