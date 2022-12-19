package com.dynamicyield.templates.ui.refinance

import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class RefinanceData(
    val backgroundColor: String,
    val cornerRadius: Int,

    val image: String?,
    val imageScaleType: ImageScaleType,

    val title: String,
    val titleTextColor: String,
    val titleTextSize: Int,

    val subtitle: String,
    val subtitleTextColor: String,
    val subtitleTextSize: Int,

    val ctaButton1Text: String?,
    val ctaButton1TextSize: Int,
    val ctaButton1TextColor: String,
    val ctaButton1PressedTextColor: String,
    val ctaButton1BackgroundColor: String,
    val ctaButton1PressedBackgroundColor: String,
    val ctaButton1StrokeColor: String,
    val ctaButton1StrokeWidth: Float,

    val ctaButton2Text: String?,
    val ctaButton2TextSize: Int,
    val ctaButton2TextColor: String,
    val ctaButton2PressedTextColor: String,
    val ctaButton2BackgroundColor: String,
    val ctaButton2PressedBackgroundColor: String,
    val ctaButton2StrokeColor: String,
    val ctaButton2StrokeWidth: Float,
)
