package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DisplayRecommendation(
    @SerialName("position") val position: String,
    @SerialName("scrollable") val scrollable: String,
)

@Serializable
data class DyStimulationProperties(
    @SerialName("backgroundColor") val backgroundColor: String,

    @SerialName("title") val title: String?,
    @SerialName("titleTextColor") val titleTextColor: String,
    @SerialName("titleTextSize") val titleTextSize: Int,

    @SerialName("expirationTimestampSec") val expirationTimestampSec: Long?,
    @SerialName("expirationTextColor") val expirationTextColor: String,
    @SerialName("expirationTextSize") val expirationTextSize: Int,

    @SerialName("buttonText") val buttonText: String?,
    @SerialName("buttonTextSize") val buttonTextSize: Int,
    @SerialName("buttonTextColor") val buttonTextColor: String,
    @SerialName("buttonBackgroundColor") val buttonBackgroundColor: String,
    @SerialName("buttonPressedBackgroundColor") val buttonPressedBackgroundColor: String,

    @SerialName("closeButtonColor") val closeButtonColor: String,

    @SerialName("displayRecommendation") val displayRecommendation: DisplayRecommendation,

) : DyWidgetProperties
