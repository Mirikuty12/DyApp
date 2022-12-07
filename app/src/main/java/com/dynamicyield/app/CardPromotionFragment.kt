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
import com.dynamicyield.templates.ui.base.util.dpToPx
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionView
import kotlinx.coroutines.launch

class CardPromotionFragment : Fragment(R.layout.fragment_card_promotion) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.CreditCardPromotion)
                .onSuccess { choices ->
                    addCreditCardPromotion(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("CardPromotionFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("CardPromotionFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addCreditCardPromotion(vararg choices: DyWidgetChoice) {
        val creditCardPromotionChoice =
            choices.find { it.name == DyWidgetName.CreditCardPromotion.selector } ?: return
        val creditCardPromotionView = DyWidgets.createDyWidgetFromChoice<CardPromotionView>(
            requireContext(),
            creditCardPromotionChoice
        ) ?: return
        creditCardPromotionView.setBottomPanelButtonListener {
            Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show()
        }
        creditCardPromotionView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = 16f.dpToPx()
            setMargins(margin)
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)
            ?.addView(creditCardPromotionView)
    }

    companion object {
        fun newInstance() = CardPromotionFragment()
    }
}