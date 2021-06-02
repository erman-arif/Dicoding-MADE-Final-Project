package id.go.babelprov.moviecatalogues5.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.model.TvshowsResponse;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;


public class TvshowSearchViewModel extends ViewModel {

    private MediatorLiveData<TvshowsResponse> mediatorLiveDataTvshowsResponse;
    private DataRepository tvshowsRepository;

    public TvshowSearchViewModel() {
        mediatorLiveDataTvshowsResponse = new MediatorLiveData<>();
        tvshowsRepository = new DataRepository();
    }

    public void setTvshowSearch(String query) {
        mediatorLiveDataTvshowsResponse.addSource(tvshowsRepository.getSearchTvshows(query), new Observer<TvshowsResponse>() {
            @Override
            public void onChanged(@Nullable TvshowsResponse apiResponse) {
                mediatorLiveDataTvshowsResponse.postValue(apiResponse);
            }
        });
    }

    public MutableLiveData<TvshowsResponse> getTvshowSearch() {
        return mediatorLiveDataTvshowsResponse;
    }
}
