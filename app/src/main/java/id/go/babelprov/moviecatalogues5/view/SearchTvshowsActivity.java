package id.go.babelprov.moviecatalogues5.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.TvshowAdapter;
import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.TvshowSearchViewModel;

public class SearchTvshowsActivity extends AppCompatActivity {

    private SearchView searchView;
    public static TvshowAdapter tvshowListAdapter;
    TvshowSearchViewModel tvshowSearchViewModel;

    // --------------------------------------------------------
    //       On Create View
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tvshows);

        // Set up button and title
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.search_tvshows));
        }

        setupRecyclerView();

        tvshowSearchViewModel = ViewModelProviders.of(this).get(TvshowSearchViewModel.class);
        tvshowSearchViewModel.getTvshowSearch().observe(this, new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(TvshowsResponse tvshowsResponse) {
                if (tvshowsResponse == null) {
                    // handle error
                    Toast.makeText(SearchTvshowsActivity.this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvshowsResponse.getError() == null) {
                    tvshowListAdapter.setData(tvshowsResponse.getResults());
                } else {
                    // call failed.
                    Throwable e = tvshowsResponse.getError();
                    Toast.makeText(SearchTvshowsActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
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
        inflater.inflate(R.menu.menu_search_tvshows, menu);

        MenuItem search = menu.findItem(R.id.search_tvshows);
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
            showTvshowsData(query);
            searchView.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };

    // --------------------------------------------------------
    //       Fungsi Show TV Shows Data
    // --------------------------------------------------------
    private void showTvshowsData(String query){

        tvshowSearchViewModel.setTvshowSearch(query);
        tvshowSearchViewModel.getTvshowSearch().observe(this, new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(TvshowsResponse tvshowsResponse) {
                if (tvshowsResponse == null) {
                    Toast.makeText(SearchTvshowsActivity.this, getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvshowsResponse.getError() == null) {
                    tvshowListAdapter.setData(tvshowsResponse.getResults());
                } else {
                    Throwable e = tvshowsResponse.getError();
                    Toast.makeText(SearchTvshowsActivity.this, getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------
    //   Setup Recyclerview
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        tvshowListAdapter = new TvshowAdapter(1, SearchTvshowsActivity.this);

        RecyclerView rvTvshowList = findViewById(R.id.rv_tvshow_search_list);
        rvTvshowList.setLayoutManager(new LinearLayoutManager(SearchTvshowsActivity.this));
        rvTvshowList.setAdapter(tvshowListAdapter);
        rvTvshowList.setItemAnimator(new DefaultItemAnimator());
        rvTvshowList.setNestedScrollingEnabled(true);
    }
}
