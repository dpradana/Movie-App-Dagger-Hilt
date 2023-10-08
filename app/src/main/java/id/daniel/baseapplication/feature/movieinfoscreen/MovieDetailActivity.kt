package id.daniel.baseapplication.feature.movieinfoscreen

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.daniel.baseapplication.R
import id.daniel.baseapplication.constants.MovieConstant
import id.daniel.baseapplication.databinding.ActivityMovieDetailBinding
import id.daniel.baseapplication.feature.movieinfoscreen.adapter.YoutubeListAdapter
import id.daniel.baseapplication.feature.movieinfoscreen.view.ReviewBottomSheet
import id.daniel.baseapplication.model.MovieDetail
import id.daniel.baseapplication.util.NetworkChangeReceiver
import id.daniel.baseapplication.util.Result
import id.daniel.baseapplication.util.formatToK
import id.daniel.baseapplication.util.hide
import id.daniel.baseapplication.util.loadImage
import id.daniel.baseapplication.util.loadImageRounded
import id.daniel.baseapplication.util.show

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<MovieDetailViewModel>()
    private lateinit var _binding: ActivityMovieDetailBinding
    private var isNetworkConnected: Boolean = true

    private val adapter: YoutubeListAdapter by lazy {
        YoutubeListAdapter { movieKey ->
            val intent = YoutubePlayerActivity.createIntent(this, movieKey)
            startActivity(intent)
        }
    }

    private val networkChangeReceiver = NetworkChangeReceiver { isConnected ->
        isNetworkConnected = isConnected
    }

    private val movieId: Int by lazy {
        intent?.getIntExtra(MovieConstant.PARAM_MOVIE_ID, 0) ?: 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        _binding.progressBarContainer.show()
        viewModel.fetchDetailMovie(movieId)
        initObserver()
        initView()
        initAdapter()

        NetworkChangeReceiver { isConnected ->
            isNetworkConnected = isConnected
        }

    }

    private fun initView() {
        _binding.ivBack.setOnClickListener {
            finish()
        }

        _binding.tryAgain.setOnClickListener {
            viewModel.fetchDetailMovie(movieId)
        }

        _binding.imgReview.setOnClickListener {
            val bottomSheetFragment = ReviewBottomSheet()
            bottomSheetFragment.movieId = movieId
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun initAdapter() {
        _binding.rvYoutubeList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        _binding.rvYoutubeList.adapter = adapter
    }

    private fun initObserver() {
        viewModel.responseDetail.observe(this) { response ->
            when (response) {
                is Result.Success -> {
                    // bind data to the view
                    response.data?.let {movieDetail ->
                        successView(movieDetail)
                    }

                }
                is Result.Error -> errorView()
                is Result.Loading -> {}
            }
        }

    }

    private fun successView(data: MovieDetail) {
        _binding.apply {
            _binding.nsScroll.show()
            _binding.errorView.hide()
            _binding.progressBarContainer.hide()
            imgBanner.loadImage(data.backdropPath)
            imgMovie.loadImageRounded(data.posterPath)
            movieDetailTitle.text = data.title
            moviePopularity.text = data.popularity.formatToK()
            movieReleaseDate.text = data.releaseDate
            movieDescription.text = data.overview
            if (data.videos.result.isNotEmpty()) {
                adapter.submitList(data.videos.result.toMutableList())
            } else {
                _binding.rvYoutubeList.hide()
                _binding.labelVideo.hide()
            }
        }
    }

    private fun errorView() {
        _binding.progressBarContainer.hide()
        _binding.nsScroll.hide()
        _binding.errorView.show()
        _binding.tvErrorInfo.text = if (isNetworkConnected) {
            getString(R.string.info_server_error)
        } else {
            getString(R.string.info_network_error)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    companion object {
        fun createIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailActivity::class.java).putExtra(
                MovieConstant.PARAM_MOVIE_ID, movieId
            )
    }
}