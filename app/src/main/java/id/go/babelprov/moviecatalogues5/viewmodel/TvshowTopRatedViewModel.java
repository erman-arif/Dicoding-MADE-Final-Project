package id.go.babelprov.moviecatalogues5.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;

public class TvshowTopRatedViewModel extends ViewModel {

    private MediatorLiveData<TvshowsResponse> mediatorLiveDataTvshowsResponse;
    private DataRepository tvshowsRepository;

    public TvshowTopRatedViewModel() {
        mediatorLiveDataTvshowsResponse = new MediatorLiveData<>();
        tvshowsRepository = new DataRepository();
    }

    public void setTvshowTopRated() {
        mediatorLiveDataTvshowsResponse.addSource(tvshowsRepository.getTvshowsTopRated(), new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(@Nullable TvshowsResponse tvshowsResponse) {
                mediatorLiveDataTvshowsResponse.setValue(tvshowsResponse);
            }
        });
    }


    public MutableLiveData<TvshowsResponse> getTvshowTopRated() {
        return mediatorLiveDataTvshowsResponse;
    }
}
