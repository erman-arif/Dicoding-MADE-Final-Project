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
import id.go.babelprov.moviecatalogues5.adapter.TvshowAdapter;
import id.go.babelprov.moviecatalogues5.helper.UtilHelper;
import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.viewmodel.TvshowOnTheAirViewModel;


public class FragmentTvshowsOnTheAir extends Fragment {


    //-------------------------------------------------------------------------------------------
    //   DECLARE PROPERTIES
    //-------------------------------------------------------------------------------------------
    View view;
    TvshowAdapter tvshowAdapter;
    TvshowOnTheAirViewModel tvshowOnTheAirViewModel;


    //-------------------------------------------------------------------------------------------
    //   ON CREATE VIEW
    //-------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tvshows_on_the_air, container, false);

        setupRecyclerView();

        tvshowOnTheAirViewModel = ViewModelProviders.of(this).get(TvshowOnTheAirViewModel.class);

        if (savedInstanceState == null) {
            tvshowOnTheAirViewModel.setTvshowOnTheAir();
        }

        tvshowOnTheAirViewModel.getTvshowOnTheAir().observe(this, new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(TvshowsResponse tvshowsOnTheAirResponse) {
                if (tvshowsOnTheAirResponse == null) {
                    // handle error
                    Toast.makeText(getActivity(), getString(R.string.data_empty), Toast.LENGTH_SHORT).show();
                    UtilHelper.showLoading(view, false);
                    return;
                }

                if (tvshowsOnTheAirResponse.getError() == null) {
                    tvshowAdapter.setData(tvshowsOnTheAirResponse.getResults());
                    UtilHelper.showLoading(view, false);

                } else {
                    // call failed.
                    Throwable e = tvshowsOnTheAirResponse.getError();
                    Toast.makeText(getActivity(), getString(R.string.conection_error) + "(" + e + ")", Toast.LENGTH_SHORT).show();
                }
            }
        });

        UtilHelper.showLoading(view, true);

        return view;
    }

    //-------------------------------------------------------------------------------------------
    //   SETUP RECYCLERVIEW
    //-------------------------------------------------------------------------------------------
    private void setupRecyclerView() {

        tvshowAdapter = new TvshowAdapter(1, this.getContext());

        RecyclerView rvTvshowList = view.findViewById(R.id.rv_tvshow_list);
        rvTvshowList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTvshowList.setAdapter(tvshowAdapter);
        rvTvshowList.setItemAnimator(new DefaultItemAnimator());
        rvTvshowList.setNestedScrollingEnabled(true);
    }

}
