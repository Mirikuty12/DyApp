package com.dynamicyield.templates.ui.stories

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.text.SpannableString
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.core.DyApplication
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.create
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener
import com.dynamicyield.templates.ui.base.util.createRectDrawable
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull
import com.dynamicyield.templates.ui.stories.dialog.TextLineBackgroundSpan
import com.google.android.material.imageview.ShapeableImageView
import kotlin.math.max

class StoryView : ConstraintLayout {

    private var storyListener: StoryListener? = null

    // internal views
    private lateinit var storyTimelineSpb: SegmentProgressBar
    private lateinit var closeIv: ImageView
    private lateinit var slidesRv: RecyclerView
    private lateinit var selectBtn: Button

    // timeline helper
    private val timelineProgressHelper = TimelineProgressHelper().apply {
        setProgressListener(object : TimelineProgressHelper.ProgressListener {
            override fun onSegmentStart(segmentIndex: Int) {
                if (segmentIndex in slidesAdapter.currentList.indices) {
                    currentSlideIndex = segmentIndex
                    slidesRv.scrollToPosition(segmentIndex)
                }
            }

            override fun onSegmentEnd(segmentIndex: Int) {
                if (segmentIndex >= slidesAdapter.currentList.lastIndex) {
                    storyListener?.onEnd()
                }
            }
        })
    }

    // stories adapter
    private val slidesAdapter = DelegateAdapter(createSlideDelegate())
    private val pagerSnapHelper = PagerSnapHelper()
    var currentSlideIndex: Int = -1
        private set
    private val snapOnScrollListener = SnapOnScrollListener(pagerSnapHelper) { position ->
        currentSlideIndex = position
        if (timelineProgressHelper.currentTimeIndex != position
            && timelineProgressHelper.state != TimelineProgressHelper.State.IDLE) {
            timelineProgressHelper.start(position)
        }
    }

    constructor(context: Context) : super(context) {
        dyInit(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        dyInit(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        dyInit(context, attrs, defStyleAttr)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        dyInit(context, attrs, defStyleAttr, defStyleRes)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dyInit(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        inflate(context, R.layout.story_item_layout, this)

        // init internal views
        storyTimelineSpb = findViewById(R.id.storyTimelineSpb)
        closeIv = findViewById(R.id.closeIv)
        slidesRv = findViewById(R.id.slidesRv)
        selectBtn = findViewById(R.id.selectBtn)

        // setup internal views
        // timeline
        storyTimelineSpb.setBackgroundCorners(2f)
        storyTimelineSpb.setProgressCorners(2f)
        storyTimelineSpb.setGapWidth(4f)
        timelineProgressHelper.attachToProgressBar(storyTimelineSpb)

        // close button
        closeIv.setOnClickListener {
            storyListener?.onCancel(
                currentSlideIndex,
                slidesAdapter.currentList.getOrNull(currentSlideIndex)
            )
        }

        // recycler view
        slidesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(slidesRv)
        slidesRv.addOnScrollListener(snapOnScrollListener)
        slidesRv.adapter = slidesAdapter

        slidesRv.setOnTouchListener { _, event ->
            // pause/resume animation
            when (event.actionMasked) {
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_POINTER_UP,
                MotionEvent.ACTION_OUTSIDE,
                /*MotionEvent.ACTION_CANCEL*/ ->
                    if (timelineProgressHelper.state == TimelineProgressHelper.State.PAUSED) {
                        timelineProgressHelper.resume()
                    }
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_POINTER_DOWN ->
                    if (timelineProgressHelper.state == TimelineProgressHelper.State.RUNNING) {
                        timelineProgressHelper.pause()
                    }
            }

            // tap action - move to next/previous slide
            when (event.actionMasked) {
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_POINTER_UP -> {
                    val eventDuration = event.eventTime - event.downTime
                    if (eventDuration < 500) {
                        val positionToScroll = when {
                            event.x <= (slidesRv.width * 0.33f) -> currentSlideIndex - 1
                            event.x >= (slidesRv.width * 0.67f) -> currentSlideIndex + 1
                            else -> null
                        }
                        positionToScroll?.let { slidesRv.scrollToPosition(it) }
                    }
                }
            }

            false
        }

        // select button
        selectBtn.setOnClickListener {
            storyListener?.onSelect(
                currentSlideIndex,
                slidesAdapter.currentList.getOrNull(currentSlideIndex)
            )
        }
    }

    fun setListener(listener: StoryListener?) {
        storyListener = listener
    }

    fun setBackgroundColor(color: String) {
        background = color.parseColorOrNull()?.let { ColorDrawable(it) }
    }

    fun setTimelineColors(backgroundColor: String, progressColor: String) {
        storyTimelineSpb.setColors(
            progressColor = progressColor.parseColorOrNull() ?: Color.WHITE,
            backgroundColor = backgroundColor.parseColorOrNull() ?: Color.BLACK
        )
    }

    fun setCloseBtnColor(color: String) {
        color.parseColorOrNull()?.let {
            closeIv.imageTintList = ColorStateList.valueOf(it)
        }
    }

    fun setSelectButtonText(
        buttonText: String?,
        buttonTextSize: Int,
        buttonTextColor: String,
        pressedButtonTextColor: String,
    ) = with(selectBtn) {
        isVisible = !buttonText.isNullOrBlank()
        text = buttonText

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSize.toFloat())

        val textColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                pressedButtonTextColor.parseColorOrNull() ?: Color.BLACK,
                buttonTextColor.parseColorOrNull() ?: Color.BLACK
            )
        )
        setTextColor(textColor)
    }

