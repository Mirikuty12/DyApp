package com.dynamicyield.templates.ui.stories.slider

import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class CircleStoryData(
    // title
    val titleText: String?,
    val titleTextColor: String,
    val titleTextSize: Int,

    // image
    val image: String?,
    val imageScaleType: ImageScaleType,
    val imageBorderColor: String? = null,
    val imageBorderWidth: Int = 0
)
