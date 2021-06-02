package id.go.babelprov.moviecatalogues5.networking;

import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieInterface {

    // Interface for Now Playing Movie
    @GET("/3/movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(
            @Query("api_key") String api_key,
            @Query("language") String language);

    // Interface for Top Rated Movie
    @GET("/3/movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("api_key") String api_key,
            @Query("language") String language);

    // Interface for Search Movie
    @GET("/3/search/movie")
    Call<MoviesResponse> getSearchMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query);

    // Interface for Release Today Movie
    @GET("/3/discover/movie")
    Call<MoviesResponse> getReleaseTodayMovies(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("primary_release_date.gte") String release_date_gte,
            @Query("primary_release_date.lte") String release_date_lte);
}
