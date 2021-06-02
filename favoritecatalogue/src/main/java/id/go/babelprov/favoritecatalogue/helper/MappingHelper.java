package id.go.babelprov.favoritecatalogue.helper;

import android.database.Cursor;

import java.util.ArrayList;

import id.go.babelprov.favoritecatalogue.model.Favorite;

import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.BACKDROPPATH;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.DATE;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.GENRE;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.ID;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.OVERVIEW;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.POPULARITY;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.POSTERPATH;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.TITLE;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.TYPE;
import static id.go.babelprov.favoritecatalogue.model.DbContract.FavoriteColumns.VOTE_COUNT;

public class MappingHelper {

    //-------------------------------------------------------------
    // Mapping Cursor data to ArrayList
    //-------------------------------------------------------------
    public static ArrayList<Favorite> mapCursorToArrayList(Cursor favMoviesCursor) {

        ArrayList<Favorite> favMoviesList = new ArrayList<>();

        while (favMoviesCursor.moveToNext()) {

            int id = favMoviesCursor.getInt(favMoviesCursor.getColumnIndexOrThrow(ID));
            String title = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(TITLE));
            String genre = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(GENRE));
            Double popularity = favMoviesCursor.getDouble(favMoviesCursor.getColumnIndexOrThrow(POPULARITY));
            Integer voteCount = favMoviesCursor.getInt(favMoviesCursor.getColumnIndexOrThrow(VOTE_COUNT));
            String date = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(DATE));
            String overview = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(OVERVIEW));
            String posterPath = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(POSTERPATH));
            String backdropPath = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(BACKDROPPATH));
            String type = favMoviesCursor.getString(favMoviesCursor.getColumnIndexOrThrow(TYPE));

            favMoviesList.add(new Favorite(id, title, genre, popularity, voteCount, date, overview, backdropPath, posterPath, type));
        }

        return favMoviesList;
    }
}
