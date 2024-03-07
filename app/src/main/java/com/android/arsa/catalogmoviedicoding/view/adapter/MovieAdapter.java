package com.android.arsa.catalogmoviedicoding.view.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.ItemPosterBinding;
import com.android.arsa.catalogmoviedicoding.view.activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private ArrayList<Movie> moviesItem = new ArrayList<>();
    private final Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMoviesItem(ArrayList<Movie> moviesItem) {
        this.moviesItem = moviesItem;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPosterBinding binding = ItemPosterBinding.inflate(LayoutInflater.from(context));
        return new MovieHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Movie movie = moviesItem.get(position);

        holder.tvTitle.setText(movie.getTitle());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE);
        circularProgressDrawable.start();

        Glide.with(context).load(loadMoviePoster(movie.getPoster_path()))
                .apply(new RequestOptions().placeholder(circularProgressDrawable)
                        .error(R.drawable.error).centerCrop())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail = new Intent(context, DetailActivity.class);
                intentDetail.putExtra(DetailActivity.EXTRA_DATA, movie);
                intentDetail.putExtra(DetailActivity.EXTRA_POSITION, position);
                    context.startActivity(intentDetail);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return moviesItem == null ? 0 : moviesItem.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgPoster;

        MovieHolder(@NonNull ItemPosterBinding itemBinding) {
            super(itemBinding.getRoot());
            tvTitle = itemBinding.titlePoster;
            imgPoster = itemBinding.imgPoster;
        }
    }
}
