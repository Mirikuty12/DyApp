package com.dynamicyield.templates.ui.base.data

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