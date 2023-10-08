package id.daniel.baseapplication.datasource

import id.daniel.baseapplication.model.MovieDetail
import id.daniel.baseapplication.model.MovieReview
import id.daniel.baseapplication.model.TopRatedMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getTopRatedMovies(@Query("page") page: Int,
                                  @Query("language") language: String): Response<TopRatedMovies>

    @GET("movie/{movie_id}?&append_to_response=videos")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): Response<MovieDetail>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(@Path("movie_id") movieId: Int, @Query("page") page: Int): Response<MovieReview>
}