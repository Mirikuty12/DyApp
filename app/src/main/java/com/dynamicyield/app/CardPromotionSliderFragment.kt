package com.dynamicyield.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dynamicyield.app.core.DyWidgets
import com.dynamicyield.app.data.repository.onError
import com.dynamicyield.app.data.repository.onRawError
import com.dynamicyield.app.data.repository.onSuccess
import com.dynamicyield.app.data.source.remote.model.DyWidgetChoice
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionData
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionSliderView
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionView
import kotlinx.coroutines.launch

class CardPromotionSliderFragment : Fragment(R.layout.fragment_card_promotion_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addHpBannerSlider()

//        viewLifecycleOwner.lifecycleScope.launch {
//            DyWidgets.chooseDyWidgets(DyWidgetName.CreditCardPromotionSlider)
//                .onSuccess { choices ->
//                    addCreditCardPromotionSlider(*choices.toTypedArray())
//                }
//                .onError { error ->
//                    Log.e("CardPromotionSliderFrag", "error: $error")
//                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
//                }
//                .onRawError { code, msg ->
//                    Log.e("CardPromotionSliderFrag", "raw error: code=$code, msg=$msg")
//                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
//                }
//        }
    }

    private fun addCreditCardPromotionSlider(vararg choices: DyWidgetChoice) {
        val creditCardPromotionSliderChoice = choices.find { it.name == DyWidgetName.CreditCardPromotionSlider.selector } ?: return
        val cardPromotionSliderView = DyWidgets.createDyWidgetFromChoice<CardPromotionSliderView>(requireContext(), creditCardPromotionSliderChoice) ?: return

        cardPromotionSliderView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = 16f.dpToPx()
            setMargins(margin)
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(cardPromotionSliderView)
    }

    private fun addHpBannerSlider() = with(view){
        this ?: return@with

        val dyHpBannerSlider = CardPromotionSliderView(context)
        dyHpBannerSlider.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = 16f.dpToPx()
            setMargins(0, margin, 0, margin)
        }

        dyHpBannerSlider.setBannerDataList(provideBannerDataList())

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutContainer)
        linearLayout.addView(dyHpBannerSlider)
    }

    private fun provideBannerDataList(): List<CardPromotionData> = listOf(
        CardPromotionData(
            topGradientColor = "#222433",
            bottomGradientColor = "#2A38353F",
            image = "https://finance-demo.dynamicyield.com/img/rewards-card.png",
            imageScaleType = ImageScaleType.FIT,
            bottomPanelColor = "#EB082863",
            bottomPanelText = "Twice the cash back. Twice as rewarding.",
            bottomPanelTextColor = "#ffffff",
            bottomPanelTextSize = 18,
            bottomPanelButtonText = "Apply",
            bottomPanelButtonTextSize = 18,
            bottomPanelButtonTextColor = "#082863",
            bottomPanelButtonColor = "#ffffff",
            bottomPanelButtonHoverColor = "#F3F2F2"
        ) {
            Toast.makeText(context, "btn 1", Toast.LENGTH_SHORT).show()
        },
        CardPromotionData(
            topGradientColor = "#5f5a34",
            bottomGradientColor = "#d6e4d1",
            image = "https://finance-demo.dynamicyield.com/img/rewards-card.png",
            imageScaleType = ImageScaleType.FILL,
            bottomPanelColor = "#084863",
            bottomPanelText = "Twice the cash back. Twice as rewarding.",
            bottomPanelTextColor = "#ffc8c8",
            bottomPanelTextSize = 18,
            bottomPanelButtonText = "Apply",
            bottomPanelButtonTextSize = 18,
            bottomPanelButtonTextColor = "#005209",
            bottomPanelButtonColor = "#c7d228",
            bottomPanelButtonHoverColor = "#ecffe1"
        ) {
            Toast.makeText(context, "btn 2", Toast.LENGTH_SHORT).show()
        },
        CardPromotionData(
            topGradientColor = "#5f5a34",
            bottomGradientColor = "#d6e4d1",
            image = null,
            imageScaleType = ImageScaleType.FIT,
            bottomPanelColor = "#084863",
            bottomPanelText = "Twice the cash back. Twice as rewarding.",
            bottomPanelTextColor = "#ffc8c8",
            bottomPanelTextSize = 18,
            bottomPanelButtonText = "Apply",
            bottomPanelButtonTextSize = 18,
            bottomPanelButtonTextColor = "#005209",
            bottomPanelButtonColor = "#c7d228",
            bottomPanelButtonHoverColor = "#ecffe1"
        ) {
            Toast.makeText(context, "btn 3", Toast.LENGTH_SHORT).show()
        },
    )

    companion object {
        fun newInstance() = CardPromotionSliderFragment()
    }
}