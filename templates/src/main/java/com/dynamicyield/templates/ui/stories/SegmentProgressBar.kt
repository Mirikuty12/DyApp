package com.dynamicyield.templates.ui.stories

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import com.dynamicyield.templates.ui.base.util.dpToPx
import kotlin.math.max

class SegmentProgressBar : ProgressBar {
    private val segmentProgressDrawable = ProgressDrawable()

    constructor(context: Context?) : super(context) {
        dyViewInit(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        dyViewInit(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        dyViewInit(context, attrs, defStyleAttr)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        dyViewInit(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun dyViewInit(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        progressDrawable = segmentProgressDrawable
    }

    fun setColors(
        @ColorInt progressColor: Int = segmentProgressDrawable.progressColor,
        @ColorInt backgroundColor: Int = segmentProgressDrawable.backgroundColor,
    ) {
        segmentProgressDrawable.progressColor = progressColor
        segmentProgressDrawable.backgroundColor = backgroundColor
    }

    fun setProgressCorners(radius: Float) {
        setProgressCorners(radius, radius, radius, radius)
    }

    fun setProgressCorners(
        topLeft: Float? = null,
        topRight: Float? = null,
        bottomRight: Float? = null,
        bottomLeft: Float? = null,
    ) {
        segmentProgressDrawable.setProgressCorners(topLeft, topRight, bottomRight, bottomLeft)
    }

    fun setBackgroundCorners(radius: Float) {
        setBackgroundCorners(radius, radius, radius, radius)
    }

    fun setBackgroundCorners(
        topLeft: Float? = null,
        topRight: Float? = null,
        bottomRight: Float? = null,
        bottomLeft: Float? = null,
    ) {
        segmentProgressDrawable.setBackgroundCorners(topLeft, topRight, bottomRight, bottomLeft)
    }

    fun setSegmentNumber(segmentNumber: Int) {
        segmentProgressDrawable.segmentNumber = segmentNumber
    }

    fun setGapWidth(width: Float) {
        segmentProgressDrawable.gapWidthDp = width
    }

    private class ProgressDrawable : Drawable() {
        private val paint = Paint().apply {
            isAntiAlias = true
        }
        private val segmentRect = RectF()
        private val path = Path()
        private val progressCorners = floatArrayOf(
            0f, 0f,   // Top left radius in px
            0f, 0f,   // Top right radius in px
            0f, 0f,   // Bottom right radius in px
            0f, 0f    // Bottom left radius in px
        )
        private val backgroundCorners = floatArrayOf(
            0f, 0f,   // Top left radius in px
            0f, 0f,   // Top right radius in px
            0f, 0f,   // Bottom right radius in px
            0f, 0f    // Bottom left radius in px
        )

        @ColorInt
        var backgroundColor: Int = Color.GRAY

        @ColorInt
        var progressColor: Int = Color.BLUE

        var segmentNumber = 1
            set(value) {
                field = max(value, 0)
            }

        var gapWidthDp = 4f
            set(value) {
                field = max(value, 0f)
            }

        fun setProgressCorners(
            topLeft: Float? = null,
            topRight: Float? = null,
            bottomRight: Float? = null,
            bottomLeft: Float? = null,
        ) {
            // top left
            topLeft?.dpToPx()?.toFloat()?.let {
                progressCorners[0] = it
                progressCorners[1] = it
            }

            // top right
            topRight?.dpToPx()?.toFloat()?.let {
                progressCorners[2] = it
                progressCorners[3] = it
            }

            // bottom right
            bottomRight?.dpToPx()?.toFloat()?.let {
                progressCorners[4] = it
                progressCorners[5] = it
            }

            // bottom left
            bottomLeft?.dpToPx()?.toFloat()?.let {
                progressCorners[6] = it
                progressCorners[7] = it
            }
        }

        fun setBackgroundCorners(
            topLeft: Float? = null,
            topRight: Float? = null,
            bottomRight: Float? = null,
            bottomLeft: Float? = null,
        ) {
            // top left
            topLeft?.dpToPx()?.toFloat()?.let {
                backgroundCorners[0] = it
                backgroundCorners[1] = it
            }

            // top right
            topRight?.dpToPx()?.toFloat()?.let {
                backgroundCorners[2] = it
                backgroundCorners[3] = it
            }

            // bottom right
            bottomRight?.dpToPx()?.toFloat()?.let {
                backgroundCorners[4] = it
                backgroundCorners[5] = it
            }

            // bottom left
            bottomLeft?.dpToPx()?.toFloat()?.let {
                backgroundCorners[6] = it
                backgroundCorners[7] = it
            }
        }

        override fun draw(canvas: Canvas) {
            val progress = level / 10000f
            val gapWidthPx = gapWidthDp.dpToPx().toFloat()
            val segmentWidth = (bounds.width() - (segmentNumber - 1) * gapWidthPx) / segmentNumber

            segmentRect.set(0f, 0f, segmentWidth, bounds.height().toFloat())

            for (i in 0 until segmentNumber) {
                val lowProgress = i.toFloat() / segmentNumber
                val highProgress = (i + 1).toFloat() / segmentNumber

                when {
                    progress < lowProgress -> {
                        // draw background
                        paint.color = backgroundColor
                        path.reset()
                        path.addRoundRect(segmentRect, backgroundCorners, Path.Direction.CW)
                        canvas.drawPath(path, paint)
                    }

                    progress in lowProgress..highProgress -> {
                        // draw background
                        paint.color = backgroundColor
                        path.reset()
                        path.addRoundRect(segmentRect, backgroundCorners, Path.Direction.CW)
                        canvas.drawPath(path, paint)

                        // draw progress
                        val progressSegmentPart =
                            segmentRect.left + (progress - lowProgress) * segmentNumber * segmentWidth
                        paint.color = progressColor
                        path.reset()
                        path.addRoundRect(
                            segmentRect.left, segmentRect.top,
                            progressSegmentPart, segmentRect.bottom,
                            progressCorners, Path.Direction.CW
                        )
                        canvas.drawPath(path, paint)
                    }

                    progress > highProgress -> {
                        // draw background
                        paint.color = backgroundColor
                        path.reset()
                        path.addRoundRect(segmentRect, backgroundCorners, Path.Direction.CW)
                        canvas.drawPath(path, paint)

                        // draw progress
                        paint.color = progressColor
                        path.reset()
                        path.addRoundRect(segmentRect, progressCorners, Path.Direction.CW)
                        canvas.drawPath(path, paint)
                    }
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
}