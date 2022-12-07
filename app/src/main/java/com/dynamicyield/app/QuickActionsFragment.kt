package com.dynamicyield.app

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.quickactions.FeaturedQuickActionData
import com.dynamicyield.templates.ui.quickactions.IQuickActionData
import com.dynamicyield.templates.ui.quickactions.QuickActionData
import com.dynamicyield.templates.ui.quickactions.QuickActionsView

class QuickActionsFragment : Fragment(R.layout.fragment_quick_actions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addQuickActions()
    }

    var quickActionsView: QuickActionsView? = null
    private fun addQuickActions() = with(view) {
        this ?: return@with

        quickActionsView = QuickActionsView(context)

        quickActionsView?.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        quickActionsView?.setQuickActions(provideQuickActionDataList())

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayoutContainer)
        linearLayout.addView(quickActionsView)
    }

    private fun provideQuickActionDataList(): List<IQuickActionData> = listOf(
        FeaturedQuickActionData(
            title = "Student Loan",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/1c1393e746bd4__deposit.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 2,
            pressedCornerRadius = 24,
            clickListener = {
                quickActionsView?.setQuickActions(
                    quickActionsView?.getQuickActions()?.shuffled() ?: emptyList()
                )
            }
        ),
        QuickActionData(
            title = "Converter",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/2e460828c3621__converter.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
        QuickActionData(
            title = "Transfer",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/6d089c8fb6a__transfer.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
        QuickActionData(
            title = "Deposit",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/1c1393e746bd4__deposit.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
        QuickActionData(
            title = "Instant Loan",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/1c1393e746bd4__deposit.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
        QuickActionData(
            title = "Converter",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/2e460828c3621__converter.png",
            imageScaleType = ImageScaleType.FILL,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
        QuickActionData(
            title = "Transfer",
            titleColor = "#21262F",
            titleSize = 18,
            image = "https://cdn.dynamicyield.com/api/8775500/images/6d089c8fb6a__transfer.png",
            imageScaleType = ImageScaleType.FIT,
            backgroundColor = "#F4F7FC",
            borderColor = "#2870F6",
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = "#D7E2F5",
            pressedBorderColor = "#2870F6",
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
            clickListener = null
        ),
    )

    companion object {
        fun newInstance() = QuickActionsFragment()
    }
}