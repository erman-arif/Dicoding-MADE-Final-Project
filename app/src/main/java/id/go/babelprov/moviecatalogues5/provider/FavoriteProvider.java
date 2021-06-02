package id.go.babelprov.moviecatalogues5.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.model.FavoriteDao;
import id.go.babelprov.moviecatalogues5.model.FavoriteRoomDatabase;

public class FavoriteProvider extends ContentProvider {

    public static final String AUTHORITY = "id.go.babelprov.moviecatalogues5";

    private static final int FAV_MOVIE_ALL = 10;
    private static final int FAV_MOVIE_ID = 11;

    private static final int FAV_TVSHOW_ALL = 20;
    private static final int FAV_TVSHOW_ID = 21;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, Favorite.DATA_MOVIE, FAV_MOVIE_ALL);
        sUriMatcher.addURI(AUTHORITY, Favorite.DATA_MOVIE + "/#", FAV_MOVIE_ID);

        sUriMatcher.addURI(AUTHORITY, Favorite.DATA_TVSHOW, FAV_TVSHOW_ALL);
        sUriMatcher.addURI(AUTHORITY, Favorite.DATA_TVSHOW + "/#", FAV_TVSHOW_ID);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final int code = sUriMatcher.match(uri);
        if (code == FAV_MOVIE_ALL || code == FAV_MOVIE_ID || code == FAV_TVSHOW_ALL || code == FAV_TVSHOW_ID) {

            final Context context = getContext();
            if (context == null) {
                return null;
            }

            FavoriteDao favorite = FavoriteRoomDatabase.getDatabase(context).favoriteDao();
            final Cursor cursor;

            switch (code) {
                case FAV_MOVIE_ALL:
                    cursor = favorite.getCursorAllFavoriteMovies();
                    break;
                case FAV_MOVIE_ID:
                    cursor = favorite.findCursorFavoriteMovieById(ContentUris.parseId(uri));
                    break;
                case FAV_TVSHOW_ALL:
                    cursor = favorite.getCursorAllFavoriteTvshows();
                    break;
                case FAV_TVSHOW_ID:
                    cursor = favorite.findCursorFavoriteTvshowById(ContentUris.parseId(uri));
                    break;
                default:
                    cursor = null;
                    break;
            }

            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
