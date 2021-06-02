package id.go.babelprov.moviecatalogues5.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.MovieAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.MovieSearchViewModel;

public class SearchMoviesActivity extends AppCompatActivity {

    private SearchView searchView;
    public static MovieAdapter movieListAdapter;
    MovieSearchViewModel movieSearchViewModel;
    private static View view;

    // --------------------------------------------------------
    //       On Create View
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_search_movies);

         view = getWindow().getDecorView().findViewById(R.id.progressBar);

        // Set up button and title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.search_movies));
        }

        setupRecyclerView();

        movieSearchViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);
        movieSearchViewModel.getMovieSearch().observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse == null) {
                    // handle error
                    Toast.makeText(SearchMoviesActivity.this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (moviesResponse.getError() == null) {
                    movieListAdapter.setData(moviesResponse.getResults());
                } else {
                    // call failed.
                    Throwable e = moviesResponse.getError();
                    Toast.makeText(SearchMoviesActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // --------------------------------------------------------
    //       Fungsi untuk membuat menu search
    // --------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_movies, menu);

        MenuItem search = menu.findItem(R.id.search_movies);
        searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    // --------------------------------------------------------
    //       Fungsi untuk menghandle click back pada action bar
    // --------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // --------------------------------------------------------
    //       Fungsi SearchView OnQueryTextListener
    // --------------------------------------------------------
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            UtilHelper.showLoading(view, true);
            showMoviesData(query);
            searchView.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };

    // --------------------------------------------------------
    //       Fungsi Show Movies Data
    // --------------------------------------------------------
    private void showMoviesData(String query){

        movieSearchViewModel.setMovieSearch(query);
        movieSearchViewModel.getMovieSearch().observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse == null) {
                    Toast.makeText(SearchMoviesActivity.this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (moviesResponse.getError() == null) {
                    movieListAdapter.setData(moviesResponse.getResults());
                    UtilHelper.showLoading(view, false);
                } else {
                    Throwable e = moviesResponse.getError();
                    Toast.makeText(SearchMoviesActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------
    //   Setup Recyclerview
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        movieListAdapter = new MovieAdapter(1, SearchMoviesActivity.this);

        RecyclerView rvMovieList = findViewById(R.id.rv_movie_search_list);
        rvMovieList.setLayoutManager(new LinearLayoutManager(SearchMoviesActivity.this));
        rvMovieList.setAdapter(movieListAdapter);
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setNestedScrollingEnabled(true);
    }
}
