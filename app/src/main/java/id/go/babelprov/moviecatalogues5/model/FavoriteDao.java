package id.go.babelprov.moviecatalogues5.model;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("SELECT * FROM favorite_table WHERE type = 'movie'")
    LiveData<List<Favorite>> getAllFavoriteMovies();

    @Query("SELECT * FROM favorite_table WHERE type = 'movie'")
    List<Favorite> getAllFavoriteMoviesList();

    @Query("SELECT * FROM favorite_table WHERE type = 'tvshow'")
    LiveData<List<Favorite>> getAllFavoriteTvshows();

    @Query("DELETE FROM favorite_table")
    void deleteAll();

    @Query("DELETE FROM favorite_table WHERE id = :id")
    void deleteFavoriteMovie(Integer id);

    @Query("SELECT * FROM favorite_table WHERE id = :id LIMIT 1")
    LiveData<List<Favorite>> findFavoriteById(int id);

    //------------------------------------------------------------
    // Query for ContentProvider
    //------------------------------------------------------------

    @Query("SELECT * FROM favorite_table WHERE type = 'movie'")
    Cursor getCursorAllFavoriteMovies();

    @Query("SELECT * FROM favorite_table WHERE id = :id LIMIT 1")
    Cursor findCursorFavoriteMovieById(long id);

    @Query("SELECT * FROM favorite_table WHERE type = 'tvshow'")
    Cursor getCursorAllFavoriteTvshows();

    @Query("SELECT * FROM favorite_table WHERE id = :id LIMIT 1")
    Cursor findCursorFavoriteTvshowById(long id);
}
