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
import com.dynamicyield.templates.ui.offers.OfferData
import com.dynamicyield.templates.ui.offers.OffersSliderView
import kotlinx.coroutines.launch

class OffersSliderFragment : Fragment(R.layout.fragment_offers_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.OffersSlider)
                .onSuccess { choices ->
                    addOffersSlider(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("OffersSliderFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("OffersSliderFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addOffersSlider(vararg choices: DyWidgetChoice) {
        val offersSliderChoice = choices.find { it.name == DyWidgetName.OffersSlider.selector } ?: return
        val offersSliderView = DyWidgets.createDyWidgetFromChoice<OffersSliderView>(requireContext(), offersSliderChoice) ?: return

        offersSliderView.setClickListener(object : OffersSliderView.OnClickListener {
            override fun onClick(position: Int, offerData: OfferData) {
                Toast.makeText(context, "click: position=$position", Toast.LENGTH_SHORT).show()
            }
        })

        offersSliderView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val margin = 16f.dpToPx()
            setMargins(0, margin, 0, margin)
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(offersSliderView)
    }

    companion object {
        fun newInstance() = OffersSliderFragment()
    }
}