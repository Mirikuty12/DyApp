package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyStoriesProperties(
    @SerialName("story1") val story1: StoryProperties? = null,
    @SerialName("story2") val story2: StoryProperties? = null,
    @SerialName("story3") val story3: StoryProperties? = null,
    @SerialName("story4") val story4: StoryProperties? = null,
    @SerialName("story5") val story5: StoryProperties? = null,
    @SerialName("story6") val story6: StoryProperties? = null,
    @SerialName("story7") val story7: StoryProperties? = null,
    @SerialName("story8") val story8: StoryProperties? = null,
    @SerialName("story9") val story9: StoryProperties? = null,
    @SerialName("story10") val story10: StoryProperties? = null,
) : DyWidgetProperties

@Serializable
data class StoryProperties(
    @SerialName("previewProperties") val previewProperties: PreviewProperties? = null,
    @SerialName("fullscreenProperties") val fullscreenProperties: FullscreenProperties? = null,
)

@Serializable
data class PreviewProperties(
    // title
    @SerialName("titleText") val titleText: String? = null,
    @SerialName("titleTextColor") val titleTextColor: String? = null,
    @SerialName("titleTextSize") val titleTextSize: Int? = null,

    // image
    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,
    @SerialName("imageBorderColor") val imageBorderColor: String? = null,
    @SerialName("imageBorderWidth") val imageBorderWidth: Int? = null,
)

@Serializable
data class FullscreenProperties(
    // background color
    @SerialName("backgroundColor") val backgroundColor: String? = null,

    // close button color
    @SerialName("closeButtonColor") val closeButtonColor: String? = null,

    // timeline properties
    @SerialName("timelineBackgroundColor") val timelineBackgroundColor: String? = null,
    @SerialName("timelineProgressColor") val timelineProgressColor: String? = null,

    // button properties
    @SerialName("buttonText") val buttonText: String? = null,
    @SerialName("buttonTextSize") val buttonTextSize: Int? = null,
    @SerialName("buttonTextColor") val buttonTextColor: String? = null,
    @SerialName("pressedButtonTextColor") val pressedButtonTextColor: String? = null,
    @SerialName("buttonBackgroundColor") val buttonBackgroundColor: String? = null,
    @SerialName("pressedButtonBackgroundColor") val pressedButtonBackgroundColor: String? = null,
    @SerialName("buttonStrokeColor") val buttonStrokeColor: String? = null,
    @SerialName("buttonStrokeWidth") val buttonStrokeWidth: Float? = null,

    // slides
    @SerialName("slides") val slides: SlidesProperties? = null,
)

@Serializable
data class SlidesProperties(
    @SerialName("slide1") val slide1: SlideProperties? = null,
    @SerialName("slide2") val slide2: SlideProperties? = null,
    @SerialName("slide3") val slide3: SlideProperties? = null,
    @SerialName("slide4") val slide4: SlideProperties? = null,
    @SerialName("slide5") val slide5: SlideProperties? = null,
    @SerialName("slide6") val slide6: SlideProperties? = null,
    @SerialName("slide7") val slide7: SlideProperties? = null,
    @SerialName("slide8") val slide8: SlideProperties? = null,
    @SerialName("slide9") val slide9: SlideProperties? = null,
    @SerialName("slide10") val slide10: SlideProperties? = null,
)

@Serializable
data class SlideProperties(
    // background image
    @SerialName("backgroundImage") val backgroundImage: String? = null,
    @SerialName("backgroundImageScaleType") val backgroundImageScaleType: String? = null,

    // content offset from top: 0 - top, 50 - middle, 100 - bottom
    @SerialName("contentOffset") val contentOffset: Int? = null,

    // logo image
    @SerialName("logoImage") val logoImage: String? = null,
    @SerialName("logoImageScaleType") val logoImageScaleType: String? = null,

    // title
    @SerialName("titleText") val titleText: String? = null,
    @SerialName("titleTextColor") val titleTextColor: String? = null,
    @SerialName("titleTextSize") val titleTextSize: Int? = null,
    @SerialName("titleTextBackgroundColor") val titleTextBackgroundColor: String? = null,

    // subtitle
    @SerialName("subtitleText") val subtitleText: String? = null,
    @SerialName("subtitleTextColor") val subtitleTextColor: String? = null,
    @SerialName("subtitleTextSize") val subtitleTextSize: Int? = null,
    @SerialName("subtitleTextBackgroundColor") val subtitleTextBackgroundColor: String? = null,

    // slide duration
    @SerialName("durationMillis") val durationMillis: Long? = null,

    // overlay color
    @SerialName("overlayColor") val overlayColor: String? = null,
)

