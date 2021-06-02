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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.MovieAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.MovieNowPlayingViewModel;

import static id.go.babelprov.moviecatalogues5.view.FragmentMovies.movieListAdapter;


public class FragmentMoviesNowPlaying extends Fragment {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    MovieNowPlayingViewModel movieNowPlayingViewModel;


    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_movies_now_playing, container, false);

        setupRecyclerView();

        movieNowPlayingViewModel = ViewModelProviders.of(this).get(MovieNowPlayingViewModel.class);

        if (savedInstanceState == null) {
            movieNowPlayingViewModel.setMovieNowPlaying();
        }

        movieNowPlayingViewModel.getMovieNowPlaying().observe(this, moviesResponse -> {
            if (moviesResponse == null) {
                // handle error
                Toast.makeText(getActivity(), getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if (moviesResponse.getError() == null) {
                movieListAdapter.setData(moviesResponse.getResults());
                UtilHelper.showLoading(view, false);
            } else {
                // call failed.
                Throwable e = moviesResponse.getError();
                Toast.makeText(getActivity(), getString(R.string.conection_error), Toast.LENGTH_SHORT).show();
            }
        });

        UtilHelper.showLoading(view,true);

        return view;
    }

    //-------------------------------------------------------------------------------------------
    //   SETUP RECYCLERVIEW
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        movieListAdapter = new MovieAdapter(1, this.getContext());

        RecyclerView rvMovieList = view.findViewById(R.id.rv_movie_list);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieList.setAdapter(movieListAdapter);
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setNestedScrollingEnabled(true);
    }
}