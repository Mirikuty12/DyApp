package com.dynamicyield.templates.ui.cardpromotion

import com.dynamicyield.templates.ui.base.data.ImageScaleType

/**
 * The data class that represents a Card Promotion item in the Card Promotion Slider view
 */
data class CardPromotionData(
    val topGradientColor: String,
    val bottomGradientColor: String,
    val image: String?,
    val imageScaleType: ImageScaleType,
    val bottomPanelColor: String,
    val bottomPanelText: String?,
    val bottomPanelTextColor: String,
    val bottomPanelTextSize: Int,
    val bottomPanelButtonText: String?,
    val bottomPanelButtonTextSize: Int,
    val bottomPanelButtonTextColor: String,
    val bottomPanelButtonColor: String,
    val bottomPanelButtonHoverColor: String,
)
