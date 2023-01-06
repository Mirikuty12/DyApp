package com.dynamicyield.templates.ui.stories.slider

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.base.util.parseColorOrNull
import com.google.android.material.imageview.ShapeableImageView
import kotlin.math.max

class StoriesSliderView : ConstraintLayout, DyWidget {

    private val storiesAdapter = DelegateAdapter(createStoryDelegate())
    private lateinit var recyclerView: RecyclerView

    private var clickListener: OnClickListener? = null

    override val dyName = DyWidgetName.StoriesSlider

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
        inflate(context, R.layout.story_slider_layout, this)

        // init internal variables
        recyclerView = findViewById(R.id.recyclerView)

        // setup Recycler View
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = storiesAdapter
    }

    fun setStoryDataList(stories: List<CircleStoryData>?) {
        storiesAdapter.submitList(stories)
    }

    fun setClickListener(listener: OnClickListener?) {
        clickListener = listener
    }

    private fun createStoryDelegate() =
        itemDelegate<CircleStoryData>(R.layout.story_circle_item_layout)
            .create { parent ->
                val visibleItems = 4.5f
                val spaces = visibleItems.toInt() + 1
                val marginBetweenItems = 16f
                itemView.layoutParams = RecyclerView.LayoutParams(
                    ((parent.measuredWidth - (spaces * marginBetweenItems).dpToPx()) / visibleItems).toInt(),
                    RecyclerView.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins((marginBetweenItems / 2).dpToPx())
                }

                // adjust the round shape of the image
                val previewIv = itemView.findViewById<ShapeableImageView>(R.id.previewIv)
                previewIv.shapeAppearanceModel = previewIv.shapeAppearanceModel.toBuilder().apply {
                    setAllCornerSizes { rect -> max(rect.width(), rect.height()) / 2 }
                }.build()

            }.bind { position, storyData ->
                val containerConstraintLayout =
                    itemView.findViewById<ConstraintLayout>(R.id.containerConstraintLayout)
                val previewIv = itemView.findViewById<ShapeableImageView>(R.id.previewIv)
                val titleTv = itemView.findViewById<TextView>(R.id.titleTv)

                // set click listener
                containerConstraintLayout.setOnClickListener {
                    clickListener?.onClick(position, storyData)
                }

                // set image
                previewIv.scaleType = when (storyData.imageScaleType) {
                    ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                    ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
                }
                previewIv.load(
                    data = storyData.image,
                    imageLoader = DyApplication.imageLoader(previewIv.context)
                )
                previewIv.strokeColor = storyData.imageBorderColor.parseColorOrNull()?.let {
                    ColorStateList.valueOf(it)
                }
                val strokeWidthPx = storyData.imageBorderWidth.toFloat().dpToPx()
                previewIv.strokeWidth = strokeWidthPx.toFloat()
                previewIv.setPadding(strokeWidthPx / 2)

                // set title
                titleTv.text = storyData.titleText
                storyData.titleTextColor.parseColorOrNull()?.let { titleTv.setTextColor(it) }
                titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, storyData.titleTextSize.toFloat())
            }

    interface OnClickListener {
        fun onClick(position: Int, circleStoryData: CircleStoryData)
    }
}