package com.android.arsa.catalogmoviedicoding.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.arsa.catalogmoviedicoding.R;
import com.android.arsa.catalogmoviedicoding.data.db.DatabaseContract;
import com.android.arsa.catalogmoviedicoding.data.model.Movie;
import com.android.arsa.catalogmoviedicoding.view.activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.arsa.catalogmoviedicoding.utils.Const.loadMoviePoster;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private Cursor cursor;

    public FavoriteAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_poster, viewGroup, false);
        return new FavoriteViewHolder(view);
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

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_poster)
        TextView tvTitle;
        @BindView(R.id.img_poster)
        ImageView imgPoster;

        private FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
