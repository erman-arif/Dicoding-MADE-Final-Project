package id.go.babelprov.moviecatalogues5.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.go.babelprov.moviecatalogues5.BuildConfig;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.model.FavoriteDao;
import id.go.babelprov.moviecatalogues5.model.FavoriteRoomDatabase;
import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.networking.ApiServer;
import id.go.babelprov.moviecatalogues5.networking.MovieInterface;
import id.go.babelprov.moviecatalogues5.networking.TvshowInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.go.babelprov.moviecatalogues5.helper.UtilHelper.lang;


public class  DataRepository {

    private MovieInterface movieInterface;
    private TvshowInterface tvshowInterface;
    private FavoriteDao mFavoriteDao;
    private LiveData<List<Favorite>> mAllFavoriteMovies, mAllFavoriteTvshows, mFavoriteMovie;

    // ----------------------------------------------------------------------
    //   Constructor
    // ----------------------------------------------------------------------
    public DataRepository() {
        this.movieInterface = ApiServer.getService();
        this.tvshowInterface = ApiServer.getTvshowService();
    }

    public DataRepository(Application application) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        this.mFavoriteDao = db.favoriteDao();
        this.mAllFavoriteMovies = mFavoriteDao.getAllFavoriteMovies();
        this.mAllFavoriteTvshows = mFavoriteDao.getAllFavoriteTvshows();
    }

    // ----------------------------------------------------------------------
    //   Connecting to Now Playing Movies Data
    // ----------------------------------------------------------------------
    public LiveData<MoviesResponse> getMoviesNowPlaying() {

        final MutableLiveData<MoviesResponse> movieResponse = new MutableLiveData<>();
        Call<MoviesResponse> call = movieInterface.getNowPlayingMovies(BuildConfig.TMDB_API_KEY, lang);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    movieResponse.postValue(new MoviesResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                movieResponse.postValue(new MoviesResponse(t));
            }
        });

        return movieResponse;
    }

    // ----------------------------------------------------------------------
    //  Connecting to Top Rated Movies Data
    // ----------------------------------------------------------------------
    public LiveData<MoviesResponse> getMoviesTopRated() {

        final MutableLiveData<MoviesResponse> movieResponse = new MutableLiveData<>();
        Call<MoviesResponse> call = movieInterface.getTopRatedMovies(BuildConfig.TMDB_API_KEY, lang);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    movieResponse.postValue(new MoviesResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                movieResponse.postValue(new MoviesResponse(t));
            }
        });

        return movieResponse;
    }

    // ----------------------------------------------------------------------
    //   Connecting to On The Air TV Shows Data
    // ----------------------------------------------------------------------
    public LiveData<TvshowsResponse> getTvshowOnTheAir() {

        final MutableLiveData<TvshowsResponse> tvshowsResponse = new MutableLiveData<>();
        Call<TvshowsResponse> call = tvshowInterface.getOnTheAirTvshows(BuildConfig.TMDB_API_KEY, lang);

        call.enqueue(new Callback<TvshowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvshowsResponse> call, @NonNull Response<TvshowsResponse> response) {
                if (response.isSuccessful()) {
                    tvshowsResponse.postValue(new TvshowsResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvshowsResponse> call, @NonNull Throwable t) {
                tvshowsResponse.postValue(new TvshowsResponse(t));
            }
        });

        return tvshowsResponse;
    }

    // ----------------------------------------------------------------------
    //  Connecting to Top Rated TV Shows Data
    // ----------------------------------------------------------------------
    public LiveData<TvshowsResponse> getTvshowsTopRated() {

        final MutableLiveData<TvshowsResponse> tvshowsResponse = new MutableLiveData<>();
        Call<TvshowsResponse> call = tvshowInterface.getTopRatedTvshows(BuildConfig.TMDB_API_KEY, lang);

        call.enqueue(new Callback<TvshowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvshowsResponse> call, @NonNull Response<TvshowsResponse> response) {
                if (response.isSuccessful()) {
                    tvshowsResponse.postValue(new TvshowsResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvshowsResponse> call, @NonNull Throwable t) {
                tvshowsResponse.postValue(new TvshowsResponse(t));
            }
        });

        return tvshowsResponse;
    }

    // ----------------------------------------------------------------------
    //  Connecting to Search Movie
    // ----------------------------------------------------------------------
    public LiveData<MoviesResponse> getSearchMovies(String query) {

        final MutableLiveData<MoviesResponse> moviesResponse = new MutableLiveData<>();
        Call<MoviesResponse> call = movieInterface.getSearchMovies(BuildConfig.TMDB_API_KEY, lang, query);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesResponse.postValue(new MoviesResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                moviesResponse.postValue(new MoviesResponse(t));
            }
        });

        return moviesResponse;
    }

    // ----------------------------------------------------------------------
    //  Connecting to Search TV Shows
    // ----------------------------------------------------------------------
    public LiveData<TvshowsResponse> getSearchTvshows(String query) {

        final MutableLiveData<TvshowsResponse> tvshowsResponse = new MutableLiveData<>();
        Call<TvshowsResponse> call = tvshowInterface.getSearchTvshows(BuildConfig.TMDB_API_KEY, lang, query);

        call.enqueue(new Callback<TvshowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvshowsResponse> call, @NonNull Response<TvshowsResponse> response) {
                if (response.isSuccessful()) {
                    tvshowsResponse.postValue(new TvshowsResponse(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvshowsResponse> call, @NonNull Throwable t) {
                tvshowsResponse.postValue(new TvshowsResponse(t));
            }
        });

        return tvshowsResponse;
    }

    //----------------------------------
    // Get All Favorites Movies
    //----------------------------------
    public LiveData<List<Favorite>> getAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    //----------------------------------
    // Get All Favorites TV Shows
    //----------------------------------
    public LiveData<List<Favorite>> getAllFavoriteTvshows() {
        return mAllFavoriteTvshows;
    }

    //----------------------------------
    // Get a Favorites Movie by ID
    //----------------------------------
    public LiveData<List<Favorite>> findFavoriteById(int id) {
        mFavoriteMovie = mFavoriteDao.findFavoriteById(id);
        return mFavoriteMovie;
    }

    //----------------------------------
    // Insert Favorite Data
    //----------------------------------
    public void insert (Favorite favorite) {
        new insertAsyncTask(mFavoriteDao).execute(favorite);
    }

    //----------------------------------
    // Delete Favorite Data
    //----------------------------------
    public void delete (int id) {
        new deleteAsyncTask(mFavoriteDao).execute(id);
    }

    //----------------------------------
    // Insert AsyncTask
    //----------------------------------
    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {
        FavoriteDao mAsyncTaskDao;

        insertAsyncTask(FavoriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favorite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //----------------------------------
    // Delete AsyncTask
    //----------------------------------
    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        FavoriteDao mAsyncTaskDao;

        deleteAsyncTask(FavoriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteFavoriteMovie(params[0]);
            return null;
        }
    }
}
