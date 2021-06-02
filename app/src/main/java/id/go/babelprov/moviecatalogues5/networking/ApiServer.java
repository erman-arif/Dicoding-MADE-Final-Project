package id.go.babelprov.moviecatalogues5.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiServer {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static MovieInterface movieInterface;
    private static TvshowInterface tvshowInterface;

    public static MovieInterface getService() {
        if (movieInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

            movieInterface = retrofit.create(MovieInterface.class);
        }

        return movieInterface;
    }

    public static TvshowInterface getTvshowService() {
        if (tvshowInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

            tvshowInterface = retrofit.create(TvshowInterface.class);
        }

        return tvshowInterface;
    }
}
