package id.daniel.baseapplication.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.daniel.baseapplication.R
import id.daniel.baseapplication.constants.MovieConstant.MOVIE_ORIGINAL_IMAGE

fun String.createImageUrl(): String {
    return MOVIE_ORIGINAL_IMAGE + this
}

fun ImageView.loadImageRounded(imageUrl: String, roundedValue: Int = 24) {
    Glide.with(this.context)
        .load(imageUrl.createImageUrl())
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_error))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_default))
        .transform(RoundedCorners(roundedValue))
        .into(this)
}

fun ImageView.loadImage(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl.createImageUrl())
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_error))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_default))
        .into(this)
}

fun ImageView.loadImageUrl(imageUrl: String) {
    Glide.with(this.context)
        .load(imageUrl)
        .error(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_error))
        .placeholder(ContextCompat.getDrawable(this.context, R.drawable.ic_placeholder_default))
        .transform(RoundedCorners(24))
        .into(this)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Double.formatToK(): String {
    val absNumber = Math.abs(this)
    return when {
        absNumber < 1000.0 -> absNumber.toString()
        absNumber < 10_000.0 -> String.format("%.1fK", absNumber / 1000.0)
        absNumber < 100_000.0 -> String.format("%.0fK", absNumber / 1000.0)
        else -> String.format("%.0fK", absNumber / 1000.0)
    }
}