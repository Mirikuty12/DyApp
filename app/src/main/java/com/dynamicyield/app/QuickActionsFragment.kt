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
import com.dynamicyield.templates.ui.quickactions.QuickActionsView
import kotlinx.coroutines.launch

class QuickActionsFragment : Fragment(R.layout.fragment_quick_actions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addQuickActions()

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.QuickActions)
                .onSuccess { choices ->
                    addQuickActions(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("QuickActionsFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("QuickActionsFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addQuickActions(vararg choices: DyWidgetChoice) {
        val quickActionsChoice = choices.find { it.name == DyWidgetName.QuickActions.selector } ?: return
        val quickActionsView = DyWidgets.createDyWidgetFromChoice<QuickActionsView>(
            requireContext(), quickActionsChoice
        ) ?: return

        quickActionsView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )

        val linearLayout = view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)
        linearLayout?.addView(quickActionsView)
    }

    companion object {
        fun newInstance() = QuickActionsFragment()
    }
}