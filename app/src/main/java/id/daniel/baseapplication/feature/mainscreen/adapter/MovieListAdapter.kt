package id.daniel.baseapplication.feature.mainscreen.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.daniel.baseapplication.feature.mainscreen.viewholder.MovieItemViewHolder
import id.daniel.baseapplication.model.MovieItem

class MovieListAdapter(private val listener: (movieId: Int) -> Unit) :
    ListAdapter<MovieItem, MovieItemViewHolder>(MovieListDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    internal class MovieListDiffUtilCallBack : DiffUtil.ItemCallback<MovieItem>() {

        override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
            return oldItem.title == newItem.title
        }

    }
}