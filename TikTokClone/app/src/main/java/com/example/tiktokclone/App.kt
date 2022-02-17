package com.example.tiktokclone

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File

class App : Application() {
    companion object{
        var context : Context?= null
        var videoCache: SimpleCache?=null
    }

    override fun onCreate() {
        super.onCreate()
        context = this!!
        videoCache = SimpleCache(
            File(cacheDir, "videosCacheFolder"),
            LeastRecentlyUsedCacheEvictor(32 * 1024 * 1024),
            ExoDatabaseProvider(this))
    }
}