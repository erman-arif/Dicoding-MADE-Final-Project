package id.go.babelprov.moviecatalogues5.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;


public class FavoriteViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<List<Favorite>> mFavorite, mAllFavoriteMovies, mAllFavoriteTvshows;
    private List<Favorite> mAllFavoriteMoviesx;

    //----------------------------------
    // Constructor
    //----------------------------------
    public FavoriteViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
    }

    //----------------------------------
    // Set All Favorites Movies
    //----------------------------------
    public void setAllFavoriteMovies() {
        mAllFavoriteMovies = mRepository.getAllFavoriteMovies();
    }

    //----------------------------------
    // Set All Favorites TV Shows
    //----------------------------------
    public void setAllFavoriteTvshows() {
        mAllFavoriteTvshows = mRepository.getAllFavoriteTvshows();
    }

    //----------------------------------
    // Get All Favorites Movies
    //----------------------------------
    public LiveData<List<Favorite>> getAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    public List<Favorite> getAllFavoriteMoviesx() {
        return mAllFavoriteMoviesx;
    }

    //----------------------------------
    // Get All Favorites TV Shows
    //----------------------------------
    public LiveData<List<Favorite>> getAllFavoriteTvshows() {
        return mAllFavoriteTvshows;
    }

    //----------------------------------
    // Insert Favorite Movie/TV Shows
    //----------------------------------
    public void insert(Favorite favorite) {
        mRepository.insert(favorite);
    }

    //----------------------------------
    // Delete Favorite Movie/TV Shows
    //----------------------------------
    public void delete(Integer id) {
        mRepository.delete(id);
    }

    //----------------------------------
    // Find Favorite Movie/TV Shows by ID
    //----------------------------------
    public LiveData<List<Favorite>> findFavoriteById(int id) {
        mFavorite = mRepository.findFavoriteById(id);
        return mFavorite;
    }
}
