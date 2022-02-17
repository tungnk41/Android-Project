package com.example.stopwatch

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var running : Boolean = false
    private var wasRunning : Boolean = false
    private var hours : Int = 0
    private var minutes : Int = 0
    private var seconds : Int = 0
    private fun time(hours : Int,minutes : Int,seconds : Int) : String = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            hours = savedInstanceState.getInt("hours")
            minutes = savedInstanceState.getInt("minutes")
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
        }
        runTimer()

        binding.btnStart.setOnClickListener {
            running = true
        }

        binding.btnStop.setOnClickListener {
            running = false
            wasRunning = false
        }

        binding.btnReset.setOnClickListener {
            hours = 0
            minutes = 0
            seconds = 0
            binding.tvTime.setText(time(hours,minutes,seconds))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        if(wasRunning){
            running = true
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
        running = false
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("hours",hours)
        outState.putInt("minutes",hours)
        outState.putInt("seconds",seconds)
        outState.putBoolean("wasRunning",wasRunning)
    }

    fun runTimer(){
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if(running){
                    seconds++
                    if(seconds == 60){
                        minutes++
                        seconds = 0
                    }
                    if(minutes == 60){
                        hours++
                        minutes = 0
                    }
                    if(hours == 60){
                        hours = 0
                    }
                    binding.tvTime.setText(time(hours,minutes,seconds))
                    wasRunning = true
                }
                handler.postDelayed(this,1000)
            }
        })
    }
}