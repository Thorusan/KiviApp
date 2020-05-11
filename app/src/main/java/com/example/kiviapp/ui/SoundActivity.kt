package com.example.kiviapp.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kiviapp.R
import kotlinx.coroutines.*


class SoundActivity : AppCompatActivity() {
    var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        playSound()
    }

    private fun playSound() {
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(500)
            runBlocking {
                play()
                delay(5000)
                player!!.isLooping = false
                navigateToStart()
            }
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
