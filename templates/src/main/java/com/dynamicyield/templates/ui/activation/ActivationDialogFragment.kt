package com.dynamicyield.templates.ui.activation

import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import coil.load
import com.dynamicyield.templates.R
import com.dynamicyield.templates.core.DyApplication
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType
import com.dynamicyield.templates.ui.base.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.imageview.ShapeableImageView

class ActivationDialogFragment : BottomSheetDialogFragment(R.layout.activation_dialog_layout), DyWidget {

    private var activationListener: ActivationListener? = null

    // internal views
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var topHandleView: View
    private lateinit var shapeableImageView: ShapeableImageView
    private lateinit var titleTv: TextView
    private lateinit var subtitleTv: TextView
    private lateinit var activationBtn: Button

    // background properties
    private var topCornerRadius = 32f
    private var backgroundColor = "#FFFFFF"

    // top handle view
    private var topHandleColorString = "#6A7486"

    // image properties
    private var imageUrl: String? = null
    private var imageScaleType = ImageScaleType.FIT
    private var imageSizeType = ImageSizeType.MEDIUM

    // title properties
    private var titleText: String? = null
    private var titleSizeSp: Int = 36
    private var titleColorString = "#21262F"

    // subtitle properties
    private var subtitleText: String? = null
    private var subtitleSizeSp: Int = 18
    private var subtitleColorString = "#21262F"

    // button properties
    private var buttonText: String? = null
    private var buttonTextSizeSp = 18
    private var buttonTextColorString = "#FFFFFF"
    private var pressedButtonTextColorString = "#21262F"
    private var buttonBackgroundColorString = "#21262F"
    private var pressedButtonBackgroundColorString = "#00FFFFFF"
    private var buttonStrokeColorString = "#21262F"
    private var buttonStrokeWidth = 2f

    override val dyName = DyWidgetName.Activation

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.lockOrientation()

