package com.android.arsa.catalogmoviedicoding.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.ItemMovieRowBinding;
import com.android.arsa.catalogmoviedicoding.databinding.ItemPosterBinding;
import com.android.arsa.catalogmoviedicoding.view.activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private static final String TAG = SearchAdapter.class.getSimpleName();
    private final Context context;
    private ArrayList<Movie> moviesItem = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setMoviesItem(ArrayList<Movie> moviesItem) {
        this.moviesItem = moviesItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMovieRowBinding binding = ItemMovieRowBinding.inflate(LayoutInflater.from(context));
        return new SearchViewHolder(binding);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int position) {
        final Movie movie = moviesItem.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvReleaseDate.setText(movie.getRelease_date());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvVote.setText(movie.getVote_average());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(25f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE);
        circularProgressDrawable.start();

        Glide.with(holder.itemView.getContext()).load(loadMoviePoster(movie.getPoster_path()))
                .apply(new RequestOptions().placeholder(circularProgressDrawable)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(holder.imgPoster);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailMovie = new Intent(context, DetailActivity.class);
                detailMovie.putExtra(DetailActivity.EXTRA_DATA, movie);
                context.startActivity(detailMovie);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, String.valueOf(moviesItem.size()));
        return moviesItem == null ? 0 : moviesItem.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        TextView tvReleaseDate;
        TextView tvVote;
        ImageView imgPoster;
        RelativeLayout rootView;

        private SearchViewHolder(@NonNull ItemMovieRowBinding itemBinding) {
            super(itemBinding.getRoot());
            tvTitle = itemBinding.titleMovieItem;
            tvOverview = itemBinding.overviewMovieItem;
            tvReleaseDate = itemBinding.releaseDateMovieItem;
            tvVote = itemBinding.voteMovieItem;
            imgPoster = itemBinding.posterMovieItem;
            rootView = itemBinding.rootViewItem;
        }
    }
}
