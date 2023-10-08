package id.daniel.baseapplication.feature.mainscreen

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.daniel.baseapplication.R
import id.daniel.baseapplication.databinding.ActivityListBinding
import id.daniel.baseapplication.feature.mainscreen.adapter.MovieListAdapter
import id.daniel.baseapplication.feature.movieinfoscreen.MovieDetailActivity
import id.daniel.baseapplication.model.MovieItem
import id.daniel.baseapplication.model.TopRatedMovies
import id.daniel.baseapplication.util.NetworkChangeReceiver
import id.daniel.baseapplication.util.Result
import id.daniel.baseapplication.util.hide
import id.daniel.baseapplication.util.show

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListViewModel>()
    private val listMovies = ArrayList<MovieItem>()
    private var isNetworkConnected: Boolean = true
    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter { movieId ->
            val intent = MovieDetailActivity.createIntent(this, movieId)
            startActivity(intent)
        }
    }

    private val networkChangeReceiver = NetworkChangeReceiver { isConnected ->
        isNetworkConnected = isConnected
    }

    private lateinit var _binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        initView()
        initAdapter()
        initObserver()
        _binding.progressBarContainer.show()
    }

    private fun initView() {
        _binding.tryAgain.setOnClickListener {
            listMovies.clear()
            viewModel.fetchListMoviesResponse(true)
        }
    }

    private fun initObserver() {
        viewModel.response.observe(this) { response ->
            when (response) {
                is Result.Success -> successView(response.data)
                is Result.Error -> errorView()
                is Result.Loading -> {}
            }
        }
    }

    private fun successView(data: TopRatedMovies?) {
        _binding.progressBarContainer.hide()
        _binding.errorView.hide()
        data?.movieItems?.let {
            listMovies.addAll(it)
            adapter.submitList(listMovies)
        }
    }

    private fun errorView() {
        _binding.progressBarContainer.hide()
        if (isNetworkConnected) {
            _binding.errorView.show()
            _binding.tvErrorInfo.text = getString(R.string.info_server_error)
        } else {
            _binding.errorView.show()
            _binding.tvErrorInfo.text = getString(R.string.info_network_error)
        }
    }

    private fun initAdapter() {
        val layoutManager = GridLayoutManager(this, 3)
        _binding.rvMovieList.layoutManager = layoutManager
        _binding.rvMovieList.adapter = adapter

        _binding.rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Handle scroll position here
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                val isLastPosition = totalItemCount.minus(1) == lastVisibleItemPosition
                if (listMovies.isNotEmpty() && isLastPosition && viewModel.response.value?.data?.page == viewModel.page) {
                    viewModel.fetchListMoviesResponse()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }
}