package com.dynamicyield.templates.ui.stories.dialog

import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class StoryData(

    // background image
    val backgroundImage: String?,
    val backgroundImageScaleType: ImageScaleType,

    // content offset from top: 0 - top, 0.5 - middle, 1 - bottom
    val contentOffset: Float,

    // logo image
    val logoImage: String?,
    val logoImageScaleType: ImageScaleType,

    // title
    val titleText: String?,
    val titleTextColor: String,
    val titleTextSize: Int,
    val titleTextBackgroundColor: String,

    // subtitle
    val subtitleText: String?,
    val subtitleTextColor: String,
    val subtitleTextSize: Int,
    val subtitleTextBackgroundColor: String,

    // time to show
    val timeMillis: Long,

    // overlay color
    val overlayColor: String,
)
