package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyStoriesProperties(
    // General
    @SerialName("backgroundColor") val backgroundColor: String,
    @SerialName("closeButtonColor") val closeButtonColor: String,

    @SerialName("timelineBackgroundColor") val timelineBackgroundColor: String,
    @SerialName("timelineProgressColor") val timelineProgressColor: String,

    @SerialName("chooseButtonText") val chooseButtonText: String?,
    @SerialName("chooseButtonTextSize") val chooseButtonTextSize: Int,
    @SerialName("chooseButtonTextColor") val chooseButtonTextColor: String,
    @SerialName("chooseButtonPressedTextColor") val chooseButtonPressedTextColor: String,
    @SerialName("chooseButtonBackgroundColor") val chooseButtonBackgroundColor: String,
    @SerialName("chooseButtonPressedBackgroundColor") val chooseButtonPressedBackgroundColor: String,
    @SerialName("chooseButtonBorderColor") val chooseButtonBorderColor: String,
    @SerialName("chooseButtonBorderWidth") val chooseButtonBorderWidth: Float,

    // Story 1
    @SerialName("backgroundImageStory1") val backgroundImageStory1: String?,
    @SerialName("backgroundImageScaleTypeStory1") val backgroundImageScaleTypeStory1: String,
    @SerialName("contentOffsetStory1") val contentOffsetStory1: Float,
    @SerialName("logoImageStory1") val logoImageStory1: String?,
    @SerialName("logoImageScaleTypeStory1") val logoImageScaleTypeStory1: String,
    @SerialName("titleTextStory1") val titleTextStory1: String?,
    @SerialName("titleTextColorStory1") val titleTextColorStory1: String,
    @SerialName("titleTextSizeStory1") val titleTextSizeStory1: Int,
    @SerialName("titleTextBackgroundColorStory1") val titleTextBackgroundColorStory1: String,
    @SerialName("subtitleTextStory1") val subtitleTextStory1: String?,
    @SerialName("subtitleTextColorStory1") val subtitleTextColorStory1: String,
    @SerialName("subtitleTextSizeStory1") val subtitleTextSizeStory1: Int,
    @SerialName("subtitleTextBackgroundColorStory1") val subtitleTextBackgroundColorStory1: String,
    @SerialName("timeMillisStory1") val timeMillisStory1: Long,
    @SerialName("overlayColorStory1") val overlayColorStory1: String,

    // Story 2
    @SerialName("backgroundImageStory2") val backgroundImageStory2: String?,
    @SerialName("backgroundImageScaleTypeStory2") val backgroundImageScaleTypeStory2: String,
    @SerialName("contentOffsetStory2") val contentOffsetStory2: Float,
    @SerialName("logoImageStory2") val logoImageStory2: String?,
    @SerialName("logoImageScaleTypeStory2") val logoImageScaleTypeStory2: String,
    @SerialName("titleTextStory2") val titleTextStory2: String?,
    @SerialName("titleTextColorStory2") val titleTextColorStory2: String,
    @SerialName("titleTextSizeStory2") val titleTextSizeStory2: Int,
    @SerialName("titleTextBackgroundColorStory2") val titleTextBackgroundColorStory2: String,
    @SerialName("subtitleTextStory2") val subtitleTextStory2: String?,
    @SerialName("subtitleTextColorStory2") val subtitleTextColorStory2: String,
    @SerialName("subtitleTextSizeStory2") val subtitleTextSizeStory2: Int,
    @SerialName("subtitleTextBackgroundColorStory2") val subtitleTextBackgroundColorStory2: String,
    @SerialName("timeMillisStory2") val timeMillisStory2: Long,
    @SerialName("overlayColorStory2") val overlayColorStory2: String,

    // Story 3
    @SerialName("backgroundImageStory3") val backgroundImageStory3: String?,
    @SerialName("backgroundImageScaleTypeStory3") val backgroundImageScaleTypeStory3: String,
    @SerialName("contentOffsetStory3") val contentOffsetStory3: Float,
    @SerialName("logoImageStory3") val logoImageStory3: String?,
    @SerialName("logoImageScaleTypeStory3") val logoImageScaleTypeStory3: String,
    @SerialName("titleTextStory3") val titleTextStory3: String?,
    @SerialName("titleTextColorStory3") val titleTextColorStory3: String,
    @SerialName("titleTextSizeStory3") val titleTextSizeStory3: Int,
    @SerialName("titleTextBackgroundColorStory3") val titleTextBackgroundColorStory3: String,
    @SerialName("subtitleTextStory3") val subtitleTextStory3: String?,
    @SerialName("subtitleTextColorStory3") val subtitleTextColorStory3: String,
    @SerialName("subtitleTextSizeStory3") val subtitleTextSizeStory3: Int,
    @SerialName("subtitleTextBackgroundColorStory3") val subtitleTextBackgroundColorStory3: String,
    @SerialName("timeMillisStory3") val timeMillisStory3: Long,
    @SerialName("overlayColorStory3") val overlayColorStory3: String,

    // Story 4
    @SerialName("backgroundImageStory4") val backgroundImageStory4: String?,
    @SerialName("backgroundImageScaleTypeStory4") val backgroundImageScaleTypeStory4: String,
    @SerialName("contentOffsetStory4") val contentOffsetStory4: Float,
    @SerialName("logoImageStory4") val logoImageStory4: String?,
    @SerialName("logoImageScaleTypeStory4") val logoImageScaleTypeStory4: String,
    @SerialName("titleTextStory4") val titleTextStory4: String?,
    @SerialName("titleTextColorStory4") val titleTextColorStory4: String,
    @SerialName("titleTextSizeStory4") val titleTextSizeStory4: Int,
    @SerialName("titleTextBackgroundColorStory4") val titleTextBackgroundColorStory4: String,
    @SerialName("subtitleTextStory4") val subtitleTextStory4: String?,
    @SerialName("subtitleTextColorStory4") val subtitleTextColorStory4: String,
    @SerialName("subtitleTextSizeStory4") val subtitleTextSizeStory4: Int,
    @SerialName("subtitleTextBackgroundColorStory4") val subtitleTextBackgroundColorStory4: String,
    @SerialName("timeMillisStory4") val timeMillisStory4: Long,
    @SerialName("overlayColorStory4") val overlayColorStory4: String,

    // Story 5
    @SerialName("backgroundImageStory5") val backgroundImageStory5: String?,
    @SerialName("backgroundImageScaleTypeStory5") val backgroundImageScaleTypeStory5: String,
    @SerialName("contentOffsetStory5") val contentOffsetStory5: Float,
    @SerialName("logoImageStory5") val logoImageStory5: String?,
    @SerialName("logoImageScaleTypeStory5") val logoImageScaleTypeStory5: String,
    @SerialName("titleTextStory5") val titleTextStory5: String?,
    @SerialName("titleTextColorStory5") val titleTextColorStory5: String,
    @SerialName("titleTextSizeStory5") val titleTextSizeStory5: Int,
    @SerialName("titleTextBackgroundColorStory5") val titleTextBackgroundColorStory5: String,
    @SerialName("subtitleTextStory5") val subtitleTextStory5: String?,
    @SerialName("subtitleTextColorStory5") val subtitleTextColorStory5: String,
    @SerialName("subtitleTextSizeStory5") val subtitleTextSizeStory5: Int,
    @SerialName("subtitleTextBackgroundColorStory5") val subtitleTextBackgroundColorStory5: String,
    @SerialName("timeMillisStory5") val timeMillisStory5: Long,
    @SerialName("overlayColorStory5") val overlayColorStory5: String,

) : DyWidgetProperties
