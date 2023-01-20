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
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.stories.*
import kotlinx.coroutines.launch

class StoriesFragment : Fragment(R.layout.fragment_stories_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            DyWidgets.chooseDyWidgets(DyWidgetName.Stories)
                .onSuccess { choices ->
                    addStories(*choices.toTypedArray())
                }
                .onError { error ->
                    Log.e("StoriesFragment", "error: $error")
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
                .onRawError { code, msg ->
                    Log.e("StoriesFragment", "raw error: code=$code, msg=$msg")
                    Toast.makeText(context, "$code $msg", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun addStories(vararg choices: DyWidgetChoice) {
        val storiesChoice = choices.find { it.name == DyWidgetName.Stories.selector } ?: return
        val storiesView = DyWidgets.createDyWidgetFromChoice<StoriesView>(
            requireContext(),
            storiesChoice
        ) ?: return

        storiesView.setListener(object : StoriesDialogFragment.StoriesListener {
            override fun onSelect(
                storyPosition: Int,
                storyData: StoryData?,
                slidePosition: Int,
                slideData: SlideData?
            ) {
                Log.d(
                    "StoriesFragment",
                    "onSelect(): storyPosition=$storyPosition, storyData=$storyData, slidePosition=$slidePosition, slideData=$slideData"
                )
                Toast.makeText(
                    context,
                    "Select: story=$storyPosition, slide=$slidePosition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onEnd() {
                Log.d("StoriesFragment", "onEnd()")
                Toast.makeText(context, "End", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel(
                storyPosition: Int,
                storyData: StoryData?,
                slidePosition: Int?,
                slideData: SlideData?
            ) {
                Log.d(
                    "StoriesFragment",
                    "onCancel(): storyPosition=$storyPosition, storyData=$storyData, slidePosition=$slidePosition, slideData=$slideData"
                )
                Toast.makeText(
                    context,
                    "Cancel: story=$storyPosition, slide=$slidePosition",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        view?.findViewById<LinearLayout>(R.id.linearLayoutContainer)?.addView(storiesView)
    }

    companion object {
        fun newInstance() = StoriesFragment()
    }
}