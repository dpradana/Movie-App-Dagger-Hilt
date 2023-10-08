package id.daniel.baseapplication.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.daniel.baseapplication.R
import id.daniel.baseapplication.databinding.ActivityListBinding
import id.daniel.baseapplication.databinding.ActivityMainBinding
import id.daniel.baseapplication.feature.mainscreen.ListActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // 3 seconds
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // Delay for a few seconds and then start the main activity
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}