package id.go.babelprov.moviecatalogues5.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;


public class MovieTopRatedViewModel extends ViewModel {

    private MediatorLiveData<MoviesResponse> mediatorLiveDataMoviesResponse;
    private DataRepository moviesRepository;

    public MovieTopRatedViewModel() {
        mediatorLiveDataMoviesResponse = new MediatorLiveData<>();
        moviesRepository = new DataRepository();
    }

    public void setMovieTopRated() {
        mediatorLiveDataMoviesResponse.addSource(moviesRepository.getMoviesTopRated(), new Observer<MoviesResponse>() {
            @Override
            public void onChanged(@Nullable MoviesResponse apiResponse){
                mediatorLiveDataMoviesResponse.setValue(apiResponse);
            }
        });
    }

    public MutableLiveData<MoviesResponse> getMovieTopRated() {
        return mediatorLiveDataMoviesResponse;
    }
}
