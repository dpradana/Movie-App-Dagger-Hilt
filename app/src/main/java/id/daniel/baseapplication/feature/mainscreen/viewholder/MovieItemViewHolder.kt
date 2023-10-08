package id.daniel.baseapplication.feature.mainscreen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.daniel.baseapplication.databinding.ItemMovieListBinding
import id.daniel.baseapplication.model.MovieItem
import id.daniel.baseapplication.util.loadImageRounded

class MovieItemViewHolder(private val itemBinding: ItemMovieListBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    companion object {
        fun inflate(parent: ViewGroup): MovieItemViewHolder {
            val itemBinding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieItemViewHolder(itemBinding)
        }
    }

    fun bind(data: MovieItem, listener: (movieId: Int) -> Unit) = with(itemBinding) {
        itemBinding.root.setOnClickListener {
            listener.invoke(data.id)
        }
        itemBinding.movieTitle.text = data.title
        itemBinding.movieImage.loadImageRounded(data.posterPath)
    }
}