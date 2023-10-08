package id.daniel.baseapplication.feature.movieinfoscreen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.daniel.baseapplication.feature.movieinfoscreen.viewholder.ReviewsViewHolder
import id.daniel.baseapplication.model.ResultReviews

class ReviewsAdapter() :
    ListAdapter<ResultReviews, ReviewsViewHolder>(ReviewsDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        return ReviewsViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class ReviewsDiffUtilCallBack : DiffUtil.ItemCallback<ResultReviews>() {

        override fun areItemsTheSame(oldItem: ResultReviews, newItem: ResultReviews): Boolean {
            return oldItem.authorDetails.username == newItem.authorDetails.username
        }

        override fun areContentsTheSame(oldItem: ResultReviews, newItem: ResultReviews): Boolean {
            return oldItem.content == newItem.content
        }

    }
}