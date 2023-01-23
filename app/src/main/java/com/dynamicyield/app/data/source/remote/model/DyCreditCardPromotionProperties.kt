package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyCreditCardPromotionProperties(
    @SerialName("topGradientColor") val topGradientColor: String? = null,
    @SerialName("bottomGradientColor") val bottomGradientColor: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,
    @SerialName("bottomPanelColor") val bottomPanelColor: String? = null,
    @SerialName("bottomPanelText") val bottomPanelText: String? = null,
    @SerialName("bottomPanelTextColor") val bottomPanelTextColor: String? = null,
    @SerialName("bottomPanelTextSize") val bottomPanelTextSize: Int? = null,
    @SerialName("bottomPanelButtonText") val bottomPanelButtonText: String? = null,
    @SerialName("bottomPanelButtonTextSize") val bottomPanelButtonTextSize: Int? = null,
    @SerialName("bottomPanelButtonTextColor") val bottomPanelButtonTextColor: String? = null,
    @SerialName("bottomPanelButtonColor") val bottomPanelButtonColor: String? = null,
    @SerialName("bottomPanelButtonHoverColor") val bottomPanelButtonHoverColor: String? = null,
) : DyWidgetProperties

@Serializable
data class DyCreditCardPromotionSliderProperties(
    @SerialName("card1") val card1: DyCreditCardPromotionProperties? = null,
    @SerialName("card2") val card2: DyCreditCardPromotionProperties? = null,
    @SerialName("card3") val card3: DyCreditCardPromotionProperties? = null,
    @SerialName("card4") val card4: DyCreditCardPromotionProperties? = null,
    @SerialName("card5") val card5: DyCreditCardPromotionProperties? = null,
    @SerialName("card6") val card6: DyCreditCardPromotionProperties? = null,
    @SerialName("card7") val card7: DyCreditCardPromotionProperties? = null,
    @SerialName("card8") val card8: DyCreditCardPromotionProperties? = null,
    @SerialName("card9") val card9: DyCreditCardPromotionProperties? = null,
    @SerialName("card10") val card10: DyCreditCardPromotionProperties? = null,
): DyWidgetProperties