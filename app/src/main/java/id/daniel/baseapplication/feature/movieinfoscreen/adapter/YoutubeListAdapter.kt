package id.daniel.baseapplication.feature.movieinfoscreen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.daniel.baseapplication.feature.mainscreen.viewholder.MovieItemViewHolder
import id.daniel.baseapplication.feature.movieinfoscreen.viewholder.YoutubeItemViewHolder
import id.daniel.baseapplication.model.MovieItem
import id.daniel.baseapplication.model.VideosResult

class YoutubeListAdapter(private val listener: (ytbKey: String) -> Unit) :
    ListAdapter<VideosResult, YoutubeItemViewHolder>(YoutubeListDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeItemViewHolder {
        return YoutubeItemViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: YoutubeItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    internal class YoutubeListDiffUtilCallBack : DiffUtil.ItemCallback<VideosResult>() {

        override fun areItemsTheSame(oldItem: VideosResult, newItem: VideosResult): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: VideosResult, newItem: VideosResult): Boolean {
            return oldItem.key == newItem.key
        }

    }
}