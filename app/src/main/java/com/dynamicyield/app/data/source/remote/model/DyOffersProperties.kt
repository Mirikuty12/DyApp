package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyOffersProperties(
    @SerialName("backgroundColor") val backgroundColor: String,
    @SerialName("topCornerRadius") val topCornerRadius: Int,
    @SerialName("topHandleColor") val topHandleColor: String,
    @SerialName("title") val title: String?,
    @SerialName("titleSize") val titleSize: Int,
    @SerialName("titleColor") val titleColor: String,
    @SerialName("subtitle") val subtitle: String?,
    @SerialName("subtitleSize") val subtitleSize: Int,
    @SerialName("subtitleColor") val subtitleColor: String,
    @SerialName("offerViewMode") val offerViewMode: String,

    @SerialName("backgroundImageOffer1") val backgroundImageOffer1: String?,
    @SerialName("backgroundImageScaleTypeOffer1") val backgroundImageScaleTypeOffer1: String,
    @SerialName("logoImageOffer1") val logoImageOffer1: String?,
    @SerialName("logoImageScaleTypeOffer1") val logoImageScaleTypeOffer1: String,
    @SerialName("labelOffer1") val labelOffer1: String?,
    @SerialName("labelTextColorOffer1") val labelTextColorOffer1: String,
    @SerialName("labelTextSizeOffer1") val labelTextSizeOffer1: Int,
    @SerialName("labelBackgroundColorOffer1") val labelBackgroundColorOffer1: String,
    @SerialName("titleOffer1") val titleOffer1: String?,
    @SerialName("titleTextColorOffer1") val titleTextColorOffer1: String,
    @SerialName("titleTextSizeOffer1") val titleTextSizeOffer1: Int,

    @SerialName("backgroundImageOffer2") val backgroundImageOffer2: String?,
    @SerialName("backgroundImageScaleTypeOffer2") val backgroundImageScaleTypeOffer2: String,
    @SerialName("logoImageOffer2") val logoImageOffer2: String?,
    @SerialName("logoImageScaleTypeOffer2") val logoImageScaleTypeOffer2: String,
    @SerialName("labelOffer2") val labelOffer2: String?,
    @SerialName("labelTextColorOffer2") val labelTextColorOffer2: String,
    @SerialName("labelTextSizeOffer2") val labelTextSizeOffer2: Int,
    @SerialName("labelBackgroundColorOffer2") val labelBackgroundColorOffer2: String,
    @SerialName("titleOffer2") val titleOffer2: String?,
    @SerialName("titleTextColorOffer2") val titleTextColorOffer2: String,
    @SerialName("titleTextSizeOffer2") val titleTextSizeOffer2: Int,

    @SerialName("backgroundImageOffer3") val backgroundImageOffer3: String?,
    @SerialName("backgroundImageScaleTypeOffer3") val backgroundImageScaleTypeOffer3: String,
    @SerialName("logoImageOffer3") val logoImageOffer3: String?,
    @SerialName("logoImageScaleTypeOffer3") val logoImageScaleTypeOffer3: String,
    @SerialName("labelOffer3") val labelOffer3: String?,
    @SerialName("labelTextColorOffer3") val labelTextColorOffer3: String,
    @SerialName("labelTextSizeOffer3") val labelTextSizeOffer3: Int,
    @SerialName("labelBackgroundColorOffer3") val labelBackgroundColorOffer3: String,
    @SerialName("titleOffer3") val titleOffer3: String?,
    @SerialName("titleTextColorOffer3") val titleTextColorOffer3: String,
    @SerialName("titleTextSizeOffer3") val titleTextSizeOffer3: Int,
) : DyWidgetProperties
