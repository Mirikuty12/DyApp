package com.dynamicyield.templates.ui.offers

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.dynamicyield.templates.ui.base.util.dpToPx

class OffersSliderView : ConstraintLayout, DyWidget {
    private var clickListener: OnClickListener? = null

    private val offersAdapter = DelegateAdapter(createOfferDelegate())

    private lateinit var recyclerView: RecyclerView
    private lateinit var pagerSnapHelper: PagerSnapHelper

    override val dyName = DyWidgetName.OffersSlider

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

    private fun dyInit(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        inflate(context, R.layout.offers_slider_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)
        pagerSnapHelper = PagerSnapHelper()

        // setup Recycler View
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = offersAdapter
        pagerSnapHelper.attachToRecyclerView(recyclerView)
    }

    fun setOfferDataList(offers: List<OfferData>) {
        offersAdapter.submitList(offers)
    }

    fun setClickListener(listener: OnClickListener) {
        clickListener = listener
    }

    private fun createOfferDelegate() = itemDelegate<OfferData> { context ->
        OfferView(context)
    }.create { parent ->
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                parent.measuredWidth - (16 + 36 + 16 + 36).toFloat().dpToPx(),
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8f.dpToPx(), 0, 8f.dpToPx(), 0)
            }
        }
    }.bind { position, offerData ->
        (itemView as? OfferView)?.apply {
            setBackgroundImage(
                url = offerData.backgroundImage,
                scaleType = offerData.backgroundImageScaleType
            )
            setLogoImage(
                url = offerData.logoImage,
                scaleType = offerData.logoImageScaleType
            )
            setLabel(
                text = offerData.labelText,
                textColor = offerData.labelTextColor,
                textSize = offerData.labelTextSize.toFloat(),
                backgroundColor = offerData.labelBackgroundColor
            )
            setTitle(
                text = offerData.titleText,
                textColor = offerData.titleTextColor,
                textSize = offerData.titleTextSize.toFloat()
            )
            setOfferClickListener {
                clickListener?.onClick(position, offerData)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, offerData: OfferData)
    }
}