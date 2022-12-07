package com.dynamicyield.app.data.source.remote.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = DyWidgetChoiceSerializer::class)
sealed class DyWidgetChoice {
    @SerialName("id")
    abstract val id: Long

    @SerialName("name")
    abstract val name: String

    @SerialName("type")
    abstract val type: String

    @SerialName("groups")
    abstract val groups: List<String>

    @SerialName("decisionId")
    abstract val decisionId: String
}

@Serializable
data class DyHpBannerChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyCreditCardPromotionProperties>>
) : DyWidgetChoice()

object DyWidgetChoiceSerializer : JsonContentPolymorphicSerializer<DyWidgetChoice>(DyWidgetChoice::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out DyWidgetChoice> {
        return when(val widgetName = element.jsonObject["name"]?.jsonPrimitive?.content) {
            com.dynamicyield.templates.ui.DyWidgetName.CreditCardPromotion.selector -> DyHpBannerChoice.serializer()
            com.dynamicyield.templates.ui.DyWidgetName.CreditCardPromotionSlider.selector -> DyHpBannerChoice.serializer()
//            "expo-articles-slider" -> DyArticlesSliderChoice.serializer()
//            "expo-offers-slider" -> DyOffersSliderChoice.serializer()
            else -> throw SerializationException("Unknown DyWidgetChoice: key 'name' does not matches any widget name. Value of key 'name' is '$widgetName'")
        }
    }
}