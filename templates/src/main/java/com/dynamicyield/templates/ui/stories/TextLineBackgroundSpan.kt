package com.dynamicyield.templates.ui.stories

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan
import androidx.annotation.ColorInt

class TextLineBackgroundSpan(
    @ColorInt val backgroundColor: Int,
    val backgroundWidth: BackgroundWidth = BackgroundWidth.MatchParent,
    val backgroundAlign: BackgroundAlign = BackgroundAlign.Left
) : LineBackgroundSpan {

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val originColor = paint.color
        paint.color = backgroundColor

        val fontMetrics = paint.fontMetrics
        val contentBottom = top + (fontMetrics.descent - fontMetrics.ascent)
        val contentTop = top - (fontMetrics.top - fontMetrics.ascent)

        when (backgroundWidth) {
            BackgroundWidth.MatchParent -> {
                canvas.drawRect(left.toFloat(), contentTop, right.toFloat(), contentBottom, paint)
            }
            BackgroundWidth.WrapContent -> {
                val textWidth = paint.measureText(text.toString(), start, end)
                val bgLeft: Float = when (backgroundAlign) {
                    BackgroundAlign.Left -> left.toFloat()
                    BackgroundAlign.Right -> right - textWidth
                    BackgroundAlign.Center -> {
                        val lineWidth = right - left
                        val space = lineWidth - textWidth
                        left + space / 2
                    }
                }
                val bgRight = bgLeft + textWidth
                canvas.drawRect(bgLeft, contentTop, bgRight, contentBottom, paint)
            }
        }

        paint.color = originColor
    }

    enum class BackgroundWidth { MatchParent, WrapContent }

    enum class BackgroundAlign { Left, Center, Right }
}