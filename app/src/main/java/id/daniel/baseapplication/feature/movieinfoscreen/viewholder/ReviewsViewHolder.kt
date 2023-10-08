package id.daniel.baseapplication.feature.movieinfoscreen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.daniel.baseapplication.databinding.ItemReviewBinding
import id.daniel.baseapplication.model.ResultReviews
import id.daniel.baseapplication.util.loadImageRounded

class ReviewsViewHolder(private val itemBinding: ItemReviewBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun inflate(parent: ViewGroup): ReviewsViewHolder {
            val itemBinding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ReviewsViewHolder(itemBinding)
        }
    }

    fun bind(data: ResultReviews) = with(itemBinding) {
        itemBinding.ivAvatar.loadImageRounded(data.authorDetails.avatarPath ?: "")
        itemBinding.tvUsername.text = data.authorDetails.username
        itemBinding.tvReview.text = data.content
    }
}