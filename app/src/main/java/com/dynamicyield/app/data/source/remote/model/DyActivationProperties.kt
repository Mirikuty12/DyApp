package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyActivationProperties(
    @SerialName("topHandleColor") val topHandleColor: String? = null,
    @SerialName("backgroundColor") val backgroundColor: String? = null,
    @SerialName("topCornerRadius") val topCornerRadius: Int? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,
    @SerialName("imageSizeType") val imageSizeType: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("titleSize") val titleSize: Int? = null,
    @SerialName("titleColor") val titleColor: String? = null,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("subtitleSize") val subtitleSize: Int? = null,
    @SerialName("subtitleColor") val subtitleColor: String? = null,
    @SerialName("buttonText") val buttonText: String? = null,
    @SerialName("buttonTextSize") val buttonTextSize: Int? = null,
    @SerialName("buttonTextColor") val buttonTextColor: String? = null,
    @SerialName("pressedButtonTextColor") val pressedButtonTextColor: String? = null,
    @SerialName("buttonBackgroundColor") val buttonBackgroundColor: String? = null,
    @SerialName("pressedButtonBackgroundColor") val pressedButtonBackgroundColor: String? = null,
    @SerialName("buttonBorderColor") val buttonBorderColor: String? = null,
    @SerialName("buttonBorderWidth") val buttonBorderWidth: Int? = null,
) : DyWidgetProperties
