package com.dynamicyield.templates.ui.stories.dialog

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.dynamicyield.templates.ui.base.util.dpToPx
import kotlin.math.max

class ProgressDrawable : Drawable() {
    private val segmentRect = RectF()
    private val paint = Paint().apply {
        isAntiAlias = true
    }

    @ColorInt
    var backgroundColor: Int = Color.GRAY

    @ColorInt
    var progressColor: Int = Color.BLUE

    var segmentNumber = 1
    var gapWidthDp = 4f

    var segmentCornerRadiusDp = 0f

    override fun draw(canvas: Canvas) {
        if (segmentNumber < 1) return

        val progress = level / 10000f
        val gapWidthPx = gapWidthDp.dpToPx().toFloat()
        val segmentCornerRadiusPx = segmentCornerRadiusDp.dpToPx().toFloat()
        val segmentWidth = (bounds.width() - (segmentNumber - 1) * gapWidthPx) / segmentNumber

        paint.color = progressColor
        segmentRect.set(0f, 0f, segmentWidth, bounds.height().toFloat())

        for (i in 0 until segmentNumber) {
            val lowProgress = i.toFloat() / segmentNumber
            val highProgress = (i + 1).toFloat() / segmentNumber

            when (progress) {
                // draw a segment of two color
                in lowProgress..highProgress -> {
                    val progressSegmentPart =
                        segmentRect.left + (progress - lowProgress) * segmentNumber * segmentWidth
                    val backgroundSegmentPart =
                        max(segmentRect.left, progressSegmentPart - 2 * segmentCornerRadiusPx)

                    // draw background part of segment
                    paint.color = backgroundColor
                    canvas.drawRoundRect(
                        backgroundSegmentPart, segmentRect.top,
                        segmentRect.right, segmentRect.bottom,
                        segmentCornerRadiusPx, segmentCornerRadiusPx,
                        paint
                    )

                    // draw progress part of segment
                    paint.color = progressColor
                    canvas.drawRoundRect(
                        segmentRect.left, segmentRect.top,
                        progressSegmentPart, segmentRect.bottom,
                        segmentCornerRadiusPx, segmentCornerRadiusPx,
                        paint
                    )

                    // change paint color to background color
                    paint.color = backgroundColor
                }
                // draw a segment of one color
                else -> canvas.drawRoundRect(
                    segmentRect, segmentCornerRadiusPx, segmentCornerRadiusPx, paint
                )
            }

            // move to next segment
            segmentRect.offset(segmentRect.width() + gapWidthPx, 0f)
        }
    }

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

}