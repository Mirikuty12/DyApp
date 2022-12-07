package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyCreditCardPromotionProperties(
    @SerialName("topGradientColor") val topGradientColor: String,
    @SerialName("bottomGradientColor") val bottomGradientColor: String,
    @SerialName("image") val image: String,
    @SerialName("imageScaleType") val imageScaleType: String,
    @SerialName("bottomPanelColor") val bottomPanelColor: String,
    @SerialName("bottomPanelText") val bottomPanelText: String,
    @SerialName("bottomPanelTextColor") val bottomPanelTextColor: String,
    @SerialName("bottomPanelTextSize") val bottomPanelTextSize: Int,
    @SerialName("bottomPanelButtonText") val bottomPanelButtonText: String,
    @SerialName("bottomPanelButtonTextSize") val bottomPanelButtonTextSize: Int,
    @SerialName("bottomPanelButtonTextColor") val bottomPanelButtonTextColor: String,
    @SerialName("bottomPanelButtonColor") val bottomPanelButtonColor: String,
    @SerialName("bottomPanelButtonHoverColor") val bottomPanelButtonHoverColor: String,
) : DyWidgetProperties

//@Serializable
//data class DyCreditCardPromotionProperties(
//    @SerialName("top") val top: String,// "",
//    @SerialName("topFontColor") val topFontColor: String,// "#ffffff",
//    @SerialName("topFontSize") val topFontSize: Int,// 16,
//    @SerialName("topBackgroundColor") val topBackgroundColor: String,// "#242424",
//    @SerialName("bottom") val bottom: String,// "Enjoy a card that earn you rewards as you shop!",
//    @SerialName("bottomFontColor") val bottomFontColor: String,// "#ffffff",
//    @SerialName("bottomFontSize") val bottomFontSize: Int,// 14,
//    @SerialName("bottomBackgroundColor") val bottomBackgroundColor: String,// "#000000",
//    @SerialName("ctaText") val ctaText: String,// "Apply",
//    @SerialName("ctaBackgroundColor") val ctaBackgroundColor: String,// "#ffffff",
//    @SerialName("image") val image: String,// "https://finance-demo.dynamicyield.com/img/rewards-card.png",
////    @SerialName("enableClose") val enableClose: Boolean,// "true"
//) : DyWidgetProperties