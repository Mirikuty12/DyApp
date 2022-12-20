package com.dynamicyield.templates.ui.base.data

/**
 * Image scale type for images in DY templates.
 */
enum class ImageScaleType(val valueStr: String) {
    FIT("fit"), FILL("fill");

    companion object {
        fun fromString(value: String): ImageScaleType? = when (value) {
            FIT.valueStr -> FIT
            FILL.valueStr -> FILL
            else -> null
        }
    }
}