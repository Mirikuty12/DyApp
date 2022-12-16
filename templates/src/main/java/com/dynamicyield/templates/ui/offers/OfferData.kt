package com.dynamicyield.templates.ui.offers

import com.dynamicyield.templates.ui.base.data.ImageScaleType

data class OfferData(
    // background image
    val backgroundImage: String?,
    val backgroundImageScaleType: ImageScaleType,

    // logo image
    val logoImage: String?,
    val logoImageScaleType: ImageScaleType,

    // label
    val labelText: String?,
    val labelTextColor: String,
    val labelTextSize: Int,
    val labelBackgroundColor: String,

    // title
    val titleText: String?,
    val titleTextColor: String,
    val titleTextSize: Int,
)
