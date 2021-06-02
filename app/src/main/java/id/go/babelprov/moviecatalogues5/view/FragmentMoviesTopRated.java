package id.go.babelprov.moviecatalogues5.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.MovieAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.MovieTopRatedViewModel;


public class FragmentMoviesTopRated extends Fragment {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    MovieAdapter movieGridAdapter;
    MovieTopRatedViewModel movieTopRatedViewModel;


    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_movies_top_rated, container, false);

        setupRecyclerView();

        movieTopRatedViewModel = ViewModelProviders.of(this).get(MovieTopRatedViewModel.class);

        if (savedInstanceState == null) {
            movieTopRatedViewModel.setMovieTopRated();
        }

        movieTopRatedViewModel.getMovieTopRated().observe(this, new Observer<MoviesResponse>() {
            @Override
            public void onChanged(MoviesResponse moviesResponse) {
                if (moviesResponse == null) {
                    // handle error
                    Toast.makeText(getActivity(), getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (moviesResponse.getError() == null) {
                    movieGridAdapter.setData(moviesResponse.getResults());
                    UtilHelper.showLoading(view, false);

                } else {
                    // call failed.
                    Throwable e = moviesResponse.getError();
                    Toast.makeText(getActivity(), getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        UtilHelper.showLoading(view,true);

        return view;
    }


    //-------------------------------------------------------------------------------------------
    //   SETUP RECYCLERVIEW
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        movieGridAdapter = new MovieAdapter(2, this.getContext());

        RecyclerView rvMovieGrid = view.findViewById(R.id.rv_movie_grid);
        rvMovieGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvMovieGrid.setAdapter(movieGridAdapter);
        rvMovieGrid.setItemAnimator(new DefaultItemAnimator());
        rvMovieGrid.setNestedScrollingEnabled(true);
    }
}
