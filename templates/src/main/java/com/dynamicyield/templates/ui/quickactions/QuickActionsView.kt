package com.dynamicyield.templates.ui.quickactions

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.create
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.util.dpToPx

/**
 * The view representing the DY Quick Actions template
 */
class QuickActionsView : ConstraintLayout, DyWidget {
    private var clickListener: OnClickListener? = null

    private lateinit var recyclerView: RecyclerView
    private val adapter = DelegateAdapter(
        createQuickActionDelegate(),
        createFeaturedQuickActionDelegate(),
    )

    override val dyName = DyWidgetName.QuickActions

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

    /**
     * Set a new list of IQuickActionData items
     */
    fun setQuickActions(items: List<IQuickActionData>) {
        adapter.submitList(items)
    }

    /**
     * Set the click listener
     */
    fun setClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

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
    }.bind { position, featuredQuickActionData ->
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
            featuredQuickActionView.setActionClickListener {
                clickListener?.onClick(position, featuredQuickActionData)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, actionData: IQuickActionData)
    }
}
