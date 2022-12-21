package com.dynamicyield.templates.ui.base.data

/**
 * Image size type for images in DY templates.
 */
enum class ImageSizeType(val valueStr: String) {
    MEDIUM("medium"), BIG("big");

    companion object {
        fun fromString(value: String): ImageSizeType? = when (value) {
            MEDIUM.valueStr -> MEDIUM
            BIG.valueStr -> BIG
            else -> null
        }
    }
}