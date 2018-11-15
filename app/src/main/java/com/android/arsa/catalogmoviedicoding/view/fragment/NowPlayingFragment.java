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
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    @BindView(R.id.progress)
    ProgressBar pbLoading;
    @BindView(R.id.now_playing_frag)
    RecyclerView rvFragNowPlaying;

    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private MovieAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);

        int gridCount = getResources().getInteger(R.integer.grid_column_count);

        rvFragNowPlaying.setLayoutManager(new GridLayoutManager(context, gridCount));
        rvFragNowPlaying.setHasFixedSize(true);

        adapter = new MovieAdapter(context);
        rvFragNowPlaying.setAdapter(adapter);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, Const.SORT_NOW_PLAYING);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
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
        if (movies.size() >0){
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

