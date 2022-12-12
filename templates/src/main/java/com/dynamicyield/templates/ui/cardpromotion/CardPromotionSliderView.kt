package com.dynamicyield.templates.ui.cardpromotion

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.bind
import com.dynamicyield.templates.ui.base.recyclerview.create
import com.dynamicyield.templates.ui.base.recyclerview.itemDelegate
import com.dynamicyield.templates.ui.base.util.dpToPx

class CardPromotionSliderView : ConstraintLayout, DyWidget {
    private var clickListener: OnClickListener? = null

    private val promotionAdapter = DelegateAdapter(
        createCardPromotionDelegate()
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var pagerSnapHelper: PagerSnapHelper

    override val dyName = DyWidgetName.CreditCardPromotionSlider

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
        inflate(context, R.layout.card_promotion_slider_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)
        pagerSnapHelper = PagerSnapHelper()

        // setup Recycler View
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = promotionAdapter
        pagerSnapHelper.attachToRecyclerView(recyclerView)
    }

    fun setBannerDataList(dataList: List<CardPromotionData>) {
        promotionAdapter.submitList(dataList)
    }

    fun setClickListener(listener: OnClickListener) {
        clickListener = listener
    }

    private fun createCardPromotionDelegate() = itemDelegate<CardPromotionData> { context ->
        CardPromotionView(context)
    }.create { parent ->
        itemView.apply {
            layoutParams = RecyclerView.LayoutParams(
                parent.measuredWidth - (16 + 36 + 16 + 36).toFloat().dpToPx(),
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8f.dpToPx(), 0, 8f.dpToPx(), 0)
            }
        }
    }.bind { position, cardPromotionData ->
        (itemView as? CardPromotionView)?.let { cardPromotionView ->
            cardPromotionView.setBackgroundGradient(
                cardPromotionData.topGradientColor,
                cardPromotionData.bottomGradientColor
            )
            cardPromotionView.setImage(cardPromotionData.image, cardPromotionData.imageScaleType)
            cardPromotionView.setBottomPanelColor(cardPromotionData.bottomPanelColor)
            cardPromotionView.setBottomPanelText(cardPromotionData.bottomPanelText)
            cardPromotionView.setBottomPanelTextColor(cardPromotionData.bottomPanelTextColor)
            cardPromotionView.setBottomPanelTextSize(cardPromotionData.bottomPanelTextSize.toFloat())
            cardPromotionView.setBottomPanelButtonText(cardPromotionData.bottomPanelButtonText)
            cardPromotionView.setBottomPanelButtonTextSize(cardPromotionData.bottomPanelButtonTextSize.toFloat())
            cardPromotionView.setBottomPanelButtonTextColor(cardPromotionData.bottomPanelButtonTextColor)
            cardPromotionView.setupBottomPanelButtonBackground(
                backgroundColor = cardPromotionData.bottomPanelButtonColor,
                pressedBackgroundColor = cardPromotionData.bottomPanelButtonHoverColor,
            )
            cardPromotionView.setBottomPanelButtonListener {
                clickListener?.onClick(position, cardPromotionData)
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, cardData: CardPromotionData)
    }
}
