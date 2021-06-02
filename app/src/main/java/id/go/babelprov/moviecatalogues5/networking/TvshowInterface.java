package id.go.babelprov.moviecatalogues5.networking;

import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvshowInterface {

    // Interface for On The Air TV Shows
    @GET("/3/tv/on_the_air")
    Call<TvshowsResponse> getOnTheAirTvshows(
            @Query("api_key") String api_key,
            @Query("language") String language);

    // Interface for Top Rated TV Shows
    @GET("/3/tv/top_rated")
    Call<TvshowsResponse> getTopRatedTvshows(
            @Query("api_key") String api_key,
            @Query("language") String language);

    // Interface for Search TV Shows
    @GET("/3/search/tv")
    Call<TvshowsResponse> getSearchTvshows(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("query") String query);
}
