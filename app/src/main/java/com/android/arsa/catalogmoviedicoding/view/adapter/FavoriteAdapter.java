package com.android.arsa.catalogmoviedicoding.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.databinding.ItemPosterBinding;
import com.android.arsa.catalogmoviedicoding.view.activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final Context context;
    private Cursor cursor;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPosterBinding binding = ItemPosterBinding.inflate(LayoutInflater.from(context));
        return new FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position) {
        final Movie movie = getItem(position);

        holder.tvTitle.setText(movie.getTitle());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.setColorSchemeColors(Color.WHITE);
        circularProgressDrawable.start();

        Glide.with(context).load(loadMoviePoster(movie.getPoster_path()))
                .apply(new RequestOptions().placeholder(circularProgressDrawable)
                        .error(R.drawable.error).centerCrop().dontAnimate())
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail = new Intent(context, DetailActivity.class);
                intentDetail.putExtra(DetailActivity.EXTRA_DATA, movie);
                intentDetail.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                Uri uri = Uri.parse(DatabaseContract.CONTENT_URI + "/" + movie.getId());
                intentDetail.setData(uri);
                context.startActivity(intentDetail);
            }
        });
    }

    private Movie getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Valid");
        }
        return new Movie(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgPoster;
        private FavoriteViewHolder(@NonNull ItemPosterBinding itemBinding) {
            super(itemBinding.getRoot());
            tvTitle = itemBinding.titlePoster;
            imgPoster = itemBinding.imgPoster;
        }
    }
}
