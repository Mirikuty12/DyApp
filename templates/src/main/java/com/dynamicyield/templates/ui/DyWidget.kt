package com.dynamicyield.templates.ui

/**
 * Common interface for DY templates
 */
interface DyWidget {
    val dyName: DyWidgetName
}

/**
 * DY names for DY templates.
 * The string value is a selector for getting template properties from the DY admin panel.
 */
enum class DyWidgetName(val selector: String) {
    CreditCardPromotion("credit-card-promotion"),
    CreditCardPromotionSlider("credit-card-promotion-slider-2"),
    QuickActions("quick-actions-2"),
    QuickActionsSlider("quick-actions-slider-2"),
    CrossUpsell("cross-upsell"),
    Activation("activation"),
    Offers("offers"),
    OffersSlider("offers-slider"),
    Refinance("refinance"),
    RefinanceSlider("refinance-slider"),
    Stimulation("stimulation"),
    Stories("stories");

    companion object {
        fun fromSelector(selector: String): DyWidgetName? = when (selector) {
            CreditCardPromotion.selector -> CreditCardPromotion
            CreditCardPromotionSlider.selector -> CreditCardPromotionSlider
            QuickActions.selector -> QuickActions
            QuickActionsSlider.selector -> QuickActionsSlider
            CrossUpsell.selector -> CrossUpsell
            Activation.selector -> Activation
            Offers.selector -> Offers
            OffersSlider.selector -> OffersSlider
            Refinance.selector -> Refinance
            RefinanceSlider.selector -> RefinanceSlider
            Stimulation.selector -> Stimulation
            Stories.selector -> Stories
            else -> null
        }
    }
}