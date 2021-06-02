package id.go.babelprov.moviecatalogues5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MoviesResponse {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    @SerializedName("results")
    @Expose
    private List<Movies> results = null;

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    @SerializedName("dates")
    @Expose
    private Dates dates;

    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    private Throwable error;

    //-------------------------------------------------------------------------------------------
    //   CONSTRUCTOR
    //-------------------------------------------------------------------------------------------
    public MoviesResponse(List<Movies> results) {
        this.results = results;
        this.error = null;
    }

    public MoviesResponse(Throwable error) {
        this.results = null;
        this.error = error;
    }


    //-------------------------------------------------------------------------------------------
    //   GETTER & SETTER
    //-------------------------------------------------------------------------------------------
    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
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

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
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
