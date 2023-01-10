package com.dynamicyield.templates.ui.crossupsell

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.core.DyApplication
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener
import com.dynamicyield.templates.ui.base.util.*
import kotlin.math.ceil

class CrossUpsellDialogFragment : DialogFragment(R.layout.cross_upsell_dialog_layout), DyWidget {

    private var backgroundColor = "#00FFFF"
    private var cornerRadiusPx = 4f.dpToPx()
    private var closeButtonColor = "#FF0000"

    private var crossUpsellListener: CrossUpsellListener? = null

    private lateinit var contentCardView: CardView
    private lateinit var contentConstraintLayout: ConstraintLayout
    private lateinit var closeImageView: ImageView
    private lateinit var stepRecyclerView: RecyclerView
    private lateinit var progressTextView: TextView
    private lateinit var previousTv: TextView
    private lateinit var progressBar: DyProgressBar

    private val stepAdapter = DelegateAdapter(createCrossUpsellDelegate())
    private val pagerSnapHelper = PagerSnapHelper()
    private var currentStepIndex: Int = -1
    private val snapOnScrollListener = SnapOnScrollListener(pagerSnapHelper) { position ->
        currentStepIndex = position
        val crossUpsellStepData = stepAdapter.currentList.getOrNull(position)
            ?: return@SnapOnScrollListener

        // progress bar
        setupProgressBackground(
            backgroundColor = crossUpsellStepData.progressBarBackgroundColor,
            progressColor = crossUpsellStepData.progressBarColor,
            progress = ceil((((position + 1f) / stepAdapter.currentList.size) * 100)).toInt()
        )

        // previous text view
        previousTv.visibility = when (position > 0 && stepAdapter.currentList.size > 0) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
        crossUpsellStepData.previousTextColor.parseColorOrNull()?.let { colorInt ->
            previousTv.setTextColor(colorInt)
            previousTv.compoundDrawables.forEach { it?.setTint(colorInt) }
        }

        // steps text view
        progressTextView.text = "${position + 1}/${stepAdapter.currentList.size}"
        crossUpsellStepData.progressTextColor.parseColorOrNull()?.let { colorInt ->
            progressTextView.setTextColor(colorInt)
        }
    }

    override val dyName = DyWidgetName.CrossUpsell

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.lockOrientation()

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
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
        contentCardView = view.findViewById(R.id.contentCardView)
        contentConstraintLayout = view.findViewById(R.id.contentConstraintLayout)
        closeImageView = view.findViewById(R.id.closeIv)
        progressTextView = view.findViewById(R.id.progressTextView)
        previousTv = view.findViewById(R.id.previousTv)
        progressBar = view.findViewById(R.id.progressBar)
        stepRecyclerView = view.findViewById(R.id.stepRecyclerView)

        // setup internal views
        // dialog background
        setupBackground()

        // close button
        setupCloseButton()

        // recycler view
        setupRecyclerView()

