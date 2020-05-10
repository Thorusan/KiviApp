package com.example.kiviapp.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kiviapp.R
import kotlinx.coroutines.*


class ScanActivity : AppCompatActivity() {
    var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        scanQRCode()
    }

    private fun scanQRCode() {
        GlobalScope.launch(context = Dispatchers.Main) {
            delay(500)
            runBlocking {
                play()
                delay(5000)
                player!!.isLooping = false
                finish()
            }
        }
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
