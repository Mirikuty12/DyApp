package com.dynamicyield.templates.ui.stories

import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class StoryData(
    // Circle preview data
    val previewData: PreviewStoryData,

    // Fullscreen story data
    val fullscreenData: FullscreenStoryData
)

data class PreviewStoryData(
    // title
    val titleText: String?,
    val titleTextColor: String,
    val titleTextSize: Int,

    // image
    val image: String?,
    val imageScaleType: ImageScaleType,
    val imageBorderColor: String? = null,
    val imageBorderWidth: Int = 0,
)

data class FullscreenStoryData(
    // background color
    val backgroundColor: String,

    // close button color
    val closeButtonColor: String,

    // timeline properties
    val timelineBackgroundColor: String,
    val timelineProgressColor: String,

    // button properties
    val buttonText: String? = null,
    val buttonTextSizeSp: Int,
    val buttonTextColorString: String,
    val pressedButtonTextColorString: String,
    val buttonBackgroundColorString: String,
    val pressedButtonBackgroundColorString: String,
    val buttonStrokeColorString: String,
    val buttonStrokeWidth: Float,

    // slides
    val slides: List<SlideData>
)

data class SlideData(
    // background image
    val backgroundImage: String?,
    val backgroundImageScaleType: ImageScaleType,

    // content offset from top: 0 - top, 50 - middle, 100 - bottom
    val contentOffset: Int,

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

    // slide duration
    val durationMillis: Long,

    // overlay color
    val overlayColor: String,
)
