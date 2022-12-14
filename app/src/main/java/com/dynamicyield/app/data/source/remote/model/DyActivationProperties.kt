package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyActivationProperties(
    @SerialName("topHandleColor") val topHandleColor: String,
    @SerialName("backgroundColor") val backgroundColor: String,
    @SerialName("topCornerRadius") val topCornerRadius: Int,
    @SerialName("image") val image: String,
    @SerialName("imageScaleType") val imageScaleType: String,
    @SerialName("imageSizeType") val imageSizeType: String,
    @SerialName("title") val title: String,
    @SerialName("titleSize") val titleSize: Int,
    @SerialName("titleColor") val titleColor: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("subtitleSize") val subtitleSize: Int,
    @SerialName("subtitleColor") val subtitleColor: String,
    @SerialName("buttonText") val buttonText: String,
    @SerialName("buttonTextSize") val buttonTextSize: Int,
    @SerialName("buttonTextColor") val buttonTextColor: String,
    @SerialName("pressedButtonTextColor") val pressedButtonTextColor: String,
    @SerialName("buttonBackgroundColor") val buttonBackgroundColor: String,
    @SerialName("pressedButtonBackgroundColor") val pressedButtonBackgroundColor: String,
    @SerialName("buttonBorderColor") val buttonBorderColor: String,
    @SerialName("buttonBorderWidth") val buttonBorderWidth: Int,
) : DyWidgetProperties
