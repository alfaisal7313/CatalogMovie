package com.android.arsa.catalogmoviedicoding.view.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.FragmentNowPlayingBinding;
import com.android.arsa.catalogmoviedicoding.utils.AsyncMovieLoader;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.android.arsa.catalogmoviedicoding.view.adapter.MovieAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    private FragmentNowPlayingBinding bindingView;

    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private MovieAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        bindingView = FragmentNowPlayingBinding.bind(view);

        int gridCount = getResources().getInteger(R.integer.grid_column_count);

        bindingView.nowPlayingFrag.setLayoutManager(new GridLayoutManager(context, gridCount));
        bindingView.nowPlayingFrag.setHasFixedSize(true);

        adapter = new MovieAdapter(context);
        bindingView.nowPlayingFrag.setAdapter(adapter);

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
            bindingView.progress.setVisibility(View.GONE);
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

