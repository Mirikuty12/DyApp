package com.dynamicyield.templates.ui.base.recyclerview.snap

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

abstract class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    var positionChangeBehavior: Int = NOTIFY_ON_SCROLL,
) : RecyclerView.OnScrollListener() {

    private var snapPosition = RecyclerView.NO_POSITION

    abstract fun onSnapPositionChanged(newPosition: Int)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (positionChangeBehavior == NOTIFY_ON_SCROLL || snapPosition == RecyclerView.NO_POSITION) {
            dispatchSnapPositionChange(recyclerView)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if ((positionChangeBehavior == NOTIFY_ON_SCROLL_STATE_IDLE
                    && newState == RecyclerView.SCROLL_STATE_IDLE)
            || snapPosition == RecyclerView.NO_POSITION) {
            dispatchSnapPositionChange(recyclerView)
        }
    }

    private fun dispatchSnapPositionChange(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager ?: return
        val snapView = snapHelper.findSnapView(layoutManager) ?: return
        val snapPosition = layoutManager.getPosition(snapView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChanged(snapPosition)
            this.snapPosition = snapPosition
        }
    }

    companion object {
        const val NOTIFY_ON_SCROLL = 0
        const val NOTIFY_ON_SCROLL_STATE_IDLE = 1
    }
}