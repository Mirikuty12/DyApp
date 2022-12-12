package com.dynamicyield.templates.ui.crossupsell

import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType

data class CrossUpsellStepData(
    val image: String?,
    val imageScaleType: ImageScaleType,
    val imageSizeType: ImageSizeType,
    val title: String?,
    val titleSize: Int,
    val titleColor: String,
    val subtitle: String?,
    val subtitleSize: Int,
    val subtitleColor: String,
    val buttonColor: String,
    val buttonHoverColor: String,
    val buttonText: String?,
    val buttonTextSize: Int,
    val buttonTextColor: String,
    val progressBarColor: String,
    val progressBarBackgroundColor: String,
    val progressTextColor: String,
)
