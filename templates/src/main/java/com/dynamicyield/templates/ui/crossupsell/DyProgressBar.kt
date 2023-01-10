package com.dynamicyield.templates.ui.crossupsell

import android.animation.ArgbEvaluator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import com.dynamicyield.templates.ui.base.util.dpToPx

class DyProgressBar : ProgressBar {

    private val progressDrawable = ProgressDrawable()

    private val intEvaluator = IntEvaluator()
    private val argbEvaluator = ArgbEvaluator()

    private var valueAnimator: ValueAnimator? = null

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
        setProgressDrawable(progressDrawable)
    }

    fun setColors(
        @ColorInt progressColor: Int = progressDrawable.progressColor,
        @ColorInt backgroundColor: Int = progressDrawable.backgroundColor,
    ) {
        progressDrawable.progressColor = progressColor
        progressDrawable.backgroundColor = backgroundColor
    }

    fun setProgressCorners(
        topLeft: Float? = null,
        topRight: Float? = null,
        bottomRight: Float? = null,
        bottomLeft: Float? = null,
    ) {
        progressDrawable.setProgressCorners(topLeft, topRight, bottomRight, bottomLeft)
    }

    fun setBackgroundCorners(
        topLeft: Float? = null,
        topRight: Float? = null,
        bottomRight: Float? = null,
        bottomLeft: Float? = null,
    ) {
        progressDrawable.setBackgroundCorners(topLeft, topRight, bottomRight, bottomLeft)
    }

    fun animateState(
        newProgress: Int,
        @ColorInt newProgressColor: Int? = null,
        @ColorInt newBackgroundColor: Int? = null
    ) {
        valueAnimator?.cancel()

        val startProgress = progress
        val startProgressColor = progressDrawable.progressColor
        val startBackgroundColor = progressDrawable.backgroundColor

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val fraction = animator.animatedValue as Float

                if (newProgressColor != null) {
                    val fractionProgressColor = argbEvaluator.evaluate(
                        fraction, startProgressColor, newProgressColor
                    ) as Int
                    progressDrawable.progressColor = fractionProgressColor
                }

                if (newBackgroundColor != null) {
                    val fractionBackgroundColor = argbEvaluator.evaluate(
                        fraction, startBackgroundColor, newBackgroundColor
                    ) as Int
                    progressDrawable.backgroundColor = fractionBackgroundColor
                }

                val fractionProgress = intEvaluator.evaluate(fraction, startProgress, newProgress)
                if (progress != fractionProgress) {
                    progress = fractionProgress
                } else {
                    postInvalidate()
                }
            }
            start()
        }
    }

    private class ProgressDrawable : Drawable() {
        private val paint = Paint().apply {
            isAntiAlias = true
        }
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

            // draw background
            paint.color = backgroundColor
            path.reset()
            path.addRoundRect(
                bounds.left.toFloat(), bounds.top.toFloat(),
                bounds.right.toFloat(), bounds.bottom.toFloat(),
                backgroundCorners, Path.Direction.CW
            )
            canvas.drawPath(path, paint)

            // draw progress
            paint.color = progressColor
            path.reset()
            path.addRoundRect(
                bounds.left.toFloat(), bounds.top.toFloat(),
                bounds.right * progress, bounds.bottom.toFloat(),
                progressCorners, Path.Direction.CW
            )
            canvas.drawPath(path, paint)
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

    companion object {
        const val ANIMATION_DURATION = 300L
    }
}