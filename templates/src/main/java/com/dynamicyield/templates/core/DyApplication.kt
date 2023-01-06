package com.dynamicyield.templates.core

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder

object DyApplication {
    private var imageLoader: ImageLoader? = null

    fun imageLoader(context: Context) = imageLoader ?: newImageLoader(context)

    private fun newImageLoader(context: Context): ImageLoader {
        // Check again in case imageLoader was just set.
        imageLoader?.let { return it }

        val newImageLoader = ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .crossfade(true)
            .build()
        imageLoader = newImageLoader
        return newImageLoader
    }
}