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
} catch (e: IllegalArgumentException) {
    var color: Int? = null
    val colorWithoutSpaces = this?.replace("\\s".toRegex(), "")

    if (colorWithoutSpaces != null
        && colorWithoutSpaces.startsWith("rgba(") && colorWithoutSpaces.endsWith(")")
    ) {
        val components = colorWithoutSpaces
            .substring(5, colorWithoutSpaces.lastIndex).split(",")
        color = try {
            val red = components[0].toInt().also {
                if (it !in 0..255) throw IllegalArgumentException()
            }
            val green = components[1].toInt().also {
                if (it !in 0..255) throw IllegalArgumentException()
            }
            val blue = components[2].toInt().also {
                if (it !in 0..255) throw IllegalArgumentException()
            }
            val alpha = components[3].toFloat().also {
                if (it !in 0f..1f) throw IllegalArgumentException()
            }
            Color.argb((alpha * 255).toInt(), red, green, blue)
        } catch (e: Exception) {
            null
        }
    }

    color
} catch (e: NullPointerException) {
    null
}

/**
 * Lock screen orientation
 */
fun Activity.lockOrientation() {
    requestedOrientation = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        else -> ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
    }
}

/**
 * Unlock screen orientation
 */
fun Activity.unlockOrientation() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}