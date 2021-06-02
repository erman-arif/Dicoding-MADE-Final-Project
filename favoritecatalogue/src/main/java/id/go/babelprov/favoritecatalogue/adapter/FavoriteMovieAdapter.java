package id.go.babelprov.favoritecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.go.babelprov.favoritecatalogue.R;
import id.go.babelprov.favoritecatalogue.model.Favorite;
import id.go.babelprov.favoritecatalogue.model.Movies;
import id.go.babelprov.favoritecatalogue.view.DetailMovieActivity;


public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewholder>{

    private Context context;
    private final ArrayList<Favorite> listFavMovies = new ArrayList<>();

    // ------------------------------------------------
    //   Constructor
    // ------------------------------------------------
    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    // ------------------------------------------------
    //   Get List Favorite Moview
    // ------------------------------------------------
    public ArrayList<Favorite> getListFavMovies() {
        return listFavMovies;
    }

    // ------------------------------------------------
    //   Set List Favorite Moview
    // ------------------------------------------------
    public void setListFavMovies(ArrayList<Favorite> listFavMovies) {
        this.listFavMovies.clear();
        this.listFavMovies.addAll(listFavMovies);
        notifyDataSetChanged();
    }

    // ------------------------------------------------
    //   onCreateViewHolder
    // ------------------------------------------------
    @NonNull
    @Override
    public FavoriteMovieViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list_wide, parent, false);
        return new FavoriteMovieViewholder(view);
    }

    // ------------------------------------------------
    //   onCreateViewHolder
    // ------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewholder holder, final int position) {

        Favorite currentFavMovie = listFavMovies.get(position);

        Glide.with(holder.itemView.getContext())
                .load(currentFavMovie.getBackdropPath())
                .into(holder.ivBackdropPoster);

        holder.tvTitle.setText(currentFavMovie.getTitle());
        holder.tvGenre.setText(currentFavMovie.getGenre());
        holder.tvDate.setText(currentFavMovie.getDate());
        holder.tvPopularity.setText(currentFavMovie.getPopularity().toString());
        holder.tvVoteCount.setText(currentFavMovie.getVoteCount().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movies favorite = new Movies();

                // Setup Data for Detail Movie Activity, send with Parcelable
                favorite.setId(listFavMovies.get(position).getId());
                favorite.setPosterPath(listFavMovies.get(position).getPosterPath());
                favorite.setBackdropPath(listFavMovies.get(position).getBackdropPath());
                favorite.setTitle(listFavMovies.get(position).getTitle());
                favorite.setGenre(listFavMovies.get(position).getGenre());
                favorite.setReleaseDate(listFavMovies.get(position).getDate());
                favorite.setPopularity(listFavMovies.get(position).getPopularity());
                favorite.setVoteCount(listFavMovies.get(position).getVoteCount());
                favorite.setOverview(listFavMovies.get(position).getOverview());

                Intent detailMovieIntent = new Intent(context, DetailMovieActivity.class);
                detailMovieIntent.putExtra(DetailMovieActivity.EXTRA_FAVMOVIE, favorite);
                context.startActivity(detailMovieIntent);
            }
        });
    }

    // ------------------------------------------------
    //   getItemCount
    // ------------------------------------------------
    @Override
    public int getItemCount() {
        return listFavMovies.size();
    }

    // ------------------------------------------------
    //   FavoriteMovieViewholder
    // ------------------------------------------------
    class FavoriteMovieViewholder extends RecyclerView.ViewHolder {
        final ImageView ivBackdropPoster;
        final TextView tvTitle, tvGenre, tvDate, tvPopularity, tvVoteCount;

        FavoriteMovieViewholder(View itemView) {
            super(itemView);

            ivBackdropPoster = itemView.findViewById(R.id.iv_wide_movie_poster);
            tvTitle = itemView.findViewById(R.id.tv_wide_movie_title);
            tvGenre = itemView.findViewById(R.id.tv_wide_movie_genre);
            tvDate = itemView.findViewById(R.id.tv_list_movie_release);
            tvPopularity = itemView.findViewById(R.id.tv_list_movie_popularity);
            tvVoteCount = itemView.findViewById(R.id.tv_list_movie_vote_count);
        }
    }
}
