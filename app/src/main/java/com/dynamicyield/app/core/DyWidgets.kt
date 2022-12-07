package com.dynamicyield.app.core

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dynamicyield.app.data.repository.CommonError
import com.dynamicyield.app.data.repository.DyResultWrapper
import com.dynamicyield.app.data.repository.widget.DyWidgetRepository
import com.dynamicyield.app.data.repository.widget.DyWidgetRepositoryImpl
import com.dynamicyield.app.data.source.remote.DyApiBuilder
import com.dynamicyield.app.data.source.remote.model.DyHpBannerChoice
import com.dynamicyield.app.data.source.remote.model.DyWidgetChoice
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionData
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionSliderView
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionView
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

object DyWidgets {
    private val TAG = this::class.simpleName

    @OptIn(ExperimentalSerializationApi::class)
    private val dyApi = DyApiBuilder.buildDyApi()
    private val dyWidgetRepository: DyWidgetRepository = DyWidgetRepositoryImpl(dyApi, Dispatchers.IO)

    suspend fun chooseDyWidgets(
        vararg widgetNames: com.dynamicyield.templates.ui.DyWidgetName
    ): DyResultWrapper<List<DyWidgetChoice>, CommonError> =
        dyWidgetRepository.chooseDyWidgets(*widgetNames)
            .also { Log.d(TAG, "resultWrapper = $it") }

    inline fun <reified T : com.dynamicyield.templates.ui.DyWidget> createDyWidgetFromChoice(context: Context, choice: DyWidgetChoice): T? {
        return when(choice.name) {
            com.dynamicyield.templates.ui.DyWidgetName.CreditCardPromotion.selector -> {
                CardPromotionView(context).apply {
                    val bannerChoice = (choice as? DyHpBannerChoice) ?: return@apply
                    val variation = bannerChoice.variations.firstOrNull() ?: return@apply
                    val properties = variation.payload.properties

                    // set UI properties
                    setBackgroundGradient(properties.topGradientColor, properties.bottomGradientColor)
                    setImage(
                        properties.image,
                        ImageScaleType.fromString(properties.imageScaleType) ?: ImageScaleType.FIT
                    )
                    setBottomPanelColor(properties.bottomPanelColor)
                    setBottomPanelText(properties.bottomPanelText)
                    setBottomPanelTextColor(properties.bottomPanelTextColor)
                    setBottomPanelTextSize(properties.bottomPanelTextSize.toFloat())
                    setBottomPanelButtonText(properties.bottomPanelButtonText)
                    setBottomPanelButtonTextSize(properties.bottomPanelButtonTextSize.toFloat())
                    setupBottomPanelButtonBackground(
                        backgroundColor = properties.bottomPanelButtonColor,
                        pressedBackgroundColor = properties.bottomPanelButtonHoverColor,
                    )
                } as? T
            }
            com.dynamicyield.templates.ui.DyWidgetName.CreditCardPromotionSlider.selector -> {
                val cardPromotionSliderView = CardPromotionSliderView(context)
                val bannerChoice = (choice as? DyHpBannerChoice) ?: return null

                val cardPromotionDataList = bannerChoice.variations.map {
                    val properties = it.payload.properties
                    CardPromotionData(
                        topGradientColor = properties.topGradientColor,
                        bottomGradientColor = properties.bottomGradientColor,
                        image = properties.image,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleType) ?: ImageScaleType.FIT,
                        bottomPanelColor = properties.bottomPanelColor,
                        bottomPanelText = properties.bottomPanelText,
                        bottomPanelTextColor = properties.bottomPanelTextColor,
                        bottomPanelTextSize = properties.bottomPanelTextSize,
                        bottomPanelButtonText = properties.bottomPanelButtonText,
                        bottomPanelButtonTextSize = properties.bottomPanelButtonTextSize,
                        bottomPanelButtonTextColor = properties.bottomPanelButtonTextColor,
                        bottomPanelButtonColor = properties.bottomPanelButtonColor,
                        bottomPanelButtonHoverColor = properties.bottomPanelButtonHoverColor,
                        bottomPanelButtonListener = {
                            Toast.makeText(it.context, "CLICK", Toast.LENGTH_SHORT).show()
                        },
                    )
                }
                cardPromotionSliderView.setBannerDataList(cardPromotionDataList) as? T
            }
            else -> null
        }
    }
}