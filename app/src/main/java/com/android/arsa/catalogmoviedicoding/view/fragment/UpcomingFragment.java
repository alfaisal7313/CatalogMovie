package com.android.arsa.catalogmoviedicoding.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.utils.AsyncMovieLoader;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.android.arsa.catalogmoviedicoding.view.adapter.MovieAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{

    @BindView(R.id.upcoming_frag)
    RecyclerView rvFragUpcoming;
    @BindView(R.id.progress)
    ProgressBar pbLoading;

    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private MovieAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        ButterKnife.bind(this, view);
        setupRecyclerView();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, Const.SORT_UPCOMING);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    private void setupRecyclerView() {
        int gridCount = getResources().getInteger(R.integer.grid_column_count);

        rvFragUpcoming.setLayoutManager(new GridLayoutManager(context, gridCount));
        rvFragUpcoming.setHasFixedSize(true);

        adapter = new MovieAdapter(context);
        rvFragUpcoming.setAdapter(adapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String movieType = "";
        if (bundle != null) {
            movieType = bundle.getString(EXTRA_MOVIE);
        }
        return new AsyncMovieLoader(context, Const.sortMovie(movieType));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        if (movies.size() > 0) {
            pbLoading.setVisibility(View.GONE);
        }
        adapter.setMoviesItem(movies);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setMoviesItem(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null){
            this.context = context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
