package com.dynamicyield.templates.ui.stories.dialog

import android.animation.Animator
import android.animation.Animator.AnimatorPauseListener
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.text.SpannableString
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.core.DyApplication
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.create
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener.Companion.NOTIFY_ON_SCROLL_STATE_IDLE
import com.dynamicyield.templates.ui.base.util.*
import com.google.android.material.imageview.ShapeableImageView
import kotlin.math.max

class StoriesDialogFragment : DialogFragment(R.layout.story_dialog_layout), DyWidget {

    private var storiesListener: StoriesListener? = null

    // background color
    private var backgroundColor = "#000000"

    // close button color
    private var closeButtonColor = "#FF0000"

    // timeline properties
    private var timelineBackgroundColor = "#80F4F4F4"
    private var timelineProgressColor = "#F4F4F4"

    // button properties
    private var buttonText: String? = null
    private var buttonTextSizeSp = 18
    private var buttonTextColorString = "#000000"
    private var pressedButtonTextColorString = "#FFFFFF"
    private var buttonBackgroundColorString = "#FFFFFF"
    private var pressedButtonBackgroundColorString = "#00FFFFFF"
    private var buttonStrokeColorString = "#FFFFFF"
    private var buttonStrokeWidth = 2f

    // internal views
    private lateinit var containerConstraintLayout: ConstraintLayout
    private lateinit var storiesProgressBar: ProgressBar
    private lateinit var closeIv: ImageView
    private lateinit var storiesRv: RecyclerView
    private lateinit var learnStoryBtn: Button

