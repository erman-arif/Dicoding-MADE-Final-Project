package id.go.babelprov.favoritecatalogue.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.go.babelprov.favoritecatalogue.model.Favorite;
import id.go.babelprov.favoritecatalogue.model.FavoriteDao;
import id.go.babelprov.favoritecatalogue.model.FavoriteRoomDatabase;


public class DataRepository {

    private FavoriteDao mFavoriteDao;
    private LiveData<List<Favorite>> mAllFavoriteMovies, mAllFavoriteTvshows, mFavoriteMovie;

    // ----------------------------------------------------------------------
    //   Constructor
    // ----------------------------------------------------------------------
    public DataRepository() { }
    public DataRepository(Application application) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        this.mFavoriteDao = db.favoriteDao();
        this.mAllFavoriteMovies = mFavoriteDao.getAllFavoriteMovies();
        this.mAllFavoriteTvshows = mFavoriteDao.getAllFavoriteTvshows();
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
