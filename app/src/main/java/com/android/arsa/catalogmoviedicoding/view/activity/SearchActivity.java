package com.android.arsa.catalogmoviedicoding.view.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.ActivitySearchBinding;
import com.android.arsa.catalogmoviedicoding.utils.AsyncMovieLoader;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.android.arsa.catalogmoviedicoding.view.adapter.SearchAdapter;

import java.util.ArrayList;
public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    RecyclerView rvMain;

    private static final String EXTRA_SEARCH_MOVIE = "EXTRA_SEARCH_MOVIE";

    private SearchAdapter adapter;
    private SearchView searchView;
    private String queryMovie;
    private ProgressDialog dialogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding viewBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        initComponent();
    }

    private void initComponent() {
        dialogProgress = new ProgressDialog(SearchActivity.this);
        adapter = new SearchAdapter(this);
        adapter.notifyDataSetChanged();

        rvMain.setLayoutManager(new LinearLayoutManager(this));
        rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvMain.setAdapter(adapter);
    }

    private void initDialogProcess() {
        dialogProgress.setMessage(getResources().getString(R.string.search_process));
        dialogProgress.setCancelable(true);
        dialogProgress.setInverseBackgroundForced(true);
        dialogProgress.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_SEARCH_MOVIE, searchView.getQuery().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            queryMovie = savedInstanceState.getString(EXTRA_SEARCH_MOVIE);
            getSupportLoaderManager().initLoader(0, savedInstanceState, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search_view));
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setQueryHint(getResources().getString(R.string.hint_search));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();

        if (!TextUtils.isEmpty(queryMovie)) {
            searchView.onActionViewExpanded();
            searchView.setQuery(queryMovie, false);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String value) {
                if (TextUtils.isEmpty(value)) {
                    return true;
                }
                searchView.clearFocus();
                initDialogProcess();
                String query = searchView.getQuery().toString();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_SEARCH_MOVIE, query);
                getSupportLoaderManager().restartLoader(0, bundle, SearchActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String movie = "";
        if (bundle != null) {
            movie = bundle.getString(EXTRA_SEARCH_MOVIE);
        }
        return new AsyncMovieLoader(this, Const.searchMovie(movie));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies.size() > 0) {
            dialogProgress.dismiss();
        }
        adapter.setMoviesItem(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setMoviesItem(null);
    }

}
