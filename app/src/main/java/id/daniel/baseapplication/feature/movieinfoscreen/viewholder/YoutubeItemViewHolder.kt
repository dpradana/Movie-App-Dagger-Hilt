package id.daniel.baseapplication.feature.movieinfoscreen.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.daniel.baseapplication.databinding.ItemMovieListBinding
import id.daniel.baseapplication.databinding.ItemYoutubeListBinding
import id.daniel.baseapplication.model.VideosResult
import id.daniel.baseapplication.util.loadImageUrl

class YoutubeItemViewHolder(private val itemBinding: ItemYoutubeListBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun inflate(parent: ViewGroup): YoutubeItemViewHolder {
            val itemBinding = ItemYoutubeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return YoutubeItemViewHolder(itemBinding)
        }
    }

    fun bind(data: VideosResult, listener: (ytbKey: String) -> Unit) = with(itemBinding) {
        itemBinding.root.setOnClickListener {
            listener.invoke(data.key)
        }
        itemBinding.movieImage.loadImageUrl("https://img.youtube.com/vi/${data.key}/hqdefault.jpg")
    }
}