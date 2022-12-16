package com.dynamicyield.templates.ui.offers

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dynamicyield.templates.R
import com.dynamicyield.templates.ui.DyWidget
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.recyclerview.DelegateAdapter
import com.dynamicyield.templates.ui.base.recyclerview.bind
import com.dynamicyield.templates.ui.base.recyclerview.create
import com.dynamicyield.templates.ui.base.recyclerview.itemDelegate
import com.dynamicyield.templates.ui.base.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OffersDialogFragment : BottomSheetDialogFragment(R.layout.offers_dialog_fragment), DyWidget {

    private var offersListener: OffersListener? = null

    // internal views
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private lateinit var topHandleView: View
    private lateinit var titleTv: TextView
    private lateinit var subtitleTv: TextView
    private lateinit var offersRv: RecyclerView

    // background properties
    private var topCornerRadius = 32f
    private var backgroundColor = "#FFFFFF"

    // top handle view
    private var topHandleColorString = "#6A7486"

    // title properties
    private var titleText: String? = null
    private var titleSizeSp: Int = 28
    private var titleColorString = "#21262F"

    // subtitle properties
    private var subtitleText: String? = null
    private var subtitleSizeSp: Int = 18
    private var subtitleColorString = "#40454F"

    // view mode for offer items in recycler view
    private var offerViewMode = OfferView.OfferViewMode.RECTANGLE

    // adapter for recycle view
    private val offersDelegateAdapter = DelegateAdapter(createOfferDelegate())

    override val dyName = DyWidgetName.Offers

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

        topHandleView = view.findViewById(R.id.topHandleView)
        titleTv = view.findViewById(R.id.titleTv)
        subtitleTv = view.findViewById(R.id.subtitleTv)
        offersRv = view.findViewById(R.id.offersRv)

        setupTopHandleView()
        setupTitle()
        setupSubtitle()
        setupRecyclerView()
    }

    override fun onCancel(dialog: DialogInterface) {
        offersListener?.onCancel()
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        activity?.unlockOrientation()
        super.onDismiss(dialog)
    }

    fun setTopHandleColor(color: String) {
        this.topHandleColorString = color
    }

    fun setBackgroundProps(
        backgroundColor: String = this.backgroundColor,
        topCornerRadius: Float = this.topCornerRadius
    ) {
        this.backgroundColor = backgroundColor
        this.topCornerRadius = topCornerRadius
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

    fun setOfferViewMode(mode: OfferView.OfferViewMode) {
        offerViewMode = mode
    }

    fun setOffers(offers: List<OfferData>) {
        offersDelegateAdapter.submitList(offers)
    }

    fun setOffersListener(listener: OffersListener) {
        offersListener = listener
    }

    private fun setupTopHandleView() {
        topHandleView.background = createRectDrawable(
            fillColor = topHandleColorString.parseColorOrNull(), cornerRadiusPx = 3f.dpToPx()
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

    private fun setupRecyclerView() {
        offersRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        offersRv.adapter = offersDelegateAdapter
        val paddingVertical = when(offerViewMode) {
            OfferView.OfferViewMode.RECTANGLE -> 8f.dpToPx()
            OfferView.OfferViewMode.SQUARE -> 0
        }
        offersRv.updatePadding(top = paddingVertical, bottom = paddingVertical)
    }

    private fun createOfferDelegate() = itemDelegate<OfferData> { context ->
        OfferView(context)
    }.create { parent ->
        val marginHorizontal: Int
        val marginVertical: Int

        when (offerViewMode) {
            OfferView.OfferViewMode.RECTANGLE -> {
                marginHorizontal = 36f.dpToPx()
                marginVertical = 8f.dpToPx()
            }
            OfferView.OfferViewMode.SQUARE -> {
                marginHorizontal = 26f.dpToPx()
                marginVertical = 16f.dpToPx()
            }
        }

        (itemView as? OfferView)?.apply {
            layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(marginHorizontal, marginVertical, marginHorizontal, marginVertical)
            }
            setOfferViewMode(offerViewMode)
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
                offersListener?.onSuccess(position, offerData)
                dismiss()
            }
        }
    }

    interface OffersListener {
        fun onSuccess(position: Int, offerData: OfferData)
        fun onCancel()
    }

    companion object {
        const val TAG = "OffersDialogFragment"
    }
}