package id.go.babelprov.moviecatalogues5.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import id.go.babelprov.moviecatalogues5.model.MoviesResponse;
import id.go.babelprov.moviecatalogues5.repository.DataRepository;


public class MovieSearchViewModel extends ViewModel {

    private MediatorLiveData<MoviesResponse> mediatorLiveDataMoviesResponse;
    private DataRepository moviesRepository;

    public MovieSearchViewModel() {
        mediatorLiveDataMoviesResponse = new MediatorLiveData<>();
        moviesRepository = new DataRepository();
    }

    public void setMovieSearch(String query) {
        mediatorLiveDataMoviesResponse.addSource(moviesRepository.getSearchMovies(query), new Observer<MoviesResponse>() {
            @Override
            public void onChanged(@Nullable MoviesResponse apiResponse) {
                mediatorLiveDataMoviesResponse.postValue(apiResponse);
            }
        });
    }

    public MutableLiveData<MoviesResponse> getMovieSearch() {
        return mediatorLiveDataMoviesResponse;
    }
}
