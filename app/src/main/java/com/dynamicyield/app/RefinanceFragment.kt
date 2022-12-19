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
import com.dynamicyield.templates.ui.refinance.RefinanceView
import kotlinx.coroutines.launch

class RefinanceFragment : Fragment(R.layout.fragment_refinance) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.Refinance)
                .onSuccess { choices ->
                    addRefinance(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("RefinanceFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("RefinanceFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addRefinance(vararg choices: DyWidgetChoice) {
        val refinanceChoice = choices.find { it.name == DyWidgetName.Refinance.selector } ?: return
        val refinanceView = DyWidgets.createDyWidgetFromChoice<RefinanceView>(requireContext(), refinanceChoice) ?: return

        refinanceView.setRefinanceListener(object : RefinanceView.RefinanceListener {
            override fun onCtaButton1Click(v: View) {
                Toast.makeText(context, "cta 1 click", Toast.LENGTH_SHORT).show()
            }

            override fun onCtaButton2Click(v: View) {
                Toast.makeText(context, "cta 2 click", Toast.LENGTH_SHORT).show()
            }
        })

        refinanceView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16f.dpToPx())
        }

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(refinanceView)
    }

    companion object {
        fun newInstance() = RefinanceFragment()
    }
}