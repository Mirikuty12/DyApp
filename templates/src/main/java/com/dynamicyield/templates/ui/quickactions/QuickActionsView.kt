package com.dynamicyield.templates.ui.quickactions

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.base.recyclerview.*
import com.dynamicyield.templates.ui.base.util.dpToPx

class QuickActionsView : ConstraintLayout {

    private lateinit var recyclerView: RecyclerView
    private val adapter = DelegateAdapter(
        createQuickActionDelegate(),
        createFeaturedQuickActionDelegate(),
    )

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
        inflate(context, R.layout.quick_actions_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)

        // setup Recycler View
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.currentList.getOrNull(position)?.isFeatured) {
                    true -> 2
                    else -> 1
                }
            }
        }
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    fun setQuickActions(items: List<IQuickActionData>) {
        adapter.submitList(items)
    }

    fun getQuickActions(): List<IQuickActionData> = adapter.currentList

    private fun createQuickActionDelegate() = itemDelegate<QuickActionData> { context ->
        QuickActionView(context)
    }.create { parent ->
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8f.dpToPx())
            }
        }
    }.bind { _, quickActionData ->
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
            quickActionView.setActionClickListener(quickActionData.clickListener)
        }
    }

    private fun createFeaturedQuickActionDelegate() = itemDelegate<FeaturedQuickActionData> { context ->
        FeaturedQuickActionView(context)
    }.create { parent ->
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8f.dpToPx())
            }
        }
    }.bind { _, featuredQuickActionData ->
        (itemView as? FeaturedQuickActionView)?.let { featuredQuickActionView ->
            featuredQuickActionView.setTitle(featuredQuickActionData.title)
            featuredQuickActionView.setTitleColor(featuredQuickActionData.titleColor)
            featuredQuickActionView.setTitleSize(featuredQuickActionData.titleSize.toFloat())
            featuredQuickActionView.setImage(
                featuredQuickActionData.image,
                featuredQuickActionData.imageScaleType
            )
            featuredQuickActionView.setupBackground(
                backgroundColor = featuredQuickActionData.backgroundColor,
                borderColor = featuredQuickActionData.borderColor,
                borderWidth = featuredQuickActionData.borderWidth,
                cornerRadius = featuredQuickActionData.cornerRadius,
                pressedBackgroundColor = featuredQuickActionData.pressedBackgroundColor,
                pressedBorderColor = featuredQuickActionData.pressedBorderColor,
                pressedBorderWidth = featuredQuickActionData.pressedBorderWidth,
                pressedCornerRadius = featuredQuickActionData.pressedCornerRadius,
            )
            featuredQuickActionView.setActionClickListener(featuredQuickActionData.clickListener)
        }
    }
}
