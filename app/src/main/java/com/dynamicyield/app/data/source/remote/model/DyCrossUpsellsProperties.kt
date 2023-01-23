package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrossUpsellStep(
    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,
    @SerialName("imageSizeType") val imageSizeType: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("titleSize") val titleSize: Int? = null,
    @SerialName("titleColor") val titleColor: String? = null,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("subtitleSize") val subtitleSize: Int? = null,
    @SerialName("subtitleColor") val subtitleColor: String? = null,
    @SerialName("buttonColor") val buttonColor: String? = null,
    @SerialName("buttonHoverColor") val buttonHoverColor: String? = null,
    @SerialName("buttonText") val buttonText: String? = null,
    @SerialName("buttonTextSize") val buttonTextSize: Int? = null,
    @SerialName("buttonTextColor") val buttonTextColor: String? = null,
    @SerialName("progressBarColor") val progressBarColor: String? = null,
    @SerialName("progressBarBackgroundColor") val progressBarBackgroundColor: String? = null,
    @SerialName("progressTextColor") val progressTextColor: String? = null,
    @SerialName("previousTextColor") val previousTextColor: String? = null,
)

@Serializable
data class DyCrossUpsellsProperties(
    @SerialName("backgroundColor") val backgroundColor: String? = null,
    @SerialName("cornerRadius") val cornerRadius: Int? = null,
    @SerialName("closeButtonColor") val closeButtonColor: String? = null,

    @SerialName("step1") val step1: CrossUpsellStep? = null,
    @SerialName("step2") val step2: CrossUpsellStep? = null,
    @SerialName("step3") val step3: CrossUpsellStep? = null,
    @SerialName("step4") val step4: CrossUpsellStep? = null,
    @SerialName("step5") val step5: CrossUpsellStep? = null,
    @SerialName("step6") val step6: CrossUpsellStep? = null,
    @SerialName("step7") val step7: CrossUpsellStep? = null,
    @SerialName("step8") val step8: CrossUpsellStep? = null,
    @SerialName("step9") val step9: CrossUpsellStep? = null,
    @SerialName("step10") val step10: CrossUpsellStep? = null,
) : DyWidgetProperties
