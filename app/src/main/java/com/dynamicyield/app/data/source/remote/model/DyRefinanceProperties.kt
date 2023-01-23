package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyRefinanceProperties(
    @SerialName("backgroundColor") val backgroundColor: String? = null,
    @SerialName("cornerRadius") val cornerRadius: Int? = null,

    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,

    @SerialName("title") val title: String? = null,
    @SerialName("titleTextColor") val titleTextColor: String? = null,
    @SerialName("titleTextSize") val titleTextSize: Int? = null,

    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("subtitleTextColor") val subtitleTextColor: String? = null,
    @SerialName("subtitleTextSize") val subtitleTextSize: Int? = null,

    @SerialName("ctaButton1Text") val ctaButton1Text: String? = null,
    @SerialName("ctaButton1TextSize") val ctaButton1TextSize: Int? = null,
    @SerialName("ctaButton1TextColor") val ctaButton1TextColor: String? = null,
    @SerialName("ctaButton1PressedTextColor") val ctaButton1PressedTextColor: String? = null,
    @SerialName("ctaButton1BackgroundColor") val ctaButton1BackgroundColor: String? = null,
    @SerialName("ctaButton1PressedBackgroundColor") val ctaButton1PressedBackgroundColor: String? = null,
    @SerialName("ctaButton1StrokeColor") val ctaButton1StrokeColor: String? = null,
    @SerialName("ctaButton1StrokeWidth") val ctaButton1StrokeWidth: Float? = null,

    @SerialName("ctaButton2Text") val ctaButton2Text: String? = null,
    @SerialName("ctaButton2TextSize") val ctaButton2TextSize: Int? = null,
    @SerialName("ctaButton2TextColor") val ctaButton2TextColor: String? = null,
    @SerialName("ctaButton2PressedTextColor") val ctaButton2PressedTextColor: String? = null,
    @SerialName("ctaButton2BackgroundColor") val ctaButton2BackgroundColor: String? = null,
    @SerialName("ctaButton2PressedBackgroundColor") val ctaButton2PressedBackgroundColor: String? = null,
    @SerialName("ctaButton2StrokeColor") val ctaButton2StrokeColor: String? = null,
    @SerialName("ctaButton2StrokeWidth") val ctaButton2StrokeWidth: Float? = null,

) : DyWidgetProperties

@Serializable
data class DyRefinanceSliderProperties(
    @SerialName("item1") val item1: DyRefinanceProperties? = null,
    @SerialName("item2") val item2: DyRefinanceProperties? = null,
    @SerialName("item3") val item3: DyRefinanceProperties? = null,
    @SerialName("item4") val item4: DyRefinanceProperties? = null,
    @SerialName("item5") val item5: DyRefinanceProperties? = null,
    @SerialName("item6") val item6: DyRefinanceProperties? = null,
    @SerialName("item7") val item7: DyRefinanceProperties? = null,
    @SerialName("item8") val item8: DyRefinanceProperties? = null,
    @SerialName("item9") val item9: DyRefinanceProperties? = null,
    @SerialName("item10") val item10: DyRefinanceProperties? = null,
) : DyWidgetProperties
