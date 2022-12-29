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
import com.dynamicyield.templates.ui.stories.slider.CircleStoryData
import com.dynamicyield.templates.ui.stories.slider.StoriesSliderView
import kotlinx.coroutines.launch

class StoriesSliderFragment : Fragment(R.layout.fragment_stories_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.StoriesSlider)
                .onSuccess { choices ->
                    addStoriesSlider(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("StoriesSliderFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("StoriesSliderFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addStoriesSlider(vararg choices: DyWidgetChoice) {
        val storiesSliderChoice =
            choices.find { it.name == DyWidgetName.StoriesSlider.selector } ?: return
        val storiesSliderView = DyWidgets.createDyWidgetFromChoice<StoriesSliderView>(
            requireContext(),
            storiesSliderChoice
        ) ?: return

        storiesSliderView.setClickListener(object : StoriesSliderView.OnClickListener {
            override fun onClick(position: Int, circleStoryData: CircleStoryData) {
                Toast.makeText(
                    requireContext(),
                    "pos=$position, ${circleStoryData.titleText}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(storiesSliderView)
    }

    companion object {
        fun newInstance() = StoriesSliderFragment()
    }
}