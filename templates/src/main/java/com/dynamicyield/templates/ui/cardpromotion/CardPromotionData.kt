package com.dynamicyield.templates.ui.cardpromotion

import android.view.View
import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class CardPromotionData(
    val topGradientColor: String,
    val bottomGradientColor: String,
    val image: String?,
    val imageScaleType: ImageScaleType,
    val bottomPanelColor: String,
    val bottomPanelText: String,
    val bottomPanelTextColor: String,
    val bottomPanelTextSize: Int,
    val bottomPanelButtonText: String,
    val bottomPanelButtonTextSize: Int,
    val bottomPanelButtonTextColor: String,
    val bottomPanelButtonColor: String,
    val bottomPanelButtonHoverColor: String,
    val bottomPanelButtonListener: View.OnClickListener,
)
