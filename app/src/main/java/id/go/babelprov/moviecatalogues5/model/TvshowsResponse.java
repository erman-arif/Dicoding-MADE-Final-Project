package id.go.babelprov.moviecatalogues5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvshowsResponse {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    @SerializedName("results")
    @Expose
    private List<Tvshows> results = null;

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    private Throwable error;

    //-------------------------------------------------------------------------------------------
    //   CONSTRUCTOR
    //-------------------------------------------------------------------------------------------
    public TvshowsResponse(List<Tvshows> results) {
        this.results = results;
        this.error = null;
    }

    public TvshowsResponse(Throwable error) {
        this.results = null;
        this.error = error;
    }

    //-------------------------------------------------------------------------------------------
    //   GETTER & SETTER
    //-------------------------------------------------------------------------------------------
    public List<Tvshows> getResults() {
        return results;
    }

    public void setResults(List<Tvshows> results) {
        this.results = results;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
