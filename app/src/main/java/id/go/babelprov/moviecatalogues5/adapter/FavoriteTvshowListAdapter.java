package id.go.babelprov.moviecatalogues5.adapter;

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

import java.util.List;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.model.Tvshows;
import id.go.babelprov.moviecatalogues5.view.DetailTvshowActivity;

public class FavoriteTvshowListAdapter extends RecyclerView.Adapter<FavoriteTvshowListAdapter.FavoriteViewHolder>{

    private Context context;
    private final LayoutInflater mInflater;
    private List<Favorite> mFavorites;

    public FavoriteTvshowListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_movie_list_wide, parent, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, final int position) {
        if (mFavorites != null) {
            Favorite current = mFavorites.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(current.getBackdropPath())
                    .into(holder.backdropPath);

            holder.title.setText(current.getTitle());
            holder.date.setText(current.getDate());
            holder.genre.setText(current.getGenre());
            holder.popularity.setText(Double.toString(current.getPopularity()));
            holder.votecount.setText(Integer.toString(current.getVoteCount()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Tvshows favorite = new Tvshows();

                    // Setup Data for Detail Tvshow Activity, send with Parcelable
                    favorite.setId(mFavorites.get(position).getId());
                    favorite.setPosterPath(mFavorites.get(position).getPosterPath());
                    favorite.setBackdropPath(mFavorites.get(position).getBackdropPath());
                    favorite.setName(mFavorites.get(position).getTitle());
                    favorite.setGenre(mFavorites.get(position).getGenre());
                    favorite.setFirstAirDate(mFavorites.get(position).getDate());
                    favorite.setPopularity(mFavorites.get(position).getPopularity());
                    favorite.setVoteCount(mFavorites.get(position).getVoteCount());
                    favorite.setOverview(mFavorites.get(position).getOverview());

                    Intent detailTvshowIntent = new Intent(context.getApplicationContext(), DetailTvshowActivity.class);
                    detailTvshowIntent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, favorite);
                    context.startActivity(detailTvshowIntent);
                }
            });
        }
    }

    public void setFavorites(List<Favorite> favorites){
        mFavorites = favorites;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mFavorites != null) {
            return mFavorites.size();
        }
        else return 0;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView title, genre, date, popularity, votecount;
        ImageView backdropPath;

        private FavoriteViewHolder(View itemView) {
            super(itemView);

            backdropPath = itemView.findViewById(R.id.iv_wide_movie_poster);
            title = itemView.findViewById(R.id.tv_wide_movie_title);
            date = itemView.findViewById(R.id.tv_list_movie_release);
            genre = itemView.findViewById(R.id.tv_wide_movie_genre);
            popularity = itemView.findViewById(R.id.tv_list_movie_popularity);
            votecount = itemView.findViewById(R.id.tv_list_movie_vote_count);

        }
    }
}
