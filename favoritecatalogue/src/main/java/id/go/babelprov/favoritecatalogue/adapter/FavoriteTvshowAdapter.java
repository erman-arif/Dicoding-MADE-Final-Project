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
import id.go.babelprov.favoritecatalogue.view.DetailTvshowActivity;


public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.FavoriteTvshowViewholder>{

    private Context context;
    private final ArrayList<Favorite> listFavTvshows = new ArrayList<>();

    // ------------------------------------------------
    //   Constructor
    // ------------------------------------------------
    public FavoriteTvshowAdapter(Context context) {
        this.context = context;
    }

    // ------------------------------------------------
    //   Get List Favorite Moview
    // ------------------------------------------------
    public ArrayList<Favorite> getListFavTvshows() {
        return listFavTvshows;
    }

    // ------------------------------------------------
    //   Set List Favorite Moview
    // ------------------------------------------------
    public void setListTvshows(ArrayList<Favorite> listFavTvshows) {
        this.listFavTvshows.clear();
        this.listFavTvshows.addAll(listFavTvshows);
        notifyDataSetChanged();
    }

    // ------------------------------------------------
    //   onCreateViewHolder
    // ------------------------------------------------
    @NonNull
    @Override
    public FavoriteTvshowViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list_wide, parent, false);
        return new FavoriteTvshowViewholder(view);
    }

    // ------------------------------------------------
    //   onCreateViewHolder
    // ------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull FavoriteTvshowViewholder holder, final int position) {

        Favorite currentFavMovie = listFavTvshows.get(position);

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

                Favorite favTvshows = new Favorite();

                // Setup Data for Detail Movie Activity, send with Parcelable
                favTvshows.setId(listFavTvshows.get(position).getId());
                favTvshows.setPosterPath(listFavTvshows.get(position).getPosterPath());
                favTvshows.setBackdropPath(listFavTvshows.get(position).getBackdropPath());
                favTvshows.setTitle(listFavTvshows.get(position).getTitle());
                favTvshows.setGenre(listFavTvshows.get(position).getGenre());
                favTvshows.setDate(listFavTvshows.get(position).getDate());
                favTvshows.setPopularity(listFavTvshows.get(position).getPopularity());
                favTvshows.setVoteCount(listFavTvshows.get(position).getVoteCount());
                favTvshows.setOverview(listFavTvshows.get(position).getOverview());

                Intent detailMovieIntent = new Intent(context, DetailTvshowActivity.class);
                detailMovieIntent.putExtra(DetailTvshowActivity.EXTRA_FAVTVSHOW, favTvshows);
                context.startActivity(detailMovieIntent);
            }
        });
    }

    // ------------------------------------------------
    //   getItemCount
    // ------------------------------------------------
    @Override
    public int getItemCount() {
        return listFavTvshows.size();
    }

    // ------------------------------------------------
    //   FavoriteTvshowViewholder
    // ------------------------------------------------
    class FavoriteTvshowViewholder extends RecyclerView.ViewHolder {
        final ImageView ivBackdropPoster;
        final TextView tvTitle, tvGenre, tvDate, tvPopularity, tvVoteCount;

        FavoriteTvshowViewholder(View itemView) {
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
