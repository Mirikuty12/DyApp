package com.dynamicyield.templates.ui.base.util

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.annotation.ColorInt

fun createGradientRectDrawable(
    @ColorInt gColors: IntArray,
    gOrientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
    cornerRadiusPx: Int = 0,
    strokeWidthPx: Int = 0,
    @ColorInt strokeColor: Int? = null
): Drawable = GradientDrawable().apply {
    colors = gColors
    gradientType = GradientDrawable.LINEAR_GRADIENT
    shape = GradientDrawable.RECTANGLE
    orientation = gOrientation
    if (cornerRadiusPx > 0) cornerRadius = cornerRadiusPx.toFloat()
    if (strokeWidthPx > 0 && strokeColor != null) setStroke(strokeWidthPx, strokeColor)
}

fun createRectDrawable(
    @ColorInt fillColor: Int? = null,
    @ColorInt strokeColor: Int? = null,
    strokeWidthPx: Int = 0,
    tlCornerRadiusPx: Int = 0,
    trCornerRadiusPx: Int = 0,
    brCornerRadiusPx: Int = 0,
    blCornerRadiusPx: Int = 0,
): Drawable? {
    var fillDrawable: Drawable? = null
    var strokeDrawable: Drawable? = null
    val isAntiAlias = listOf(
        tlCornerRadiusPx, trCornerRadiusPx, brCornerRadiusPx, blCornerRadiusPx
    ).max() > 0

    if (fillColor != null && fillColor != Color.TRANSPARENT) {
        fillDrawable = ShapeDrawable().apply {
            shape = RoundRectShape(
                floatArrayOf(
                    tlCornerRadiusPx.toFloat(), tlCornerRadiusPx.toFloat(),
                    trCornerRadiusPx.toFloat(), trCornerRadiusPx.toFloat(),
                    brCornerRadiusPx.toFloat(), brCornerRadiusPx.toFloat(),
                    blCornerRadiusPx.toFloat(), blCornerRadiusPx.toFloat(),
                ),
                null,
                null
            )
//            shape = RectShape()
//            paint.pathEffect = CornerPathEffect(cornerRadiusPx.toFloat())
            paint.isAntiAlias = isAntiAlias
            paint.color = fillColor
        }
    }

    if (strokeColor != null && strokeColor != Color.TRANSPARENT && strokeWidthPx > 0) {
        strokeDrawable = ShapeDrawable().apply {
            shape = RoundRectShape(
                floatArrayOf(
                    tlCornerRadiusPx.toFloat(), tlCornerRadiusPx.toFloat(),
                    trCornerRadiusPx.toFloat(), trCornerRadiusPx.toFloat(),
                    brCornerRadiusPx.toFloat(), brCornerRadiusPx.toFloat(),
                    blCornerRadiusPx.toFloat(), blCornerRadiusPx.toFloat(),
                ),
                null,
                null
            )
//            shape = RectShape()
//            paint.pathEffect = CornerPathEffect(rPx)
            paint.isAntiAlias = isAntiAlias
            paint.color = strokeColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidthPx.toFloat()
        }
    }

    return when {
        fillDrawable != null && strokeDrawable != null ->
            LayerDrawable(arrayOf(fillDrawable, strokeDrawable)).apply {
                val i = (strokeWidthPx / 2f).toInt()
                setLayerInset(0, i, i, i, i)
                setLayerInset(1, i, i, i, i)
            }
        strokeDrawable != null -> LayerDrawable(arrayOf(strokeDrawable)).apply {
            val i = (strokeWidthPx / 2f).toInt()
            setLayerInset(0, i, i, i, i)
        }
        fillDrawable != null -> fillDrawable
        else -> null
    }
}

fun createRectDrawable(
    @ColorInt fillColor: Int? = null,
    @ColorInt strokeColor: Int? = null,
    strokeWidthPx: Int = 0,
    cornerRadiusPx: Int = 0,
) = createRectDrawable(
    fillColor, strokeColor, strokeWidthPx,
    cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx
)