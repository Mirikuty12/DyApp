package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuickActionProperties(
    @SerialName("title") val title: String? = null,
    @SerialName("titleColor") val titleColor: String? = null,
    @SerialName("titleSize") val titleSize: Int? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("imageScaleType") val imageScaleType: String? = null,
    @SerialName("backgroundColor") val backgroundColor: String? = null,
    @SerialName("backgroundHoverColor") val backgroundHoverColor: String? = null,
    @SerialName("borderColor") val borderColor: String? = null,
    @SerialName("isFeatured") val isFeatured: String? = null,
)

@Serializable
data class DyQuickActionsProperties(
    @SerialName("action1") val action1: QuickActionProperties? = null,
    @SerialName("action2") val action2: QuickActionProperties? = null,
    @SerialName("action3") val action3: QuickActionProperties? = null,
    @SerialName("action4") val action4: QuickActionProperties? = null,
    @SerialName("action5") val action5: QuickActionProperties? = null,
    @SerialName("action6") val action6: QuickActionProperties? = null,
    @SerialName("action7") val action7: QuickActionProperties? = null,
    @SerialName("action8") val action8: QuickActionProperties? = null,
    @SerialName("action9") val action9: QuickActionProperties? = null,
    @SerialName("action10") val action10: QuickActionProperties? = null,
) : DyWidgetProperties