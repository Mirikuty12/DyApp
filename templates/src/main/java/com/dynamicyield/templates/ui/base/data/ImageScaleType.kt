package com.dynamicyield.templates.ui.base.data

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