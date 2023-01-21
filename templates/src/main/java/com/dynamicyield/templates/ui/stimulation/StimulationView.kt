package com.dynamicyield.templates.ui.stimulation

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull
import java.text.DecimalFormat
import kotlin.math.ceil

class StimulationView : CardView, DyWidget {

    private lateinit var contentContainer: ConstraintLayout
    private lateinit var titleTv: TextView
    private lateinit var timerTv: TextView
    private lateinit var applyBtn: Button
    private lateinit var closeIv: ImageView

    private var expirationTimestampMillis: Long? = null
    private var countDownTimer: CountDownTimer? = null
    private val timerStringFormatter = TimerStringFormatter()

    private var stimulationListener: StimulationListener? = null

    override val dyName = DyWidgetName.Stimulation

    constructor(context: Context) : super(context) {
        dyViewInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        dyViewInit(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        dyViewInit(context, attrs, defStyleAttr)
    }

    private fun dyViewInit(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        inflate(context, R.layout.stimulation_layout, this)

        // set default layout params
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // init internal variables
        contentContainer = findViewById(R.id.contentContainer)
        titleTv = findViewById(R.id.titleTv)
        timerTv = findViewById(R.id.timerTv)
        applyBtn = findViewById(R.id.applyBtn)
        closeIv = findViewById(R.id.closeIv)

        // setup view
        setCornerRadius(0f)
        cardElevation = 0f

        // set listeners
        closeIv.setOnClickListener {
            stimulationListener?.onClose()
        }

        applyBtn.setOnClickListener {
            stimulationListener?.onClick()
        }

        contentContainer.setOnClickListener {
            if (!applyBtn.isVisible) {
                stimulationListener?.onClick()
            }
        }

    }

    override fun onAttachedToWindow() {
        updateTimer()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        countDownTimer?.cancel()
        super.onDetachedFromWindow()
    }

    fun setCornerRadius(radiusDp: Float) {
        radius = radiusDp.dpToPx().toFloat()
    }

    fun setBackgroundColor(color: String) {
        contentContainer.background = color.parseColorOrNull()?.let { ColorDrawable(it) }
    }

    fun setTitle(text: String?, textColor: String, textSize: Float) {
        titleTv.isVisible = !text.isNullOrBlank()
        titleTv.text = text
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { titleTv.setTextColor(it) }
    }

    fun setExpirationTimer(expirationTimestampSec: Long?, textColor: String, textSize: Float) {
        timerTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        textColor.parseColorOrNull()?.let { timerTv.setTextColor(it) }
        this.expirationTimestampMillis = expirationTimestampSec?.times(1000L)
        updateTimer()

        // show/hide Apply button
        updateApplyButtonVisibility()
    }

    fun setButton(
        buttonText: String?,
        buttonTextSizeSp: Float = 18f,
        buttonTextColorString: String = "#2870F6",
        buttonBackgroundColorString: String = "#FFFFFF",
        pressedButtonBackgroundColorString: String = "#F3F2F2",
    ) = with (applyBtn) {
        text = buttonText
        updateApplyButtonVisibility()

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSizeSp)
        buttonTextColorString.parseColorOrNull()?.let { setTextColor(it) }

        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColorString.parseColorOrNull(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColorString.parseColorOrNull(),
                cornerRadiusPx = height / 2
            )
            background = StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                addState(intArrayOf(), defaultDrawable)
            }
            backgroundTintList = null
        }
    }

    fun setCloseButtonColor(colorStr: String) {
        colorStr.parseColorOrNull()?.let {
            closeIv.imageTintList = ColorStateList.valueOf(it)
        }
    }

    fun setStimulationListener(listener: StimulationListener?) {
        stimulationListener = listener
    }

    private fun updateApplyButtonVisibility() {
        applyBtn.isVisible = !applyBtn.text.isNullOrBlank() && this.expirationTimestampMillis == null
    }

    private fun setTimerText(timeString: String?) {
        val currentLength = timerTv.text?.length ?: 0
        val newLength = timeString?.length ?: 0

        if (currentLength != newLength) {
            // set min width fot timer text view
            timerTv.minWidth = timeString?.replace(Regex("\\d"), "0")?.let {
                ceil(timerTv.paint.measureText(it)).toInt()
            } ?: 0
        }

        // set visibility
        timerTv.isVisible = newLength > 0

        // set text
        timerTv.text = timeString
    }

    private fun updateTimer() {
        // cancel previous timer
        countDownTimer?.cancel()

        // check end time
        if (expirationTimestampMillis == null) { // no timer needed
            setTimerText("")
            return
        }

        // check timer time
        val timerTime = (expirationTimestampMillis ?: 0) - System.currentTimeMillis()
        if (timerTime <= 0) { // time is over
            stimulationListener?.onTimeOver()
            setTimerText(timerStringFormatter.format(0))
            return
        }

        // start new timer
        countDownTimer = object : CountDownTimer(timerTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                setTimerText(timerStringFormatter.format(millisUntilFinished))
            }

            override fun onFinish() {
                stimulationListener?.onTimeOver()
            }

        }.start()
    }

    interface StimulationListener {
        fun onClick()
        fun onTimeOver()
        fun onClose()
    }

    private class TimerStringFormatter {
        private val decimalFormat = DecimalFormat("#00")
        private val array = LongArray(4) { 0 }

        fun format(millisUntilFinished: Long): String {
            var endTimeMillis = millisUntilFinished

            // days
            array[0] = endTimeMillis / dayMillis
            endTimeMillis %= dayMillis

            // hours
            array[1] = endTimeMillis / hourMillis
            endTimeMillis %= hourMillis

            // minutes
            array[2] = endTimeMillis / minuteMillis
            endTimeMillis %= minuteMillis

            // seconds
            array[3] = endTimeMillis / secondMillis
            endTimeMillis %= secondMillis



            return array/*.dropWhile { it == 0L }*/.joinToString(separator = " : ") {
                decimalFormat.format(it)
            }
        }

        companion object {
            private const val secondMillis = 1_000L
            private const val minuteMillis = 60_000L
            private const val hourMillis = 3_600_000L
            private const val dayMillis = 86_400_000L
        }
    }
}