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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.DataGenre;
import id.go.babelprov.moviecatalogues5.model.Genres;
import id.go.babelprov.moviecatalogues5.model.Tvshows;
import id.go.babelprov.moviecatalogues5.view.DetailTvshowActivity;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder>{

    //-------------------------------------------------------------------------------------------
    //   Declare Properties
    //-------------------------------------------------------------------------------------------
    private static final int LIST_LAYOUT = 1;
    private static final int GRID_LAYOUT = 2;
    private int typeLayout;
    private ArrayList<Tvshows> tvshowResults = new ArrayList<>();
    private ArrayList<Genres> genres = new ArrayList<>();
    private Context context;

    //-------------------------------------------------------------------------------------------
    //   Constructor
    //-------------------------------------------------------------------------------------------
    public TvshowAdapter(int typeLayout, Context context) {

        this.typeLayout = typeLayout;
        this.context = context;
    }

    //-------------------------------------------------------------------------------------------
    //   setData
    //-------------------------------------------------------------------------------------------
    public void setData(List<Tvshows> items) {
        tvshowResults.clear();
        tvshowResults.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //-------------------------------------------------------------------------------------------
    //   onCreateViewHolder
    //-------------------------------------------------------------------------------------------
    public TvshowAdapter.TvshowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView;
        genres.addAll(DataGenre.getDataGenre());

        switch (typeLayout) {
            //-- List Layout for On The Air
            case LIST_LAYOUT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_list_wide, parent, false);
                break;
            //-- Grid Layout for Top Rated TV Shows
            case GRID_LAYOUT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_grid, parent, false);
                break;
            default:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow_list_wide, parent, false);
                break;
        }
        return new TvshowAdapter.TvshowViewHolder(mView);
    }

    @Override
    //-------------------------------------------------------------------------------------------
    //   onBindViewHolder
    //-------------------------------------------------------------------------------------------
    public void onBindViewHolder(@NonNull TvshowAdapter.TvshowViewHolder tvshowViewHolder, final int position) {

        bindViewHolderByLayout(tvshowViewHolder, position, typeLayout);

        // Click event for Detail TV Show Activity
        tvshowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tvshows tvshows = new Tvshows();

                // Setup Data for Detail TV Show Activity, send with Parcelable
                tvshows.setId(tvshowResults.get(position).getId());
                tvshows.setPosterPath("http://image.tmdb.org/t/p/w500" + tvshowResults.get(position).getPosterPath());
                tvshows.setBackdropPath("http://image.tmdb.org/t/p/w500" + tvshowResults.get(position).getBackdropPath());
                tvshows.setName(tvshowResults.get(position).getName());
                tvshows.setGenre(UtilHelper.getGenres(tvshowResults.get(position).getGenreIds(), genres));
                tvshows.setFirstAirDate(tvshowResults.get(position).getFirstAirDate());
                tvshows.setPopularity(tvshowResults.get(position).getPopularity());
                tvshows.setVoteCount(tvshowResults.get(position).getVoteCount());
                tvshows.setOverview(tvshowResults.get(position).getOverview());

                Intent detailMovieIntent = new Intent(context.getApplicationContext(), DetailTvshowActivity.class);
                detailMovieIntent.putExtra(DetailTvshowActivity.EXTRA_TVSHOW, tvshows);
                context.startActivity(detailMovieIntent);
            }
        });
    }

    //-------------------------------------------------------------------------------------------
    //   getItemCount
    //-------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {

        return tvshowResults.size();
    }

    //-------------------------------------------------------------------------------------------
    //   TvshowViewHolder
    //-------------------------------------------------------------------------------------------
    class TvshowViewHolder extends RecyclerView.ViewHolder {

        TextView Name, AirDate, Genre, Popularity, VoteCount;
        ImageView PosterPath, BackdropPath;

        TvshowViewHolder(@NonNull View itemView) {
            super(itemView);

            switch (typeLayout) {
                // Items for Now Playing Tvshows
                case LIST_LAYOUT:
                    Name = itemView.findViewById(R.id.tv_wide_tvshow_name);
                    Genre = itemView.findViewById(R.id.tv_wide_tvshow_genre);
                    AirDate = itemView.findViewById(R.id.tv_list_tvshow_airdate);
                    BackdropPath = itemView.findViewById(R.id.iv_wide_tvshow_poster);
                    Popularity = itemView.findViewById(R.id.tv_list_tvshow_popularity);
                    VoteCount = itemView.findViewById(R.id.tv_list_tvshow_vote_count);
                    break;

                // Items for Top Rated Tvshows
                case GRID_LAYOUT:
                    Name = itemView.findViewById(R.id.tv_grid_tvshow_name);
                    Genre = itemView.findViewById(R.id.tv_grid_tvshow_genre);
                    PosterPath = itemView.findViewById(R.id.iv_grid_tvshow_poster);
                    break;
            }
        }
    }


    //-------------------------------------------------------------------------------------------
    //   Setup onbindViewHolder spesific by Type Layout (List or Grid)
    //-------------------------------------------------------------------------------------------
    private void bindViewHolderByLayout(TvshowViewHolder tvshowViewHolder, final int position, int typeLayout) {

        switch (typeLayout) {

            //-- List Layout for Now Playing Tvshows
            case LIST_LAYOUT:

                Glide.with(tvshowViewHolder.itemView.getContext())
                        .load("http://image.tmdb.org/t/p/w500" + tvshowResults.get(position).getBackdropPath())
                        .into(tvshowViewHolder.BackdropPath);

                tvshowViewHolder.Name.setText(tvshowResults.get(position).getName());
                tvshowViewHolder.Genre.setText(UtilHelper.getGenres(tvshowResults.get(position).getGenreIds(), genres));
                try {
                    tvshowViewHolder.AirDate.setText(UtilHelper.getDate(tvshowResults.get(position).getFirstAirDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvshowViewHolder.Popularity.setText(Double.toString(tvshowResults.get(position).getPopularity()));
                tvshowViewHolder.VoteCount.setText(Integer.toString(tvshowResults.get(position).getVoteCount()));
                break;

            //-- Grid Layout for Top Rated Tvshows
            case GRID_LAYOUT:

                Glide.with(tvshowViewHolder.itemView.getContext())
                        .load("http://image.tmdb.org/t/p/w185" + tvshowResults.get(position).getPosterPath())
                        .into(tvshowViewHolder.PosterPath);

                tvshowViewHolder.Name.setText(tvshowResults.get(position).getName());
                tvshowViewHolder.Genre.setText(UtilHelper.getGenres(tvshowResults.get(position).getGenreIds(), genres));
                break;
        }
    }

}
