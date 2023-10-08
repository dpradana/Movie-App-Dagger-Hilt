package id.daniel.baseapplication.feature.movieinfoscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import dagger.hilt.android.AndroidEntryPoint
import id.daniel.baseapplication.R
import id.daniel.baseapplication.constants.MovieConstant
import id.daniel.baseapplication.databinding.ActivityMovieDetailBinding
import id.daniel.baseapplication.databinding.ActivityYoutubePlayerBinding

@AndroidEntryPoint
class YoutubePlayerActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityYoutubePlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityYoutubePlayerBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val videoKey = intent?.getStringExtra(MovieConstant.PARAM_VIDEO_KEY)
        lifecycle.addObserver(_binding.youtubePlayerView)
        Handler(mainLooper).postDelayed({
            Log.i("videoKey", videoKey.toString())
            _binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoKey ?: "", 0F)
                }

            })
        }, 1000)

        _binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding.youtubePlayerView.release()
    }

    companion object {
        fun createIntent(context: Context, videoKey: String) =
            Intent(context, YoutubePlayerActivity::class.java).putExtra(
                MovieConstant.PARAM_VIDEO_KEY, videoKey
            )
    }
}