        return super.onCreateDialog(savedInstanceState).apply {
//            window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet =
                    findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
//                bottomSheet.setBackgroundResource(android.R.color.transparent)
                val rPx = topCornerRadius.dpToPx()
                bottomSheet.background = createRectDrawable(
                    fillColor = backgroundColor.parseColorOrNull(),
                    trCornerRadiusPx = rPx,
                    tlCornerRadiusPx = rPx,
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheetBehavior = (dialog as BottomSheetDialog).behavior
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        topHandleView = view.findViewById(R.id.topHandleView)
        shapeableImageView = view.findViewById(R.id.shapeableImageView)
        titleTv = view.findViewById(R.id.titleTv)
        subtitleTv = view.findViewById(R.id.subtitleTv)
        activationBtn = view.findViewById(R.id.activationBtn)

        setupTopHandleView()
        setupImage()
        setupTitle()
        setupSubtitle()
        setupActivationBtn()
    }

    override fun onCancel(dialog: DialogInterface) {
        activationListener?.onCancel()
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        activity?.unlockOrientation()
        super.onDismiss(dialog)
    }

    fun setTopHandleColor(color: String = this.topHandleColorString) {
        this.topHandleColorString = color
    }

    fun setBackgroundProps(
        backgroundColor: String = this.backgroundColor,
        topCornerRadius: Float = this.topCornerRadius
    ) {
        this.backgroundColor = backgroundColor
        this.topCornerRadius = topCornerRadius
    }

    fun setImageProps(
        imageUrl: String?,
        imageScaleType: ImageScaleType = this.imageScaleType,
        imageSizeType: ImageSizeType = this.imageSizeType
    ) {
        this.imageUrl = imageUrl
        this.imageScaleType = imageScaleType
        this.imageSizeType = imageSizeType
    }

    fun setTitleProps(
        titleText: String?,
        titleSizeSp: Int = this.titleSizeSp,
        titleColorString: String = this.titleColorString
    ) {
        this.titleText = titleText
        this.titleSizeSp = titleSizeSp
        this.titleColorString = titleColorString
    }

    fun setSubtitleProps(
        subtitleText: String?,
        subtitleSizeSp: Int = this.subtitleSizeSp,
        subtitleColorString: String = this.subtitleColorString
    ) {
        this.subtitleText = subtitleText
        this.subtitleSizeSp = subtitleSizeSp
        this.subtitleColorString = subtitleColorString
    }

    fun setButtonProps(
        buttonText: String?,
        buttonTextSizeSp: Int = this.buttonTextSizeSp,
        buttonTextColorString: String = this.buttonTextColorString,
        pressedButtonTextColorString: String = this.pressedButtonTextColorString,
        buttonBackgroundColorString: String = this.buttonBackgroundColorString,
        pressedButtonBackgroundColorString: String = this.pressedButtonBackgroundColorString,
        buttonStrokeColorString: String = this.buttonStrokeColorString,
        buttonStrokeWidth: Float = this.buttonStrokeWidth,
    ) {
        this.buttonText = buttonText
        this.buttonTextSizeSp = buttonTextSizeSp
        this.buttonTextColorString = buttonTextColorString
        this.pressedButtonTextColorString = pressedButtonTextColorString
        this.buttonBackgroundColorString = buttonBackgroundColorString
        this.pressedButtonBackgroundColorString = pressedButtonBackgroundColorString
        this.buttonStrokeColorString = buttonStrokeColorString
        this.buttonStrokeWidth = buttonStrokeWidth
    }

    fun setActivationListener(listener: ActivationListener?) {
        activationListener = listener
    }

    private fun setupTopHandleView() {
        topHandleView.background = createRectDrawable(
            fillColor = topHandleColorString.parseColorOrNull(), cornerRadiusPx = 3f.dpToPx()
        )
    }

    private fun setupImage() {
        shapeableImageView.shapeAppearanceModel = shapeableImageView.shapeAppearanceModel.toBuilder().apply {
            val radiusPx = when (imageSizeType) {
                ImageSizeType.MEDIUM -> 0f
                ImageSizeType.BIG -> topCornerRadius.dpToPx().toFloat()
            }
            setTopLeftCornerSize(radiusPx)
            setTopRightCornerSize(radiusPx)
        }.build()
        shapeableImageView.updateLayoutParams<ConstraintLayout.LayoutParams> {
            when (imageSizeType) {
                ImageSizeType.MEDIUM -> setMargins(46f.dpToPx(), 58f.dpToPx(), 46f.dpToPx(), 0)
                ImageSizeType.BIG -> setMargins(0)
            }
        }
        shapeableImageView.scaleType = when (imageScaleType) {
            ImageScaleType.FIT -> ImageView.ScaleType.FIT_CENTER
            ImageScaleType.FILL -> ImageView.ScaleType.CENTER_CROP
        }
        shapeableImageView.load(
            data = imageUrl,
            imageLoader = DyApplication.imageLoader(shapeableImageView.context)
        )
    }

    private fun setupTitle() = with(titleTv) {
        isVisible = !titleText.isNullOrBlank()
        text = titleText
        setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSizeSp.toFloat())
        setTextColor(titleColorString.parseColorOrNull() ?: Color.BLACK)
    }

    private fun setupSubtitle() = with(subtitleTv) {
        isVisible = !subtitleText.isNullOrBlank()
        text = subtitleText
        setTextSize(TypedValue.COMPLEX_UNIT_SP, subtitleSizeSp.toFloat())
        setTextColor(subtitleColorString.parseColorOrNull() ?: Color.BLACK)
    }

    private fun setupActivationBtn() = with(activationBtn) {
        isVisible = !buttonText.isNullOrBlank()
        text = buttonText

        setTextSize(TypedValue.COMPLEX_UNIT_SP, buttonTextSizeSp.toFloat())

        val buttonTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                pressedButtonTextColorString.parseColorOrNull() ?: Color.BLACK,
                buttonTextColorString.parseColorOrNull() ?: Color.BLACK
            )
        )
        setTextColor(buttonTextColor)

        setOnClickListener {
            activationListener?.onSuccess()
            dismiss()
        }

        doOnLayout {
            val defaultDrawable = createRectDrawable(
                fillColor = buttonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            val pressedDrawable = createRectDrawable(
                fillColor = pressedButtonBackgroundColorString.parseColorOrNull(),
                strokeColor = buttonStrokeColorString.parseColorOrNull(),
                strokeWidthPx = buttonStrokeWidth.dpToPx(),
                cornerRadiusPx = height / 2
            )
            background = StateListDrawable().apply {
                addState(intArrayOf(android.R.attr.state_pressed), pressedDrawable)
                addState(intArrayOf(), defaultDrawable)
            }
            backgroundTintList = null
        }
    }

    interface ActivationListener {
        fun onSuccess()
        fun onCancel()
    }

    companion object {
        const val TAG = "ActivationDialogFrag"
    }
}