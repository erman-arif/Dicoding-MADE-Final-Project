package id.go.babelprov.moviecatalogues5.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.go.babelprov.moviecatalogues5.R;
import id.go.babelprov.moviecatalogues5.adapter.FavoriteMovieListAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.viewmodel.FavoriteViewModel;


public class FragmentMoviesFavorite extends Fragment {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    FavoriteMovieListAdapter favoriteMovieListAdapter;
    FavoriteViewModel favoriteMoviesViewModel;

    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_movies_favorite, container, false);

        setupRecyclerView();

        favoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        if (savedInstanceState == null) {
            favoriteMoviesViewModel.setAllFavoriteMovies();
        }

        favoriteMoviesViewModel.getAllFavoriteMovies().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                favoriteMovieListAdapter.setFavorites(favorites);
                UtilHelper.showLoading(view, false);
            }
        });

        UtilHelper.showLoading(view,true);

        return view;
    }

    //-------------------------------------------------------------------------------------------
    //   SETUP RECYCLERVIEW
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        favoriteMovieListAdapter = new FavoriteMovieListAdapter(this.getContext());

        RecyclerView rvMovieList = view.findViewById(R.id.rv_movie_favorite_list);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovieList.setAdapter(favoriteMovieListAdapter);
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setNestedScrollingEnabled(true);
    }

}
