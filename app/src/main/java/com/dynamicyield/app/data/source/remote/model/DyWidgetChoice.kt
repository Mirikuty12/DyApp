package com.dynamicyield.app.data.source.remote.model

import com.dynamicyield.templates.ui.DyWidgetName.*
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
data class DyCardPromotionChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyCreditCardPromotionProperties>>
) : DyWidgetChoice()

@Serializable
data class DyCardPromotionSliderChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyCreditCardPromotionSliderProperties>>
) : DyWidgetChoice()

@Serializable
data class DyQuickActionsChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyQuickActionsProperties>>
) : DyWidgetChoice()

@Serializable
data class DyQuickActionsSliderChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyQuickActionsSliderProperties>>
) : DyWidgetChoice()

@Serializable
data class DyCrossUpsellChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyCrossUpsellProperties>>
) : DyWidgetChoice()

@Serializable
data class DyActivationChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyActivationProperties>>
) : DyWidgetChoice()

@Serializable
data class DyOffersChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyOffersProperties>>
) : DyWidgetChoice()

@Serializable
data class DyOffersSliderChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyOffersSliderProperties>>
) : DyWidgetChoice()

@Serializable
data class DyRefinanceChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyRefinanceProperties>>
) : DyWidgetChoice()

@Serializable
data class DyRefinanceSliderChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyRefinanceSliderProperties>>
) : DyWidgetChoice()

@Serializable
data class DyStimulationChoice(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val groups: List<String>,
    override val decisionId: String,
    @SerialName("variations")
    val variations: List<DyWidgetVariation<DyStimulationProperties>>
) : DyWidgetChoice()

object DyWidgetChoiceSerializer : JsonContentPolymorphicSerializer<DyWidgetChoice>(DyWidgetChoice::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out DyWidgetChoice> {
        return when(val widgetName = element.jsonObject["name"]?.jsonPrimitive?.content) {
            CreditCardPromotion.selector -> DyCardPromotionChoice.serializer()
            CreditCardPromotionSlider.selector -> DyCardPromotionSliderChoice.serializer()
            CrossUpsell.selector -> DyCrossUpsellChoice.serializer()
            QuickActions.selector -> DyQuickActionsChoice.serializer()
            QuickActionsSlider.selector -> DyQuickActionsSliderChoice.serializer()
            Activation.selector -> DyActivationChoice.serializer()
            Offers.selector -> DyOffersChoice.serializer()
            OffersSlider.selector -> DyOffersSliderChoice.serializer()
            Refinance.selector -> DyRefinanceChoice.serializer()
            RefinanceSlider.selector -> DyRefinanceSliderChoice.serializer()
            Stimulation.selector -> DyStimulationChoice.serializer()
            else -> throw SerializationException("Unknown DyWidgetChoice: key 'name' does not matches any widget name. Value of key 'name' is '$widgetName'")
        }
    }
}