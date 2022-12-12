package com.dynamicyield.templates.ui.quickactions

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.*
import com.dynamicyield.templates.ui.base.recyclerview.multisnap.MultiSnapHelper
import com.dynamicyield.templates.ui.base.util.dpToPx

class QuickActionsSliderView : ConstraintLayout, DyWidget {
    private var clickListener: OnClickListener? = null

    private lateinit var recyclerView: RecyclerView
    private val adapter = DelegateAdapter(
        createQuickActionDelegate()
    )

    override val dyName = DyWidgetName.QuickActionsSlider

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

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        dyViewInit(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun dyViewInit(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        inflate(context, R.layout.quick_actions_slider_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)

        // setup Recycler View
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        MultiSnapHelper(interval = 1).attachToRecyclerView(recyclerView)
    }

    fun setQuickActions(items: List<QuickActionData>) {
        adapter.submitList(items)
    }

    fun setClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

    private fun createQuickActionDelegate() = itemDelegate<QuickActionData> { context ->
        QuickActionView(context)
    }.create { parent ->
        // Note: RecyclerView's horizontal padding is (`sizeOfEdgeElements` + (`marginBetweenItems` / 2))
        val sizeOfEdgeElements = 16f // visible part of edge elements
        val marginBetweenItems = 16f // space between items
        val numOfFullVisibleItems = 2
        val numOfVisibleSpaces = numOfFullVisibleItems + 1
        val width =
            (parent.measuredWidth - (2 * sizeOfEdgeElements + numOfVisibleSpaces * marginBetweenItems).dpToPx()) / numOfFullVisibleItems
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                width,
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                val horizontalM = (marginBetweenItems / 2f).dpToPx()
                val verticalM = marginBetweenItems.dpToPx()
                setMargins(horizontalM, verticalM, horizontalM, verticalM)
            }
        }
    }.bind { position, quickActionData ->
        (itemView as? QuickActionView)?.let { quickActionView ->
            quickActionView.setTitle(quickActionData.title)
            quickActionView.setTitleColor(quickActionData.titleColor)
            quickActionView.setTitleSize(quickActionData.titleSize.toFloat())
            quickActionView.setImage(quickActionData.image, quickActionData.imageScaleType)
            quickActionView.setupBackground(
                backgroundColor = quickActionData.backgroundColor,
                borderColor = quickActionData.borderColor,
                borderWidth = quickActionData.borderWidth,
                cornerRadius = quickActionData.cornerRadius,
                pressedBackgroundColor = quickActionData.pressedBackgroundColor,
                pressedBorderColor = quickActionData.pressedBorderColor,
                pressedBorderWidth = quickActionData.pressedBorderWidth,
                pressedCornerRadius = quickActionData.pressedCornerRadius,
            )
            quickActionView.setActionClickListener {
                clickListener?.onClick(position, quickActionData)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, actionData: QuickActionData)
    }
}
