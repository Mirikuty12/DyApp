package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyCreditCardPromotionProperties(
    @SerialName("topGradientColor") val topGradientColor: String,
    @SerialName("bottomGradientColor") val bottomGradientColor: String,
    @SerialName("image") val image: String?,
    @SerialName("imageScaleType") val imageScaleType: String,
    @SerialName("bottomPanelColor") val bottomPanelColor: String,
    @SerialName("bottomPanelText") val bottomPanelText: String?,
    @SerialName("bottomPanelTextColor") val bottomPanelTextColor: String,
    @SerialName("bottomPanelTextSize") val bottomPanelTextSize: Int,
    @SerialName("bottomPanelButtonText") val bottomPanelButtonText: String?,
    @SerialName("bottomPanelButtonTextSize") val bottomPanelButtonTextSize: Int,
    @SerialName("bottomPanelButtonTextColor") val bottomPanelButtonTextColor: String,
    @SerialName("bottomPanelButtonColor") val bottomPanelButtonColor: String,
    @SerialName("bottomPanelButtonHoverColor") val bottomPanelButtonHoverColor: String,
) : DyWidgetProperties