package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DyStoriesSliderProperties(
    // Story 1
    // title
    @SerialName("titleTextStory1") val titleTextStory1: String?,
    @SerialName("titleTextColorStory1") val titleTextColorStory1: String,
    @SerialName("titleTextSizeStory1") val titleTextSizeStory1: Int,
    // image
    @SerialName("imageStory1") val imageStory1: String?,
    @SerialName("imageScaleTypeStory1") val imageScaleTypeStory1: String,
    @SerialName("imageBorderColorStory1") val imageBorderColorStory1: String?,
    @SerialName("imageBorderWidthStory1") val imageBorderWidthStory1: Int?,

    // Story 2
    // title
    @SerialName("titleTextStory2") val titleTextStory2: String?,
    @SerialName("titleTextColorStory2") val titleTextColorStory2: String,
    @SerialName("titleTextSizeStory2") val titleTextSizeStory2: Int,
    // image
    @SerialName("imageStory2") val imageStory2: String?,
    @SerialName("imageScaleTypeStory2") val imageScaleTypeStory2: String,
    @SerialName("imageBorderColorStory2") val imageBorderColorStory2: String?,
    @SerialName("imageBorderWidthStory2") val imageBorderWidthStory2: Int?,

    // Story 3
    // title
    @SerialName("titleTextStory3") val titleTextStory3: String?,
    @SerialName("titleTextColorStory3") val titleTextColorStory3: String,
    @SerialName("titleTextSizeStory3") val titleTextSizeStory3: Int,
    // image
    @SerialName("imageStory3") val imageStory3: String?,
    @SerialName("imageScaleTypeStory3") val imageScaleTypeStory3: String,
    @SerialName("imageBorderColorStory3") val imageBorderColorStory3: String?,
    @SerialName("imageBorderWidthStory3") val imageBorderWidthStory3: Int?,

    // Story 4
    // title
    @SerialName("titleTextStory4") val titleTextStory4: String?,
    @SerialName("titleTextColorStory4") val titleTextColorStory4: String,
    @SerialName("titleTextSizeStory4") val titleTextSizeStory4: Int,
    // image
    @SerialName("imageStory4") val imageStory4: String?,
    @SerialName("imageScaleTypeStory4") val imageScaleTypeStory4: String,
    @SerialName("imageBorderColorStory4") val imageBorderColorStory4: String?,
    @SerialName("imageBorderWidthStory4") val imageBorderWidthStory4: Int?,

    // Story 5
    // title
    @SerialName("titleTextStory5") val titleTextStory5: String?,
    @SerialName("titleTextColorStory5") val titleTextColorStory5: String,
    @SerialName("titleTextSizeStory5") val titleTextSizeStory5: Int,
    // image
    @SerialName("imageStory5") val imageStory5: String?,
    @SerialName("imageScaleTypeStory5") val imageScaleTypeStory5: String,
    @SerialName("imageBorderColorStory5") val imageBorderColorStory5: String?,
    @SerialName("imageBorderWidthStory5") val imageBorderWidthStory5: Int?,

    // Story 6
    // title
    @SerialName("titleTextStory6") val titleTextStory6: String?,
    @SerialName("titleTextColorStory6") val titleTextColorStory6: String,
    @SerialName("titleTextSizeStory6") val titleTextSizeStory6: Int,
    // image
    @SerialName("imageStory6") val imageStory6: String?,
    @SerialName("imageScaleTypeStory6") val imageScaleTypeStory6: String,
    @SerialName("imageBorderColorStory6") val imageBorderColorStory6: String?,
    @SerialName("imageBorderWidthStory6") val imageBorderWidthStory6: Int?,

) : DyWidgetProperties
