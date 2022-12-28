package com.dynamicyield.app

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dynamicyield.app.core.DyWidgets
import com.dynamicyield.app.data.repository.onError
import com.dynamicyield.app.data.repository.onRawError
import com.dynamicyield.app.data.repository.onSuccess
import com.dynamicyield.app.data.source.remote.model.DyStimulationChoice
import com.dynamicyield.app.data.source.remote.model.DyWidgetChoice
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.stimulation.StimulationView
import kotlinx.coroutines.launch

class StimulationFragment : Fragment(R.layout.fragment_stimulation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.Stimulation)
                .onSuccess { choices ->
                    addStimulation(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("StimulationFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("StimulationFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addStimulation(vararg choices: DyWidgetChoice) {
        val stimulationChoice = (choices.find { it.name == DyWidgetName.Stimulation.selector } as? DyStimulationChoice) ?: return
        val stimulationView = DyWidgets.createDyWidgetFromChoice<StimulationView>(requireContext(), stimulationChoice) ?: return

        stimulationView.setStimulationListener(object : StimulationView.StimulationListener {
            override fun onClick() {
                Log.d("StimulationListener", "onClick()")
                Toast.makeText(context, "onClick()", Toast.LENGTH_SHORT).show()
            }

            override fun onTimeOver() {
                Log.d("StimulationListener", "onTimeOver()")
                Toast.makeText(context, "onTimeOver()", Toast.LENGTH_SHORT).show()
            }

            override fun onClose() {
                Log.d("StimulationListener", "onClose()")
                Toast.makeText(context, "onClose()", Toast.LENGTH_SHORT).show()

            }
        })

        val properties = stimulationChoice.variations.firstOrNull()?.payload?.properties ?: return
        val isScrollable = properties.displayRecommendation.scrollable.toBoolean()

        if (isScrollable) {
            val linearLayout = view?.findViewById<LinearLayout>(R.id.scrollableLinearLayoutContainer) ?: return
            val positionIndex = when (properties.displayRecommendation.position) {
                "bottom" -> linearLayout.childCount
                else -> 0
            }
            linearLayout.addView(stimulationView, positionIndex)
        } else {
            stimulationView.id = View.generateViewId()
            val constraintLayoutContainer = view?.findViewById<ConstraintLayout>(R.id.constraintLayoutContainer) ?: return
            constraintLayoutContainer.addView(stimulationView)

            val constraintSet = ConstraintSet()
            constraintSet.clone(constraintLayoutContainer)

            when (properties.displayRecommendation.position) {
                "bottom" -> {
                    constraintSet.apply {
                        connect(
                            stimulationView.id, ConstraintSet.START,
                            ConstraintSet.PARENT_ID, ConstraintSet.START
                        )
                        connect(
                            stimulationView.id, ConstraintSet.END,
                            ConstraintSet.PARENT_ID, ConstraintSet.END
                        )
                        connect(
                            stimulationView.id, ConstraintSet.BOTTOM,
                            ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
                        )

                        connect(
                            R.id.scrollView, ConstraintSet.BOTTOM,
                            stimulationView.id, ConstraintSet.TOP
                        )
                    }
                }
                else -> {
                    constraintSet.apply {
                        connect(
                            stimulationView.id, ConstraintSet.START,
                            ConstraintSet.PARENT_ID, ConstraintSet.START
                        )
                        connect(
                            stimulationView.id, ConstraintSet.TOP,
                            ConstraintSet.PARENT_ID, ConstraintSet.TOP
                        )
                        connect(
                            stimulationView.id, ConstraintSet.END,
                            ConstraintSet.PARENT_ID, ConstraintSet.END
                        )

                        connect(
                            R.id.scrollView, ConstraintSet.TOP,
                            stimulationView.id, ConstraintSet.BOTTOM
                        )
                    }
                }
            }

            constraintSet.applyTo(constraintLayoutContainer)
        }




    }

    companion object {
        fun newInstance() = StimulationFragment()
    }
}