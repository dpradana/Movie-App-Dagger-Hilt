package id.daniel.baseapplication.datasource.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import id.daniel.baseapplication.base.BaseApiResponse
import id.daniel.baseapplication.datasource.RemoteDataSource
import id.daniel.baseapplication.model.MovieDetail
import id.daniel.baseapplication.model.MovieReview
import id.daniel.baseapplication.model.TopRatedMovies
import id.daniel.baseapplication.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {
    suspend fun getListMovies(page: Int): Flow<Result<TopRatedMovies>> {
        return flow<Result<TopRatedMovies>> {
            emit(safeApiCall { remoteDataSource.getTopRatedMovies(page) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(movieId: Int): Flow<Result<MovieDetail>> {
        return flow<Result<MovieDetail>> {
            emit(safeApiCall { remoteDataSource.getMovieDetail(movieId) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailReviews(movieId: Int, page: Int): Flow<Result<MovieReview>> {
        return flow<Result<MovieReview>> {
            emit(safeApiCall { remoteDataSource.getMovieReviews(movieId, page) })
        }.flowOn(Dispatchers.IO)
    }
}