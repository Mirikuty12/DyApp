package com.dynamicyield.templates.ui.base.util

import android.content.res.Resources
import android.graphics.Color

/**
 * Converts dp to pixel
 */
fun Float.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Build Android color from string
 */
fun String?.parseColorOrNull(): Int? = try {
    Color.parseColor(this)
} catch (e: RuntimeException) {
    null
}