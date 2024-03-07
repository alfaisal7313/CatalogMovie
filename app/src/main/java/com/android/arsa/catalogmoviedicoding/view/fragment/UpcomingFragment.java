package com.android.arsa.catalogmoviedicoding.view.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.FragmentUpcomingBinding;
import com.android.arsa.catalogmoviedicoding.utils.AsyncMovieLoader;
import com.android.arsa.catalogmoviedicoding.utils.Const;
import com.android.arsa.catalogmoviedicoding.view.adapter.MovieAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>{
    private FragmentUpcomingBinding bindingView;
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private MovieAdapter adapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        bindingView = FragmentUpcomingBinding.bind(view);
        setupRecyclerView();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MOVIE, Const.SORT_UPCOMING);
        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    private void setupRecyclerView() {
        int gridCount = getResources().getInteger(R.integer.grid_column_count);

        bindingView.upcomingFrag.setLayoutManager(new GridLayoutManager(context, gridCount));
        bindingView.upcomingFrag.setHasFixedSize(true);

        adapter = new MovieAdapter(context);
        bindingView.upcomingFrag.setAdapter(adapter);
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
