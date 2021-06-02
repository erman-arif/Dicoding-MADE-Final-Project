package id.go.babelprov.favoritecatalogue.model;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {

    public static final String AUTHORITY = "id.go.babelprov.moviecatalogues5";
    private static final String SCHEME = "content";


    public static final class FavoriteColumns implements BaseColumns {

        public static final String FAV_MOVIE = "favorite_movie";
        public static final String FAV_TVSHOW = "favorite_tvshow";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String GENRE = "genre";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_COUNT = "votecount";
        public static final String DATE = "date";
        public static final String OVERVIEW = "overview";
        public static final String BACKDROPPATH = "backdropPath";
        public static final String POSTERPATH = "posterPath";
        public static final String TYPE = "type";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAV_MOVIE)
                .build();

        public static final Uri CONTENT_URI_TVSHOW = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAV_TVSHOW)
                .build();
    }
}
