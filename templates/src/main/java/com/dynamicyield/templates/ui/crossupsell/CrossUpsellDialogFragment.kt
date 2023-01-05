package com.dynamicyield.templates.ui.crossupsell

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType
import com.dynamicyield.templates.ui.base.recyclerview.DisableTouchRecyclerView
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.bind
import com.dynamicyield.templates.ui.base.recyclerview.delegate.adapter.itemDelegate
import com.dynamicyield.templates.ui.base.recyclerview.snap.SnapOnScrollListener
import com.dynamicyield.templates.ui.base.util.*
import kotlin.math.ceil

class CrossUpsellDialogFragment : DialogFragment(R.layout.cross_upsell_dialog_layout), DyWidget {

    private var backgroundColor = "#00FFFF"
    private var cornerRadiusPx  = 4f.dpToPx()
    private var closeButtonColor = "#FF0000"

    private var crossUpsellListener: CrossUpsellListener? = null

    private lateinit var contentCardView: CardView
    private lateinit var contentConstraintLayout: ConstraintLayout
    private lateinit var closeImageView: ImageView
    private lateinit var stepRecyclerView: DisableTouchRecyclerView
    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar

    private val stepAdapter = DelegateAdapter(createCrossUpsellDelegate())
    private val pagerSnapHelper = PagerSnapHelper()
    private var currentStepIndex: Int = -1
    private val snapOnScrollListener = SnapOnScrollListener(pagerSnapHelper) { position ->
        currentStepIndex = position
        val crossUpsellStepData = stepAdapter.currentList.getOrNull(position)
            ?: return@SnapOnScrollListener
        setupProgressBackground(
            backgroundColor = crossUpsellStepData.progressBarBackgroundColor,
            progressColor = crossUpsellStepData.progressBarColor,
            progress = ceil((((position + 1f) / stepAdapter.currentList.size) * 100)).toInt()
        )
        progressTextView.text = "${position + 1}/${stepAdapter.currentList.size}"
        crossUpsellStepData.progressTextColor.parseColorOrNull()?.let {
            progressTextView.setTextColor(it)
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
        progressBar = view.findViewById(R.id.progressBar)
        stepRecyclerView = view.findViewById(R.id.stepRecyclerView)

        // setup internal views
        // dialog background
        setupBackground()

        // close button
        setupCloseButton()

        // recycler view
        setupRecyclerView()
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
        Log.d("setupProgressBack()", "progress=$progress")
        val isInProgress = progress < 100
        val backgroundDrawable = createRectDrawable(fillColor = backgroundColor.parseColorOrNull())
        val progressDrawable = createRectDrawable(
            fillColor = progressColor.parseColorOrNull(),
            trCornerRadiusPx = if (isInProgress) 16f.dpToPx() else 0,
            brCornerRadiusPx = if (isInProgress) 16f.dpToPx() else 0,
        )
        val progressScaleDrawable = ScaleDrawable(
            progressDrawable, Gravity.LEFT, 1f, -1f
        )

        // set background
        progressBar.progressDrawable = LayerDrawable(
            arrayOf(backgroundDrawable, progressScaleDrawable)
        ).apply {
            setId(0, android.R.id.background)
            setId(1, android.R.id.progress)
        }

        // set progress with animation
        val progressAnimator = ObjectAnimator.ofInt(
            progressBar, "progress",
            progressBar.progress, progress
        )
        progressAnimator.start()
//        progressBar.progress = progress
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
                imageView.load(crossUpsellStepData.image) { crossfade(true) }

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