package id.go.babelprov.moviecatalogues5.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import id.go.babelprov.moviecatalogues5.model.Movies;
import id.go.babelprov.moviecatalogues5.view.DetailMovieActivity;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    //-------------------------------------------------------------------------------------------
    //   Declare Properties
    //-------------------------------------------------------------------------------------------
    private static final int LIST_LAYOUT = 1;
    private static final int GRID_LAYOUT = 2;
    private int typeLayout;
    private ArrayList<Movies> movieResults = new ArrayList<>();
    private ArrayList<Genres> genres = new ArrayList<>();
    private Context context;

    //-------------------------------------------------------------------------------------------
    //   Constructor
    //-------------------------------------------------------------------------------------------
    public MovieAdapter(int typeLayout, Context context) {

        this.typeLayout = typeLayout;
        this.context = context;
    }

    //-------------------------------------------------------------------------------------------
    //   setData
    //-------------------------------------------------------------------------------------------
    public void setData(List<Movies> items) {
        movieResults.clear();
        movieResults.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //-------------------------------------------------------------------------------------------
    //   onCreateViewHolder
    //-------------------------------------------------------------------------------------------
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView;
        genres.addAll(DataGenre.getDataGenre());

        switch (typeLayout) {
            //-- List Layout for Now Playing Movies
            case LIST_LAYOUT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list_wide, parent, false);
                break;
            //-- Grid Layout for Top Rated Movies
            case GRID_LAYOUT:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_grid, parent, false);
                break;
            default:
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list_wide, parent, false);
                break;
        }
        return new MovieAdapter.MovieViewHolder(mView);
    }

    @Override
    //-------------------------------------------------------------------------------------------
    //   onBindViewHolder
    //-------------------------------------------------------------------------------------------
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder movieViewHolder, final int position) {

        bindViewHolderByLayout(movieViewHolder, position, typeLayout);

        // Click event for Detail Mivie Activity
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movies movies = new Movies();

                // Setup Data for Detail Movie Activity, send with Parcelable
                movies.setId(movieResults.get(position).getId());
                movies.setPosterPath("http://image.tmdb.org/t/p/w500" + movieResults.get(position).getPosterPath());
                movies.setBackdropPath("http://image.tmdb.org/t/p/w500" + movieResults.get(position).getBackdropPath());
                movies.setTitle(movieResults.get(position).getTitle());
                movies.setGenre(UtilHelper.getGenres(movieResults.get(position).getGenreIds(), genres));
                movies.setReleaseDate(movieResults.get(position).getReleaseDate());
                movies.setPopularity(movieResults.get(position).getPopularity());
                movies.setVoteCount(movieResults.get(position).getVoteCount());
                movies.setOverview(movieResults.get(position).getOverview());

                Intent detailMovieIntent = new Intent(context.getApplicationContext(), DetailMovieActivity.class);
                detailMovieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
                context.startActivity(detailMovieIntent);
            }
        });
    }

    //-------------------------------------------------------------------------------------------
    //   getItemCount
    //-------------------------------------------------------------------------------------------
    @Override
    public int getItemCount() {

        return movieResults.size();
    }

    //-------------------------------------------------------------------------------------------
    //   MovieViewHolder
    //-------------------------------------------------------------------------------------------
    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView Title, ReleaseDate, Genre, Popularity, VoteCount;
        ImageView PosterPath, BackdropPath;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            switch (typeLayout) {
                // Items for Now Playing Movies
                case LIST_LAYOUT:
                    Title = itemView.findViewById(R.id.tv_wide_movie_title);
                    Genre = itemView.findViewById(R.id.tv_wide_movie_genre);
                    ReleaseDate = itemView.findViewById(R.id.tv_list_movie_release);
                    BackdropPath = itemView.findViewById(R.id.iv_wide_movie_poster);
                    Popularity = itemView.findViewById(R.id.tv_list_movie_popularity);
                    VoteCount = itemView.findViewById(R.id.tv_list_movie_vote_count);
                    break;

                // Items for Top Rated Movies
                case GRID_LAYOUT:
                    Title = itemView.findViewById(R.id.tv_grid_movie_title);
                    Genre = itemView.findViewById(R.id.tv_grid_movie_genre);
                    PosterPath = itemView.findViewById(R.id.iv_grid_movie_poster);
                    break;
            }
        }
    }


    //-------------------------------------------------------------------------------------------
    //   Setup onbindViewHolder spesific by Type Layout (List or Grid)
    //-------------------------------------------------------------------------------------------
    private void bindViewHolderByLayout(MovieViewHolder movieViewHolder, final int position, int typeLayout) {

        switch (typeLayout) {

            //-- List Layout for Now Playing Movies
            case LIST_LAYOUT:

                Glide.with(movieViewHolder.itemView.getContext())
                        .load("http://image.tmdb.org/t/p/w500" + movieResults.get(position).getBackdropPath())
                        .into(movieViewHolder.BackdropPath);

                movieViewHolder.Title.setText(movieResults.get(position).getTitle());
                movieViewHolder.Genre.setText(UtilHelper.getGenres(movieResults.get(position).getGenreIds(), genres));
                try {
                    movieViewHolder.ReleaseDate.setText(UtilHelper.getDate(movieResults.get(position).getReleaseDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                movieViewHolder.Popularity.setText(Double.toString(movieResults.get(position).getPopularity()));
                movieViewHolder.VoteCount.setText(Integer.toString(movieResults.get(position).getVoteCount()));
                break;

            //-- Grid Layout for Top Rated Movies
            case GRID_LAYOUT:

                Glide.with(movieViewHolder.itemView.getContext())
                        .load("http://image.tmdb.org/t/p/w185" + movieResults.get(position).getPosterPath())
                        .into(movieViewHolder.PosterPath);

                movieViewHolder.Title.setText(movieResults.get(position).getTitle());
                movieViewHolder.Genre.setText(UtilHelper.getGenres(movieResults.get(position).getGenreIds(), genres));
                break;
        }
    }

    @Override
    public Filter getFilter() {
        return moviesFilter;
    }

    private Filter moviesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    };
}
