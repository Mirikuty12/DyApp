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
import com.dynamicyield.templates.ui.refinance.RefinanceData
import com.dynamicyield.templates.ui.refinance.RefinanceSliderView
import kotlinx.coroutines.launch

class RefinanceSliderFragment : Fragment(R.layout.fragment_refinance_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.RefinanceSlider)
                .onSuccess { choices ->
                    addRefinance(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("RefinanceSliderFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("RefinanceSliderFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addRefinance(vararg choices: DyWidgetChoice) {
        val refinanceSliderChoice = choices.find { it.name == DyWidgetName.RefinanceSlider.selector } ?: return
        val refinanceSliderView = DyWidgets.createDyWidgetFromChoice<RefinanceSliderView>(requireContext(), refinanceSliderChoice) ?: return

        refinanceSliderView.setRefinanceSliderListener(object : RefinanceSliderView.RefinanceSliderListener {
            override fun onCtaButton1Click(position: Int, refinanceData: RefinanceData) {
                Toast.makeText(context, "click: cta 1, item=$position", Toast.LENGTH_SHORT).show()
            }

            override fun onCtaButton2Click(position: Int, refinanceData: RefinanceData) {
                Toast.makeText(context, "click: cta 2, item=$position", Toast.LENGTH_SHORT).show()
            }
        })

        refinanceSliderView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 16f.dpToPx(), 0, 16f.dpToPx())
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(refinanceSliderView)
    }

    companion object {
        fun newInstance() = RefinanceSliderFragment()
    }
}