    fun setSelectButtonBackground(
        buttonBackgroundColor: String,
        pressedButtonBackgroundColor: String,
        buttonStrokeColor: String,
        buttonStrokeWidth: Float,
    ) = with(selectBtn) {
        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColor.parseColorOrNull(),
                strokeColor = buttonStrokeColor.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColor.parseColorOrNull(),
                strokeColor = buttonStrokeColor.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            background = StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                addState(intArrayOf(), defaultDrawable)
            }
            backgroundTintList = null
        }
    }

    fun setSlides(slides: List<SlideData>, commitCallback: Runnable? = null) {
        slidesAdapter.submitList(slides) {
            storyTimelineSpb.setSegmentNumber(slidesAdapter.itemCount)
            timelineProgressHelper.setTimeline(slidesAdapter.currentList.map { it.durationMillis })
            commitCallback?.run()
        }
    }

    fun start(slidePosition: Int = 0) {
        if (slidePosition in slidesAdapter.currentList.indices) {
            timelineProgressHelper.start(slidePosition)
        }
    }

    fun pause() {
        timelineProgressHelper.pause()
    }

    fun resume() {
        timelineProgressHelper.resume()
    }

    fun cancel() {
        timelineProgressHelper.cancel()
    }

    private fun createSlideDelegate() = itemDelegate<SlideData>(R.layout.slide_item_layout)
        .create {
            val logoSiv = itemView.findViewById<ShapeableImageView>(R.id.logoSiv)
            logoSiv.shapeAppearanceModel = logoSiv.shapeAppearanceModel.toBuilder().apply {
                setAllCornerSizes { rect -> max(rect.width(), rect.height()) / 2 }
            }.build()
        }.bind { _, slideData ->
            val backgroundFrameLayout =
                itemView.findViewById<FrameLayout>(R.id.backgroundFrameLayout)
            val storyIv = itemView.findViewById<ImageView>(R.id.storyIv)
            val logoSiv = itemView.findViewById<ShapeableImageView>(R.id.logoSiv)
            val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
            val subtitleTv = itemView.findViewById<TextView>(R.id.subtitleTv)

            // overlay
            backgroundFrameLayout.foreground = ColorDrawable(
                slideData.overlayColor.parseColorOrNull() ?: Color.TRANSPARENT
            )

            // background image
            storyIv.scaleType = when (slideData.backgroundImageScaleType) {
                ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
            }
            storyIv.load(
                data = slideData.backgroundImage,
                imageLoader = DyApplication.imageLoader(storyIv.context)
            )

            // logo image
            logoSiv.updateLayoutParams<LayoutParams> {
                verticalBias = (slideData.contentOffset / 100f).coerceIn(0f, 1f)
            }
            logoSiv.isVisible = !slideData.logoImage.isNullOrBlank()
            logoSiv.scaleType = when (slideData.logoImageScaleType) {
                ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
            }
            logoSiv.load(
                data = slideData.logoImage,
                imageLoader = DyApplication.imageLoader(logoSiv.context)
            )

            // title
            titleTv.isVisible = !slideData.titleText.isNullOrBlank()
            slideData.titleTextColor.parseColorOrNull()?.let {
                titleTv.setTextColor(it)
            }
            titleTv.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, slideData.titleTextSize.toFloat()
            )
            titleTv.text = SpannableString(slideData.titleText).apply {
                setSpan(
                    TextLineBackgroundSpan(
                        backgroundColor = slideData.titleTextBackgroundColor.parseColorOrNull()
                            ?: Color.TRANSPARENT,
                        backgroundWidth = TextLineBackgroundSpan.BackgroundWidth.WrapContent,
                    ),
                    0,
                    this.length,
                    0
                )
            }

            // title
            subtitleTv.isVisible = !slideData.subtitleText.isNullOrBlank()
            slideData.subtitleTextColor.parseColorOrNull()?.let {
                subtitleTv.setTextColor(it)
            }
            subtitleTv.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, slideData.subtitleTextSize.toFloat()
            )
            subtitleTv.text = SpannableString(slideData.subtitleText).apply {
                setSpan(
                    TextLineBackgroundSpan(
                        backgroundColor = slideData.subtitleTextBackgroundColor.parseColorOrNull()
                            ?: Color.TRANSPARENT,
                        backgroundWidth = TextLineBackgroundSpan.BackgroundWidth.WrapContent,
                    ),
                    0,
                    this.length,
                    0
                )
            }
        }

    private class TimelineProgressHelper {
        var state: State = State.IDLE
            private set(value) {
                if (field == value) return
                field = value
            }

        var currentTimeIndex = -1
            private set

        private var progressListener: ProgressListener? = null

        private var progressBar: ProgressBar? = null
        private var times: List<Long>? = null

        private var animationIsCanceled = false
        private var valueAnimator: ValueAnimator? = null
        private val updateListener = ValueAnimator.AnimatorUpdateListener { animator ->
            progressBar?.progress = (animator.animatedValue as Float).toInt()
        }
        private val animatorListener = object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator?) {
                state = State.RUNNING
                progressListener?.onSegmentStart(currentTimeIndex)
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (state != State.CANCELED) {
                    progressListener?.onSegmentEnd(currentTimeIndex)
                    val timeLastIndex = times?.lastIndex ?: return
                    when {
                        currentTimeIndex < timeLastIndex -> {
                            currentTimeIndex += 1
                            internalStart(currentTimeIndex)
                        }
                        else -> {
                            state = State.ENDED
                        }
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                state = State.CANCELED
                animationIsCanceled = true
            }

            override fun onAnimationRepeat(animation: Animator?) {}
        }
        private val animatorPauseListener = object : Animator.AnimatorPauseListener {
            override fun onAnimationPause(animation: Animator?) {
                state = State.PAUSED
            }

            override fun onAnimationResume(animation: Animator?) {
                state = State.RUNNING
            }
        }

        fun attachToProgressBar(progressBar: ProgressBar?) {
            this.progressBar = progressBar
            this.progressBar?.max = PROGRESS_MAX.toInt()
        }

        fun setTimeline(times: List<Long>?) {
            valueAnimator?.cancel()
            currentTimeIndex = -1
            this.times = times
        }

        fun setProgressListener(listener: ProgressListener?) {
            progressListener = listener
        }

        fun start(timeIndex: Int = 0) {
            valueAnimator?.cancel()
            val isTimeIndexExists = times?.indices?.contains(timeIndex) ?: false
            if (!isTimeIndexExists) return
            currentTimeIndex = timeIndex
            internalStart(currentTimeIndex)
        }

        fun pause() {
            valueAnimator?.pause()
        }

        fun resume() {
            valueAnimator?.resume()
        }

        fun cancel() {
            valueAnimator?.cancel()
        }

        private fun internalStart(timeIndex: Int) {
            animationIsCanceled = false
            val currentTimes = times ?: return
            val timeMs = currentTimes.getOrNull(timeIndex) ?: return

            valueAnimator?.cancel()
            val progressPart = PROGRESS_MAX / currentTimes.size
            valueAnimator = ValueAnimator.ofFloat(
                progressPart * timeIndex,
                progressPart * (timeIndex + 1)
            ).apply {
                interpolator = LinearInterpolator()
                duration = timeMs
                addUpdateListener(updateListener)
                addListener(animatorListener)
                addPauseListener(animatorPauseListener)
                start()
            }
        }

        enum class State { IDLE, RUNNING, PAUSED, CANCELED, ENDED; }

        interface ProgressListener {
            fun onSegmentStart(segmentIndex: Int)
            fun onSegmentEnd(segmentIndex: Int)
        }

        companion object {
            const val PROGRESS_MAX = 1000F
        }
    }

    interface StoryListener {
        fun onSelect(position: Int, slideData: SlideData?)
        fun onEnd()
        fun onCancel(position: Int, slideData: SlideData?)
    }
}