    // timeline helper
    private val timelineProgressHelper = TimelineProgressHelper().apply {
        setProgressListener(object : TimelineProgressHelper.ProgressListener {
            override fun onSegmentStart(segmentIndex: Int) {
                currentStoryIndex = segmentIndex
                if (view != null &&
                    viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) &&
                    segmentIndex in storiesAdapter.currentList.indices
                ) {
                    storiesRv.smoothScrollToPosition(segmentIndex)
                }
            }

            override fun onSegmentEnd(segmentIndex: Int) {
                if (segmentIndex >= storiesAdapter.currentList.lastIndex) {
                    storiesListener?.onEnd()
                    dismiss()
                }
            }
        })
    }

    // stories adapter
    private val storiesAdapter = DelegateAdapter(createStoryDelegate())
    private val pagerSnapHelper = PagerSnapHelper()
    private var currentStoryIndex: Int = -1
    private val snapOnScrollListener = SnapOnScrollListener(
        pagerSnapHelper, NOTIFY_ON_SCROLL_STATE_IDLE
    ) { position ->
        currentStoryIndex = position
        if (timelineProgressHelper.currentTimeIndex != position) {
            timelineProgressHelper.start(position)
        }
    }

    override val dyName = DyWidgetName.Stories

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.lockOrientation()

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setDimAmount(0f)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup the display mode
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        // init internal views
        containerConstraintLayout = view.findViewById(R.id.containerConstraintLayout)
        storiesProgressBar = view.findViewById(R.id.storiesProgressBar)
        closeIv = view.findViewById(R.id.closeIv)
        storiesRv = view.findViewById(R.id.storiesRv)
        learnStoryBtn = view.findViewById(R.id.learnStoryBtn)

        // setup internal views
        // dialog background
        setupBackground()

        // close button
        setupCloseButton()

        // recycler view
        setupRecyclerView()

        // setup timeline progress
        setupTimelineProgress()

        // learn more button
        setupChooseButton()
    }

    override fun onResume() {
        timelineProgressHelper.resume()
        super.onResume()
    }

    override fun onPause() {
        timelineProgressHelper.pause()
        super.onPause()
    }

    override fun onCancel(dialog: DialogInterface) {
        storiesListener?.onCancel(
            currentStoryIndex,
            storiesAdapter.currentList.getOrNull(currentStoryIndex)
        )
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        timelineProgressHelper.cancel()
        activity?.unlockOrientation()
        super.onDismiss(dialog)
    }

    fun setListener(listener: StoriesListener?) {
        storiesListener = listener
    }

    fun setBackgroundColor(color: String) {
        backgroundColor = color
    }

    fun setCloseBtnColor(color: String) {
        color.parseColorOrNull() ?: return
        closeButtonColor = color
    }

    fun setTimelineColors(
        backgroundColor: String = timelineBackgroundColor,
        progressColor: String = timelineProgressColor
    ) {
        timelineBackgroundColor = backgroundColor
        timelineProgressColor = progressColor
    }

    fun setStories(stories: List<StoryData>) {
        storiesAdapter.submitList(stories) {
            if (view != null && viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                setupTimelineProgress()
            }
        }
    }

    fun setChooseButtonProps(
        buttonText: String? = this.buttonText,
        buttonTextSize: Int = this.buttonTextSizeSp,
        buttonTextColor: String = this.buttonTextColorString,
        pressedButtonTextColor: String = this.pressedButtonTextColorString,
        buttonBackgroundColor: String = this.buttonBackgroundColorString,
        pressedButtonBackgroundColor: String = this.pressedButtonBackgroundColorString,
        buttonStrokeColor: String = this.buttonStrokeColorString,
        buttonStrokeWidth: Float = this.buttonStrokeWidth,
    ) {
        this.buttonText = buttonText
        this.buttonTextSizeSp = buttonTextSize
        this.buttonTextColorString = buttonTextColor
        this.pressedButtonTextColorString = pressedButtonTextColor
        this.buttonBackgroundColorString = buttonBackgroundColor
        this.pressedButtonBackgroundColorString = pressedButtonBackgroundColor
        this.buttonStrokeColorString = buttonStrokeColor
        this.buttonStrokeWidth = buttonStrokeWidth
    }

    private fun setupBackground() {
        containerConstraintLayout.background =
            backgroundColor.parseColorOrNull()?.let { ColorDrawable(it) }
    }

    private fun setupTimelineProgress() {
        timelineProgressHelper.setTimeline(storiesAdapter.currentList.map { it.timeMillis })
        storiesProgressBar.progressDrawable = ProgressDrawable().apply {
            segmentNumber = storiesAdapter.itemCount
            segmentCornerRadiusDp = 2f
            backgroundColor = timelineBackgroundColor.parseColorOrNull() ?: Color.BLACK
            progressColor = timelineProgressColor.parseColorOrNull() ?: Color.WHITE
        }
        timelineProgressHelper.attachToProgressBar(storiesProgressBar)
        timelineProgressHelper.start()
    }

    private fun setupCloseButton() {
        closeButtonColor.parseColorOrNull()?.let {
            closeIv.imageTintList = ColorStateList.valueOf(it)
        }

        closeIv.setOnClickListener {
            storiesListener?.onCancel(
                currentStoryIndex,
                storiesAdapter.currentList.getOrNull(currentStoryIndex)
            )
            dismiss()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        storiesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(storiesRv)
        storiesRv.addOnScrollListener(snapOnScrollListener)
        storiesRv.adapter = storiesAdapter

        storiesRv.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_POINTER_UP,
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_OUTSIDE ->
                    if (timelineProgressHelper.state == TimelineProgressHelper.State.PAUSED) {
                        timelineProgressHelper.resume()
                    }
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_POINTER_DOWN ->
                    if (timelineProgressHelper.state == TimelineProgressHelper.State.RUNNING) {
                        timelineProgressHelper.pause()
                    }
            }
            false
        }
    }

    private fun setupChooseButton() = with(learnStoryBtn) {
        isVisible = !buttonText.isNullOrBlank()
        text = buttonText

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSizeSp.toFloat())

        val buttonTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                pressedButtonTextColorString.parseColorOrNull() ?: Color.BLACK,
                buttonTextColorString.parseColorOrNull() ?: Color.BLACK
            )
        )
        setTextColor(buttonTextColor)

        setOnClickListener {
            storiesListener?.onChoose(
                currentStoryIndex,
                storiesAdapter.currentList.getOrNull(currentStoryIndex)
            )
            dismiss()
        }

        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
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

    private fun createStoryDelegate() = itemDelegate<StoryData>(R.layout.story_item_layout)
        .create { parent ->
            val logoSiv = itemView.findViewById<ShapeableImageView>(R.id.logoSiv)
            logoSiv.shapeAppearanceModel = logoSiv.shapeAppearanceModel.toBuilder().apply {
                setAllCornerSizes { rect -> max(rect.width(), rect.height()) / 2 }
            }.build()
        }.bind { _, storyData ->
            val backgroundFrameLayout =
                itemView.findViewById<FrameLayout>(R.id.backgroundFrameLayout)
            val storyIv = itemView.findViewById<ImageView>(R.id.storyIv)
            val logoSiv = itemView.findViewById<ShapeableImageView>(R.id.logoSiv)
            val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
            val subtitleTv = itemView.findViewById<TextView>(R.id.subtitleTv)

            // overlay
            backgroundFrameLayout.foreground = ColorDrawable(
                storyData.overlayColor.parseColorOrNull() ?: Color.TRANSPARENT
            )

            // background image
            storyIv.scaleType = when (storyData.backgroundImageScaleType) {
                ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
            }
            storyIv.load(
                data = storyData.backgroundImage,
                imageLoader = DyApplication.imageLoader(storyIv.context)
            )

            // logo image
            logoSiv.updateLayoutParams<ConstraintLayout.LayoutParams> {
                verticalBias = storyData.contentOffset.coerceIn(0f, 1f)
            }
            logoSiv.isVisible = !storyData.logoImage.isNullOrBlank()
            logoSiv.scaleType = when (storyData.logoImageScaleType) {
                ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
            }
            logoSiv.load(
                data = storyData.logoImage,
                imageLoader = DyApplication.imageLoader(logoSiv.context)
            )

            // title
            titleTv.isVisible = !storyData.titleText.isNullOrBlank()
            storyData.titleTextColor.parseColorOrNull()?.let {
                titleTv.setTextColor(it)
            }
            titleTv.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, storyData.titleTextSize.toFloat()
            )
            titleTv.text = SpannableString(storyData.titleText).apply {
                setSpan(
                    TextLineBackgroundSpan(
                        backgroundColor = storyData.titleTextBackgroundColor.parseColorOrNull()
                            ?: Color.TRANSPARENT,
                        backgroundWidth = TextLineBackgroundSpan.BackgroundWidth.WrapContent,
                    ),
                    0,
                    this.length,
                    0
                )
            }

            // title
            subtitleTv.isVisible = !storyData.subtitleText.isNullOrBlank()
            storyData.subtitleTextColor.parseColorOrNull()?.let {
                subtitleTv.setTextColor(it)
            }
            subtitleTv.setTextSize(
                TypedValue.COMPLEX_UNIT_SP, storyData.subtitleTextSize.toFloat()
            )
            subtitleTv.text = SpannableString(storyData.subtitleText).apply {
                setSpan(
                    TextLineBackgroundSpan(
                        backgroundColor = storyData.subtitleTextBackgroundColor.parseColorOrNull()
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
        private val animatorPauseListener = object : AnimatorPauseListener {
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

    interface StoriesListener {
        fun onChoose(position: Int, storyData: StoryData?)
        fun onEnd()
        fun onCancel(position: Int, storyData: StoryData?)
    }

    companion object {
        const val TAG = "StoriesDialog"
    }
}