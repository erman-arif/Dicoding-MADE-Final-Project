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
import id.go.babelprov.moviecatalogues5.adapter.FavoriteTvshowListAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.Favorite;
import id.go.babelprov.moviecatalogues5.viewmodel.FavoriteViewModel;


public class FragmentTvshowsFavorite extends Fragment {

    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    FavoriteTvshowListAdapter favoriteTvshowListAdapter;
    FavoriteViewModel favoriteTvshowsViewModel;

    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_tvshows_favorite, container, false);

        setupRecyclerView();

        favoriteTvshowsViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        if (savedInstanceState == null) {
            favoriteTvshowsViewModel.setAllFavoriteTvshows();
        }

        favoriteTvshowsViewModel.getAllFavoriteTvshows().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                favoriteTvshowListAdapter.setFavorites(favorites);
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

        favoriteTvshowListAdapter = new FavoriteTvshowListAdapter(this.getContext());

        RecyclerView rvTvshowList = view.findViewById(R.id.rv_tvshow_favorite_list);
        rvTvshowList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvshowList.setAdapter(favoriteTvshowListAdapter);
        rvTvshowList.setItemAnimator(new DefaultItemAnimator());
        rvTvshowList.setNestedScrollingEnabled(true);
    }
}