        // previous button
        setupPreviousTv()
    }

    override fun onDismiss(dialog: DialogInterface) {
        activity?.unlockOrientation()
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        crossUpsellListener?.onCancel(currentStepIndex)
    }

    fun setListener(listener: CrossUpsellListener) {
        crossUpsellListener = listener
    }

    fun setSteps(steps: List<CrossUpsellStepData>) {
        stepAdapter.submitList(steps)
    }

    fun setCornerRadius(radiusDp: Float) {
        cornerRadiusPx = radiusDp.dpToPx()
    }

    fun setBackgroundColor(color: String) {
        backgroundColor = color
    }

    fun setCloseButtonColor(colorStr: String) {
        colorStr.parseColorOrNull() ?: return
        closeButtonColor = colorStr
    }

    private fun setupBackground() {
        contentCardView.radius = cornerRadiusPx.toFloat()
        contentConstraintLayout.background =
            backgroundColor.parseColorOrNull()?.let { ColorDrawable(it) }
    }

    private fun setupCloseButton() {
        closeButtonColor.parseColorOrNull()?.let {
            closeImageView.imageTintList = ColorStateList.valueOf(it)
        }

        closeImageView.setOnClickListener {
            crossUpsellListener?.onCancel(currentStepIndex)
            dismiss()
        }
    }

    private fun setupPreviousTv() {
        previousTv.setOnClickListener {
            showPreviousStepOrCancel()
        }
    }

    private fun setupRecyclerView() {
        stepRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pagerSnapHelper.attachToRecyclerView(stepRecyclerView)
        stepRecyclerView.addOnScrollListener(snapOnScrollListener)
        stepRecyclerView.adapter = stepAdapter
    }

    private fun setupProgressBackground(
        backgroundColor: String,
        progressColor: String,
        progress: Int
    ) {
        val isInProgress = progress < 100
        progressBar.setProgressCorners(
            topRight = if (isInProgress) 16f else 0f,
            bottomRight = if (isInProgress) 16f else 0f,
        )
        progressBar.animateState(
            newProgress = progress,
            newProgressColor = progressColor.parseColorOrNull(),
            newBackgroundColor = backgroundColor.parseColorOrNull()
        )
    }

    private fun showPreviousStepOrCancel() {
        val itemCount = stepRecyclerView.adapter?.itemCount ?: return
        val previousPosition = (currentStepIndex - 1) % itemCount


        if (previousPosition !in 0 until itemCount) {
            crossUpsellListener?.onCancel(currentStepIndex)
            dismiss()
        }
//        stepRecyclerView.smoothScrollToPosition(nextPosition)
        stepRecyclerView.scrollToPosition(previousPosition)
    }

    private fun showNextStepOrFinishSuccess() {
        val nextPosition = (currentStepIndex + 1) % (stepRecyclerView.adapter?.itemCount ?: return)

        // if now the last step call the success listener and close the dialog
        if (nextPosition == 0) {
            crossUpsellListener?.onSuccess()
            dismiss()
        }
//        stepRecyclerView.smoothScrollToPosition(nextPosition)
        stepRecyclerView.scrollToPosition(nextPosition)
    }

    private fun createCrossUpsellDelegate() =
        itemDelegate<CrossUpsellStepData>(R.layout.cross_upsell_step_layout)
            .bind { _, crossUpsellStepData ->
                val imageView = itemView.findViewById<ImageView>(R.id.imageView)
                val titleTv = itemView.findViewById<TextView>(R.id.titleTv)
                val subtitleTv = itemView.findViewById<TextView>(R.id.subtitleTv)
                val actionBtn = itemView.findViewById<Button>(R.id.actionBtn)

                // set image
                imageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    when (crossUpsellStepData.imageSizeType) {
                        ImageSizeType.MEDIUM -> {
                            matchConstraintPercentHeight = 0.15f
                            dimensionRatio = "3:2"
                            topToTop = -1
                        }
                        ImageSizeType.BIG -> {
                            matchConstraintPercentHeight = 0.35f
                            dimensionRatio = ""
                            topToTop = PARENT_ID
                        }
                    }
                }
                imageView.scaleType = when (crossUpsellStepData.imageScaleType) {
                    ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
                    ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
                }
                imageView.load(
                    data = crossUpsellStepData.image,
                    imageLoader = DyApplication.imageLoader(imageView.context)
                )

                // set title
                titleTv.text = crossUpsellStepData.title
                crossUpsellStepData.titleColor.parseColorOrNull()?.let {
                    titleTv.setTextColor(it)
                }
                titleTv.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP, crossUpsellStepData.titleSize.toFloat()
                )

                // set subtitle
                subtitleTv.text = crossUpsellStepData.subtitle
                crossUpsellStepData.subtitleColor.parseColorOrNull()?.let {
                    subtitleTv.setTextColor(it)
                }
                subtitleTv.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP, crossUpsellStepData.subtitleSize.toFloat()
                )

                // set button
                val defaultDrawable = createRectDrawable(
                    fillColor = crossUpsellStepData.buttonColor.parseColorOrNull(),
                    cornerRadiusPx = 32f.dpToPx()
                )
                val pressedDrawable = createRectDrawable(
                    fillColor = crossUpsellStepData.buttonHoverColor.parseColorOrNull(),
                    cornerRadiusPx = 32f.dpToPx()
                )
                actionBtn.background = StateListDrawable().apply {
                    addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                    addState(intArrayOf(), defaultDrawable)
                }
                actionBtn.backgroundTintList = null

                actionBtn.text = crossUpsellStepData.buttonText
                actionBtn.setTextSize(
                    TypedValue.COMPLEX_UNIT_SP, crossUpsellStepData.buttonTextSize.toFloat()
                )
                crossUpsellStepData.buttonTextColor.parseColorOrNull()?.let {
                    actionBtn.setTextColor(it)
                }

                actionBtn.setOnClickListener {
                    showNextStepOrFinishSuccess()
                }
            }

    interface CrossUpsellListener {
        fun onSuccess()
        fun onCancel(stepIndex: Int)
    }

    companion object {
        const val TAG = "CrossUpsellDialog"
    }
}