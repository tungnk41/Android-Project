package com.example.tiktokclone

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.tiktokclone.mockup.MockupVideoData

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager : ViewPager2
    private lateinit var pagerAdapter: PagerAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.vpPager)
        viewModel.preLoadVideo(MockupVideoData.listVideo.map { it.mediaUrl })
        pagerAdapter = PagerAdapter(this, MockupVideoData.listVideo)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 2
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected: " + position)
            }
        })
    }
}