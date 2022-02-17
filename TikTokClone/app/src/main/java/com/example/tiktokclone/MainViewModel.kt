package com.example.tiktokclone

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    val context = App.context!!
    val cache = App.videoCache!!
//    // Specify cache folder, my cache folder named media which is inside getCacheDir.
//    private val cacheFolder = File(context.cacheDir, "videosCacheFolder")
//    // Specify cache size and removing policies
//    private val cacheEvictor =
//        LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024) //90mb cache size, using LRU cache
//    private val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
//    var cache = SimpleCache(cacheFolder, cacheEvictor, databaseProvider)

    private val httpDataSourceFactory = DefaultHttpDataSourceFactory()
    private val dataSourceFactory = DefaultDataSourceFactory(context, httpDataSourceFactory)
    var cacheDataSourceFactory: CacheDataSource.Factory = CacheDataSource.Factory()
        .setUpstreamDataSourceFactory(dataSourceFactory)
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
        .setCache(cache)
        private set

    fun preLoadVideo(listVideoUrl : List<String>) {
        val jobs = listVideoUrl.map { url ->
            viewModelScope.launch(Dispatchers.IO) {
                val dataUri = Uri.parse(url)
                val dataSpec = DataSpec.Builder()
                    .setUri(Uri.parse(url))
                    .setPosition(0)
                    .setLength(500*1024) //cache 500kb from 0s
                    .setKey(url).build()

                CacheWriter(
                    cacheDataSourceFactory.createDataSource(),
                    dataSpec,
                    true,
                    null,
                    CacheWriter.ProgressListener { requestLength, bytesCached, newBytesCached ->
                        val downloadPercentage = (bytesCached * 100.0 / requestLength)
                        Log.d("TAG", "downloadPercentage: $downloadPercentage")
                    }
                ).cache()
            }
        }
    }
}