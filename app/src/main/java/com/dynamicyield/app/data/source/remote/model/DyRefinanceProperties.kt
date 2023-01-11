package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyRefinanceProperties(
    @SerialName("backgroundColor") val backgroundColor: String,
    @SerialName("cornerRadius") val cornerRadius: Int,

    @SerialName("image") val image: String?,
    @SerialName("imageScaleType") val imageScaleType: String,

    @SerialName("title") val title: String?,
    @SerialName("titleTextColor") val titleTextColor: String,
    @SerialName("titleTextSize") val titleTextSize: Int,

    @SerialName("subtitle") val subtitle: String?,
    @SerialName("subtitleTextColor") val subtitleTextColor: String,
    @SerialName("subtitleTextSize") val subtitleTextSize: Int,

    @SerialName("ctaButton1Text") val ctaButton1Text: String?,
    @SerialName("ctaButton1TextSize") val ctaButton1TextSize: Int,
    @SerialName("ctaButton1TextColor") val ctaButton1TextColor: String,
    @SerialName("ctaButton1PressedTextColor") val ctaButton1PressedTextColor: String,
    @SerialName("ctaButton1BackgroundColor") val ctaButton1BackgroundColor: String,
    @SerialName("ctaButton1PressedBackgroundColor") val ctaButton1PressedBackgroundColor: String,
    @SerialName("ctaButton1StrokeColor") val ctaButton1StrokeColor: String,
    @SerialName("ctaButton1StrokeWidth") val ctaButton1StrokeWidth: Float,

    @SerialName("ctaButton2Text") val ctaButton2Text: String?,
    @SerialName("ctaButton2TextSize") val ctaButton2TextSize: Int,
    @SerialName("ctaButton2TextColor") val ctaButton2TextColor: String,
    @SerialName("ctaButton2PressedTextColor") val ctaButton2PressedTextColor: String,
    @SerialName("ctaButton2BackgroundColor") val ctaButton2BackgroundColor: String,
    @SerialName("ctaButton2PressedBackgroundColor") val ctaButton2PressedBackgroundColor: String,
    @SerialName("ctaButton2StrokeColor") val ctaButton2StrokeColor: String,
    @SerialName("ctaButton2StrokeWidth") val ctaButton2StrokeWidth: Float,

    ) : DyWidgetProperties
