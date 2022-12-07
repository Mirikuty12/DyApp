package com.dynamicyield.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() = with(view) {
        this ?: return@with

        findViewById<MaterialButton>(R.id.hpBannerBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.hpBannerSliderBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.quickActionsBtn).setOnClickListener(selectionListener)
        findViewById<MaterialButton>(R.id.quickActionsSliderBtn).setOnClickListener(selectionListener)
    }

    private val selectionListener = View.OnClickListener {
        val nextFragment = when (it.id) {
            R.id.hpBannerBtn -> CardPromotionFragment.newInstance()
            R.id.hpBannerSliderBtn -> CardPromotionSliderFragment.newInstance()
            R.id.quickActionsBtn -> QuickActionsFragment.newInstance()
            R.id.quickActionsSliderBtn -> QuickActionsSliderFragment.newInstance()
            else -> return@OnClickListener
        }

        activity?.replaceFragment(nextFragment)
    }

    companion object {
        fun newInstance() = SelectionFragment()
    }
}