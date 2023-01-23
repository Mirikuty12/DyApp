package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferProperties(
    @SerialName("backgroundImage") val backgroundImage: String? = null,
    @SerialName("backgroundImageScaleType") val backgroundImageScaleType: String? = null,
    @SerialName("logoImage") val logoImage: String? = null,
    @SerialName("logoImageScaleType") val logoImageScaleType: String? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("labelTextColor") val labelTextColor: String? = null,
    @SerialName("labelTextSize") val labelTextSize: Int? = null,
    @SerialName("labelBackgroundColor") val labelBackgroundColor: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("titleTextColor") val titleTextColor: String? = null,
    @SerialName("titleTextSize") val titleTextSize: Int? = null,
)
@Serializable
data class DyOffersProperties(
    @SerialName("backgroundColor") val backgroundColor: String? = null,
    @SerialName("topCornerRadius") val topCornerRadius: Int? = null,
    @SerialName("topHandleColor") val topHandleColor: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("titleSize") val titleSize: Int? = null,
    @SerialName("titleColor") val titleColor: String? = null,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("subtitleSize") val subtitleSize: Int? = null,
    @SerialName("subtitleColor") val subtitleColor: String? = null,
    @SerialName("offerViewMode") val offerViewMode: String? = null,

    @SerialName("offer1") val offer1: OfferProperties? = null,
    @SerialName("offer2") val offer2: OfferProperties? = null,
    @SerialName("offer3") val offer3: OfferProperties? = null,
    @SerialName("offer4") val offer4: OfferProperties? = null,
    @SerialName("offer5") val offer5: OfferProperties? = null,
    @SerialName("offer6") val offer6: OfferProperties? = null,
    @SerialName("offer7") val offer7: OfferProperties? = null,
    @SerialName("offer8") val offer8: OfferProperties? = null,
    @SerialName("offer9") val offer9: OfferProperties? = null,
    @SerialName("offer10") val offer10: OfferProperties? = null,
) : DyWidgetProperties

@Serializable
data class DyOffersSliderProperties(
    @SerialName("offer1") val offer1: OfferProperties? = null,
    @SerialName("offer2") val offer2: OfferProperties? = null,
    @SerialName("offer3") val offer3: OfferProperties? = null,
    @SerialName("offer4") val offer4: OfferProperties? = null,
    @SerialName("offer5") val offer5: OfferProperties? = null,
    @SerialName("offer6") val offer6: OfferProperties? = null,
    @SerialName("offer7") val offer7: OfferProperties? = null,
    @SerialName("offer8") val offer8: OfferProperties? = null,
    @SerialName("offer9") val offer9: OfferProperties? = null,
    @SerialName("offer10") val offer10: OfferProperties? = null,
) : DyWidgetProperties
