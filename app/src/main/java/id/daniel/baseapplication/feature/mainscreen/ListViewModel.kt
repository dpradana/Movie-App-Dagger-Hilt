package id.daniel.baseapplication.feature.mainscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.daniel.baseapplication.datasource.repository.MovieRepository
import id.daniel.baseapplication.model.TopRatedMovies
import id.daniel.baseapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: MovieRepository,
    application: Application
) : AndroidViewModel(application) {
    private val _response: MutableLiveData<Result<TopRatedMovies>> = MutableLiveData()
    val response: LiveData<Result<TopRatedMovies>> = _response

    var page: Int = 1

    init {
        fetchListMoviesResponse(true)
    }

    fun fetchListMoviesResponse(isReset: Boolean = false) = viewModelScope.launch {
        if (isReset) {
            page = 1
        } else {
            page++
        }
        repository.getListMovies(page).collect { values ->
            _response.value = values
        }
    }
}