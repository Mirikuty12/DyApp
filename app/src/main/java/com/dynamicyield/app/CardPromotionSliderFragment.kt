package com.dynamicyield.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dynamicyield.app.core.DyWidgets
import com.dynamicyield.app.data.repository.onError
import com.dynamicyield.app.data.repository.onRawError
import com.dynamicyield.app.data.repository.onSuccess
import com.dynamicyield.app.data.source.remote.model.DyWidgetChoice
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionData
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionSliderView
import kotlinx.coroutines.launch

class CardPromotionSliderFragment : Fragment(R.layout.fragment_card_promotion_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.CreditCardPromotionSlider)
                .onSuccess { choices ->
                    addCreditCardPromotionSlider(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("CardPromotionSliderFrag", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("CardPromotionSliderFrag", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addCreditCardPromotionSlider(vararg choices: DyWidgetChoice) {
        val creditCardPromotionSliderChoice = choices.find { it.name == DyWidgetName.CreditCardPromotionSlider.selector } ?: return
        val cardPromotionSliderView = DyWidgets.createDyWidgetFromChoice<CardPromotionSliderView>(requireContext(), creditCardPromotionSliderChoice) ?: return

        cardPromotionSliderView.setClickListener(object : CardPromotionSliderView.OnClickListener {
            override fun onClick(position: Int, cardData: CardPromotionData) {
                Toast.makeText(context, "$position-${cardData.bottomPanelButtonText}", Toast.LENGTH_SHORT).show()
            }
        })

        cardPromotionSliderView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = 16f.dpToPx()
            setMargins(0, margin, 0, margin)
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(cardPromotionSliderView)
    }

    companion object {
        fun newInstance() = CardPromotionSliderFragment()
    }
}