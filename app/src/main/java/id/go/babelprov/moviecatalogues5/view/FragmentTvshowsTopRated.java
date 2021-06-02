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
import id.go.babelprov.moviecatalogues5.adapter.TvshowAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.TvshowTopRatedViewModel;


public class FragmentTvshowsTopRated extends Fragment {
    
    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    TvshowAdapter tvshowGridAdapter;
    TvshowTopRatedViewModel tvshowTopRatedViewModel;

    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_tvshows_top_rated, container, false);

        setupRecyclerView();

        tvshowTopRatedViewModel = ViewModelProviders.of(this).get(TvshowTopRatedViewModel.class);

        if (savedInstanceState == null) {
            tvshowTopRatedViewModel.setTvshowTopRated();
        }

        tvshowTopRatedViewModel.getTvshowTopRated().observe(this, new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(TvshowsResponse tvshowsTopRatedResponse) {
                if (tvshowsTopRatedResponse == null) {
                    // handle error
                    Toast.makeText(getActivity(), getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    UtilHelper.showLoading(view, false);
                    return;
                }

                if (tvshowsTopRatedResponse.getError() == null) {
                    tvshowGridAdapter.setData(tvshowsTopRatedResponse.getResults());
                    UtilHelper.showLoading(view, false);

                } else {
                    // call failed.
                    Throwable e = tvshowsTopRatedResponse.getError();
                    Toast.makeText(getActivity(), getString(R.string.conection_error) + "(" + e + ")", Toast.LENGTH_SHORT).show();
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

        tvshowGridAdapter = new TvshowAdapter(2, this.getContext());

        RecyclerView rvTvshowGrid = view.findViewById(R.id.rv_tvshow_grid);
        rvTvshowGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvTvshowGrid.setAdapter(tvshowGridAdapter);
        rvTvshowGrid.setItemAnimator(new DefaultItemAnimator());
        rvTvshowGrid.setNestedScrollingEnabled(true);
    }

}
