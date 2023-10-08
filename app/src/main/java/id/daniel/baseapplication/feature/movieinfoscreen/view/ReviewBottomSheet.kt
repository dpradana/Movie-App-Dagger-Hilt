package id.daniel.baseapplication.feature.movieinfoscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.daniel.baseapplication.databinding.BottomsheetReviewBinding
import id.daniel.baseapplication.feature.movieinfoscreen.MovieDetailViewModel
import id.daniel.baseapplication.feature.movieinfoscreen.adapter.ReviewsAdapter
import id.daniel.baseapplication.model.ResultReviews
import id.daniel.baseapplication.util.Result

@AndroidEntryPoint
class ReviewBottomSheet: BottomSheetDialogFragment() {
    private lateinit var _binding: BottomsheetReviewBinding
    private val listReview = ArrayList<ResultReviews>()
    private val adapter: ReviewsAdapter by lazy { ReviewsAdapter() }
    private val viewModel by viewModels<MovieDetailViewModel>()
    var movieId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetReviewBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the BottomSheetBehavior and expand it
        val behavior = BottomSheetBehavior.from(view.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initAdapter()
        initObserver()
        viewModel.fetchMovieReviews(movieId, true)
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        _binding.rvReviews.layoutManager = layoutManager
        _binding.rvReviews.adapter = adapter

        _binding.rvReviews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Handle scroll position here
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                val isLastPosition = totalItemCount.minus(1) == lastVisibleItemPosition
                if (listReview.isNotEmpty() && isLastPosition && viewModel.responseReviews.value?.data?.page == viewModel.page) {
                    viewModel.fetchMovieReviews(movieId)
                }
            }
        })
    }

    private fun initObserver() {
        viewModel.responseReviews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    // bind data to the view
                    _binding.tvTitleReview.text = "Reviews (${(response.data?.totalResults ?: 0)})"
                    response.data?.results?.let {
                        listReview.addAll(it)
                        adapter.submitList(listReview)
                    }

                }
                is Result.Error -> {
                    // show error message
                }
                is Result.Loading -> {
                    // show a progress bar
                }
            }
        }
    }
}