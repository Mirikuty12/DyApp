package com.dynamicyield.templates.ui.stories

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.create
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener
import com.dynamicyield.templates.ui.base.util.lockOrientation
import com.dynamicyield.templates.ui.base.util.unlockOrientation

class StoriesDialogFragment : DialogFragment(R.layout.stories_dialog_layout), DyWidget {

    private var storiesListener: StoriesListener? = null

    private lateinit var storiesRv: RecyclerView

    // stories adapter
    private val storiesAdapter = DelegateAdapter(createStoryDelegate())
    private val pagerSnapHelper = PagerSnapHelper()
    private var currentStoryIndex: Int = -1

    private val snapOnScrollListener2 = object : SnapOnScrollListener(
        pagerSnapHelper, NOTIFY_ON_SCROLL_STATE_IDLE
    ) {
        private var isIdle = true
        private var previousStoryIndex = -1

        override fun onSnapPositionChanged(newPosition: Int) {
            if (previousStoryIndex == -1) previousStoryIndex = newPosition

            currentStoryIndex = newPosition
            findStoryViewForAdapterPosition(currentStoryIndex)?.start()
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    isIdle = true
                    if (previousStoryIndex == currentStoryIndex) {
                        findStoryViewForAdapterPosition(previousStoryIndex)?.resume()
                    } else {
                        findStoryViewForAdapterPosition(previousStoryIndex)?.apply {
                            start(0)
                            cancel()
                        }
                    }
                    previousStoryIndex = currentStoryIndex
                }
                RecyclerView.SCROLL_STATE_DRAGGING -> if (isIdle) {
                    isIdle = false
                    findStoryViewForAdapterPosition(previousStoryIndex)?.pause()
                }
            }
        }

    }

    private val storyListener: StoryView.StoryListener = object : StoryView.StoryListener {
        override fun onSelect(position: Int, slideData: SlideData?) {
            storiesListener?.onSelect(
                storyPosition = currentStoryIndex,
                storyData = storiesAdapter.currentList.getOrNull(currentStoryIndex),
                slidePosition = position,
                slideData = slideData,
            )
            dismiss()
        }

        override fun onEnd() {
            if (currentStoryIndex < storiesAdapter.currentList.lastIndex) {
                storiesRv.smoothScrollToPosition(++currentStoryIndex)
            } else {
                storiesListener?.onEnd()
                dismiss()
            }
        }

        override fun onCancel(position: Int, slideData: SlideData?) {
            storiesListener?.onCancel(
                storyPosition = currentStoryIndex,
                storyData = storiesAdapter.currentList.getOrNull(currentStoryIndex),
                slidePosition = position,
                slideData = slideData,
            )
            dismiss()
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
        storiesRv = view.findViewById(R.id.storiesRv)

        // setup internal views
        setupRecyclerView()
    }

    override fun onResume() {
        findStoryViewForAdapterPosition(currentStoryIndex)?.resume()

        super.onResume()
    }

    override fun onPause() {
        findStoryViewForAdapterPosition(currentStoryIndex)?.pause()

        super.onPause()
    }

    override fun onCancel(dialog: DialogInterface) {
        val storyData = storiesAdapter.currentList.getOrNull(currentStoryIndex)
        val currentSlideIndex = findStoryViewForAdapterPosition(currentStoryIndex)?.currentSlideIndex
        val slideData = currentSlideIndex?.let { storyData?.fullscreenData?.slides?.getOrNull(it) }
        storiesListener?.onCancel(
            storyPosition = currentStoryIndex,
            storyData = storyData,
            slidePosition = currentSlideIndex,
            slideData = slideData,
        )
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        findStoryViewForAdapterPosition(currentStoryIndex)?.pause()
        activity?.unlockOrientation()

        super.onDismiss(dialog)
    }

    fun setListener(listener: StoriesListener?) {
        storiesListener = listener
    }

    fun setStories(stories: List<StoryData>, startStoryIndex: Int? = null) {
        storiesAdapter.submitList(stories) {
            startStoryIndex ?: return@submitList
            if (startStoryIndex in storiesAdapter.currentList.indices) {
                if (view != null) {
                    storiesRv.scrollToPosition(startStoryIndex)
                } else {
                    lifecycle.addObserver(object : LifecycleEventObserver {
                        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                            if (view != null) {
                                storiesRv.scrollToPosition(startStoryIndex)
                                lifecycle.removeObserver(this)
                            }
                        }
                    })
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        storiesRv.setHasFixedSize(true)
        storiesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(storiesRv)
        storiesRv.addOnScrollListener(snapOnScrollListener2)
        storiesRv.adapter = storiesAdapter
    }

    private fun createStoryDelegate() = itemDelegate<StoryData> { context ->
        StoryView(context)
    }.create {
        itemView.layoutParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }.bind { _, storyData ->
        val fullscreenStoryData = storyData.fullscreenData
        (itemView as? StoryView)?.apply {
            setBackgroundColor(fullscreenStoryData.backgroundColor)
            setTimelineColors(
                backgroundColor = fullscreenStoryData.timelineBackgroundColor,
                progressColor = fullscreenStoryData.timelineProgressColor,
            )
            setCloseBtnColor(fullscreenStoryData.closeButtonColor)
            setSelectButtonText(
                buttonText = fullscreenStoryData.buttonText,
                buttonTextSize = fullscreenStoryData.buttonTextSizeSp,
                buttonTextColor = fullscreenStoryData.buttonTextColorString,
                pressedButtonTextColor = fullscreenStoryData.pressedButtonTextColorString,
            )
            setSelectButtonBackground(
                buttonBackgroundColor = fullscreenStoryData.buttonBackgroundColorString,
                pressedButtonBackgroundColor = fullscreenStoryData.pressedButtonBackgroundColorString,
                buttonStrokeColor = fullscreenStoryData.buttonStrokeColorString,
                buttonStrokeWidth = fullscreenStoryData.buttonStrokeWidth
            )
            setSlides(fullscreenStoryData.slides)
            setListener(storyListener)
        }
    }

    private fun findStoryViewForAdapterPosition(position: Int): StoryView? =
        (storiesRv.findViewHolderForAdapterPosition(position)?.itemView as? StoryView)

    interface StoriesListener {
        fun onSelect(
            storyPosition: Int,
            storyData: StoryData?,
            slidePosition: Int,
            slideData: SlideData?
        )

        fun onEnd()
        fun onCancel(
            storyPosition: Int,
            storyData: StoryData?,
            slidePosition: Int?,
            slideData: SlideData?
        )
    }

    companion object {
        const val TAG = "StoriesDialog"
    }
}