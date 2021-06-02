package id.go.babelprov.moviecatalogues5.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;


public class MovieNowPlayingViewModel extends ViewModel {

    private MediatorLiveData<MoviesResponse> mediatorLiveDataMoviesResponse;
    private DataRepository moviesRepository;

    public MovieNowPlayingViewModel() {
        mediatorLiveDataMoviesResponse = new MediatorLiveData<>();
        moviesRepository = new DataRepository();
    }

    public void setMovieNowPlaying() {
        mediatorLiveDataMoviesResponse.addSource(moviesRepository.getMoviesNowPlaying(), new Observer<MoviesResponse>() {
            @Override
            public void onChanged(@Nullable MoviesResponse apiResponse) {
                mediatorLiveDataMoviesResponse.postValue(apiResponse);
            }
        });
    }

    public MutableLiveData<MoviesResponse> getMovieNowPlaying() {
        return mediatorLiveDataMoviesResponse;
    }
}
