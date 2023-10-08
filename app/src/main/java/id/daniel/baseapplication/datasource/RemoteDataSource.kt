package id.daniel.baseapplication.datasource

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieService: MovieApi) {
    suspend fun getTopRatedMovies(page: Int) =
        movieService.getTopRatedMovies(page, "en-US")

    suspend fun getMovieDetail(movieId: Int) =
        movieService.getMovieDetail(movieId)

    suspend fun getMovieReviews(movieId: Int, page: Int) =
        movieService.getMovieReview(movieId, page)
}