package com.dynamicyield.templates.ui.base.util

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
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

fun Activity.lockOrientation() {
    requestedOrientation = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        else -> ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }
}

fun Activity.unlockOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}