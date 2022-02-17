package com.example.tiktokclone

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tiktokclone.model.VideoModel

class PagerAdapter(activity: AppCompatActivity, val listVideo: List<VideoModel>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return listVideo.size
    }

    override fun createFragment(position: Int): Fragment {
            return VideoFragment.newInstance(listVideo[position].mediaUrl)
    }

}