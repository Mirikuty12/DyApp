package com.dynamicyield.templates.ui

interface DyWidget {
    val dyName: DyWidgetName
}

enum class DyWidgetName(val selector: String) {
    CreditCardPromotion("credit-card-promotion"),
    CreditCardPromotionSlider("credit-card-promotion-slider"),
    QuickAction("quick-action"),
    QuickActionSlider("quick-action-slider");

    companion object {
        fun fromSelector(selector: String): DyWidgetName? = when (selector) {
            CreditCardPromotion.selector -> CreditCardPromotion
            CreditCardPromotionSlider.selector -> CreditCardPromotionSlider
            QuickAction.selector -> QuickAction
            QuickActionSlider.selector -> QuickActionSlider
            else -> null
        }
    }
}