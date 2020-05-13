package com.example.kiviapp.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kiviapp.R
import com.example.kiviapp.R.layout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SoundActivity : AppCompatActivity() {
    var player: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_sound)

        lifecycleScope.launch {
            playSound()
        }
    }

    override fun onBackPressed() {
        // do nothing when back pressed
    }

    private suspend fun playSound() {
        withContext(Dispatchers.Main) {
                play()
                delay(5000)
                player!!.isLooping = false
                navigateToStart()
            }
    }

    private fun navigateToStart() {
        val intent = Intent(this@SoundActivity, VehiclePagerActivity::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    fun play() {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.zvok)
            player!!.setOnCompletionListener { stopPlayer() }

        }
        player!!.start()
        player!!.isLooping = true
    }

    private fun stopPlayer() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }


}
