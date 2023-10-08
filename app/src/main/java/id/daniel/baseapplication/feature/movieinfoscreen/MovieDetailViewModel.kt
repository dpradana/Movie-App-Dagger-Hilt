package id.daniel.baseapplication.feature.movieinfoscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.daniel.baseapplication.datasource.repository.MovieRepository
import id.daniel.baseapplication.model.MovieDetail
import id.daniel.baseapplication.model.MovieReview
import id.daniel.baseapplication.model.TopRatedMovies
import id.daniel.baseapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _responseDetail: MutableLiveData<Result<MovieDetail>> = MutableLiveData()
    val responseDetail: LiveData<Result<MovieDetail>> = _responseDetail

    private val _responseReviews: MutableLiveData<Result<MovieReview>> = MutableLiveData()
    val responseReviews: LiveData<Result<MovieReview>> = _responseReviews

    var page: Int = 1

    fun fetchDetailMovie(movieId: Int) = viewModelScope.launch {
        repository.getDetailMovie(movieId).collect { values ->
            _responseDetail.value = values
        }
    }

    fun fetchMovieReviews(movieId: Int, isReset: Boolean = false) = viewModelScope.launch {
        if (isReset) {
            page = 1
        } else {
            page++
        }
        repository.getDetailReviews(movieId, page).collect { values ->
            _responseReviews.value = values
        }
    }
}