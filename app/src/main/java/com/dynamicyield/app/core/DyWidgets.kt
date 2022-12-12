package com.dynamicyield.app.core

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.dynamicyield.app.data.repository.CommonError
import com.dynamicyield.app.data.repository.DyResultWrapper
import com.dynamicyield.app.data.repository.widget.DyWidgetRepository
import com.dynamicyield.app.data.repository.widget.DyWidgetRepositoryImpl
import com.dynamicyield.app.data.source.remote.DyApiBuilder
import com.dynamicyield.app.data.source.remote.model.*
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionData
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionSliderView
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionView
import com.dynamicyield.templates.ui.crossupsell.CrossUpsellDialogFragment
import com.dynamicyield.templates.ui.crossupsell.CrossUpsellStepData
import com.dynamicyield.templates.ui.quickactions.*
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
                    val bannerChoice = (choice as? DyCardPromotionChoice) ?: return@apply
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
                val bannerChoice = (choice as? DyCardPromotionSliderChoice) ?: return null
                val variation = bannerChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val cardPromotionDataList = listOf(
                    CardPromotionData(
                        topGradientColor = properties.topGradientColorCard1,
                        bottomGradientColor = properties.bottomGradientColorCard1,
                        image = properties.imageCard1,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeCard1) ?: ImageScaleType.FIT,
                        bottomPanelColor = properties.bottomPanelColorCard1,
                        bottomPanelText = properties.bottomPanelTextCard1,
                        bottomPanelTextColor = properties.bottomPanelTextColorCard1,
                        bottomPanelTextSize = properties.bottomPanelTextSizeCard1,
                        bottomPanelButtonText = properties.bottomPanelButtonTextCard1,
                        bottomPanelButtonTextSize = properties.bottomPanelButtonTextSizeCard1,
                        bottomPanelButtonTextColor = properties.bottomPanelButtonTextColorCard1,
                        bottomPanelButtonColor = properties.bottomPanelButtonColorCard1,
                        bottomPanelButtonHoverColor = properties.bottomPanelButtonHoverColorCard1,
                        bottomPanelButtonListener = {
                            Toast.makeText(it.context, "CLICK 1", Toast.LENGTH_SHORT).show()
                        },
                    ),
                    CardPromotionData(
                        topGradientColor = properties.topGradientColorCard2,
                        bottomGradientColor = properties.bottomGradientColorCard2,
                        image = properties.imageCard2,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeCard2) ?: ImageScaleType.FIT,
                        bottomPanelColor = properties.bottomPanelColorCard2,
                        bottomPanelText = properties.bottomPanelTextCard2,
                        bottomPanelTextColor = properties.bottomPanelTextColorCard2,
                        bottomPanelTextSize = properties.bottomPanelTextSizeCard2,
                        bottomPanelButtonText = properties.bottomPanelButtonTextCard2,
                        bottomPanelButtonTextSize = properties.bottomPanelButtonTextSizeCard2,
                        bottomPanelButtonTextColor = properties.bottomPanelButtonTextColorCard2,
                        bottomPanelButtonColor = properties.bottomPanelButtonColorCard2,
                        bottomPanelButtonHoverColor = properties.bottomPanelButtonHoverColorCard2,
                        bottomPanelButtonListener = {
                            Toast.makeText(it.context, "CLICK 2", Toast.LENGTH_SHORT).show()
                        },
                    ),
                    CardPromotionData(
                        topGradientColor = properties.topGradientColorCard3,
                        bottomGradientColor = properties.bottomGradientColorCard3,
                        image = properties.imageCard3,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeCard3) ?: ImageScaleType.FIT,
                        bottomPanelColor = properties.bottomPanelColorCard3,
                        bottomPanelText = properties.bottomPanelTextCard3,
                        bottomPanelTextColor = properties.bottomPanelTextColorCard3,
                        bottomPanelTextSize = properties.bottomPanelTextSizeCard3,
                        bottomPanelButtonText = properties.bottomPanelButtonTextCard3,
                        bottomPanelButtonTextSize = properties.bottomPanelButtonTextSizeCard3,
                        bottomPanelButtonTextColor = properties.bottomPanelButtonTextColorCard3,
                        bottomPanelButtonColor = properties.bottomPanelButtonColorCard3,
                        bottomPanelButtonHoverColor = properties.bottomPanelButtonHoverColorCard3,
                        bottomPanelButtonListener = {
                            Toast.makeText(it.context, "CLICK 3", Toast.LENGTH_SHORT).show()
                        },
                    )
                )

                cardPromotionSliderView.setBannerDataList(cardPromotionDataList)
                cardPromotionSliderView as? T
            }
            com.dynamicyield.templates.ui.DyWidgetName.QuickActions.selector -> {
                val quickActionsView = QuickActionsView(context)
                val quickActionsChoice = (choice as? DyQuickActionsChoice) ?: return null
                val variation = quickActionsChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val quickActions = listOfNotNull(
                    createIQuickAction(
                        properties.titleAction1,
                        properties.titleColorAction1,
                        properties.titleSizeAction1,
                        properties.imageAction1,
                        properties.imageScaleTypeAction1,
                        properties.backgroundColorAction1,
                        properties.backgroundHoverColorAction1,
                        properties.borderColorAction1,
                        properties.isFeaturedAction1,
                    ),
                    createIQuickAction(
                        properties.titleAction2,
                        properties.titleColorAction2,
                        properties.titleSizeAction2,
                        properties.imageAction2,
                        properties.imageScaleTypeAction2,
                        properties.backgroundColorAction2,
                        properties.backgroundHoverColorAction2,
                        properties.borderColorAction2,
                        properties.isFeaturedAction2,
                    ),
                    createIQuickAction(
                        properties.titleAction3,
                        properties.titleColorAction3,
                        properties.titleSizeAction3,
                        properties.imageAction3,
                        properties.imageScaleTypeAction3,
                        properties.backgroundColorAction3,
                        properties.backgroundHoverColorAction3,
                        properties.borderColorAction3,
                        properties.isFeaturedAction3,
                    ),
                    createIQuickAction(
                        properties.titleAction4,
                        properties.titleColorAction4,
                        properties.titleSizeAction4,
                        properties.imageAction4,
                        properties.imageScaleTypeAction4,
                        properties.backgroundColorAction4,
                        properties.backgroundHoverColorAction4,
                        properties.borderColorAction4,
                        properties.isFeaturedAction4,
                    ),
                    createIQuickAction(
                        properties.titleAction5,
                        properties.titleColorAction5,
                        properties.titleSizeAction5,
                        properties.imageAction5,
                        properties.imageScaleTypeAction5,
                        properties.backgroundColorAction5,
                        properties.backgroundHoverColorAction5,
                        properties.borderColorAction5,
                        properties.isFeaturedAction5,
                    ),
                    createIQuickAction(
                        properties.titleAction6,
                        properties.titleColorAction6,
                        properties.titleSizeAction6,
                        properties.imageAction6,
                        properties.imageScaleTypeAction6,
                        properties.backgroundColorAction6,
                        properties.backgroundHoverColorAction6,
                        properties.borderColorAction6,
                        properties.isFeaturedAction6,
                    ),
                    createIQuickAction(
                        properties.titleAction7,
                        properties.titleColorAction7,
                        properties.titleSizeAction7,
                        properties.imageAction7,
                        properties.imageScaleTypeAction7,
                        properties.backgroundColorAction7,
                        properties.backgroundHoverColorAction7,
                        properties.borderColorAction7,
                        properties.isFeaturedAction7,
                    ),
                    createIQuickAction(
                        properties.titleAction8,
                        properties.titleColorAction8,
                        properties.titleSizeAction8,
                        properties.imageAction8,
                        properties.imageScaleTypeAction8,
                        properties.backgroundColorAction8,
                        properties.backgroundHoverColorAction8,
                        properties.borderColorAction8,
                        properties.isFeaturedAction8,
                    ),
                )

                quickActionsView.setQuickActions(quickActions)
                quickActionsView as? T
            }
            com.dynamicyield.templates.ui.DyWidgetName.QuickActionsSlider.selector -> {
                val quickActionsSliderView = QuickActionsSliderView(context)
                val quickActionsSliderChoice = (choice as? DyQuickActionsSliderChoice) ?: return null
                val variation = quickActionsSliderChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val quickActions = listOfNotNull(
                    createQuickAction(
                        properties.titleAction1,
                        properties.titleColorAction1,
                        properties.titleSizeAction1,
                        properties.imageAction1,
                        properties.imageScaleTypeAction1,
                        properties.backgroundColorAction1,
                        properties.backgroundHoverColorAction1,
                        properties.borderColorAction1,
                    ),
                    createQuickAction(
                        properties.titleAction2,
                        properties.titleColorAction2,
                        properties.titleSizeAction2,
                        properties.imageAction2,
                        properties.imageScaleTypeAction2,
                        properties.backgroundColorAction2,
                        properties.backgroundHoverColorAction2,
                        properties.borderColorAction2,
                    ),
                    createQuickAction(
                        properties.titleAction3,
                        properties.titleColorAction3,
                        properties.titleSizeAction3,
                        properties.imageAction3,
                        properties.imageScaleTypeAction3,
                        properties.backgroundColorAction3,
                        properties.backgroundHoverColorAction3,
                        properties.borderColorAction3,
                    ),
                    createQuickAction(
                        properties.titleAction4,
                        properties.titleColorAction4,
                        properties.titleSizeAction4,
                        properties.imageAction4,
                        properties.imageScaleTypeAction4,
                        properties.backgroundColorAction4,
                        properties.backgroundHoverColorAction4,
                        properties.borderColorAction4,
                    ),
                    createQuickAction(
                        properties.titleAction5,
                        properties.titleColorAction5,
                        properties.titleSizeAction5,
                        properties.imageAction5,
                        properties.imageScaleTypeAction5,
                        properties.backgroundColorAction5,
                        properties.backgroundHoverColorAction5,
                        properties.borderColorAction5,
                    ),
                    createQuickAction(
                        properties.titleAction6,
                        properties.titleColorAction6,
                        properties.titleSizeAction6,
                        properties.imageAction6,
                        properties.imageScaleTypeAction6,
                        properties.backgroundColorAction6,
                        properties.backgroundHoverColorAction6,
                        properties.borderColorAction6,
                    ),
                    createQuickAction(
                        properties.titleAction7,
                        properties.titleColorAction7,
                        properties.titleSizeAction7,
                        properties.imageAction7,
                        properties.imageScaleTypeAction7,
                        properties.backgroundColorAction7,
                        properties.backgroundHoverColorAction7,
                        properties.borderColorAction7,
                    ),
                    createQuickAction(
                        properties.titleAction8,
                        properties.titleColorAction8,
                        properties.titleSizeAction8,
                        properties.imageAction8,
                        properties.imageScaleTypeAction8,
                        properties.backgroundColorAction8,
                        properties.backgroundHoverColorAction8,
                        properties.borderColorAction8,
                    ),
                )

                quickActionsSliderView.setQuickActions(quickActions)
                quickActionsSliderView as? T
            }
            com.dynamicyield.templates.ui.DyWidgetName.CrossUpsell.selector -> {
                val crossUpsellDialogFragment = CrossUpsellDialogFragment()
                val bannerChoice = (choice as? DyCrossUpsellChoice) ?: return null
                val variation = bannerChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val steps = listOf(
                    CrossUpsellStepData(
                        image = properties.imageStep1,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeStep1) ?: ImageScaleType.FIT,
                        imageSizeType = ImageSizeType.fromString(properties.imageSizeTypeStep1) ?: ImageSizeType.MEDIUM,
                        title = properties.titleStep1,
                        titleSize = properties.titleSizeStep1,
                        titleColor = properties.titleColorStep1,
                        subtitle = properties.subtitleStep1,
                        subtitleSize = properties.subtitleSizeStep1,
                        subtitleColor = properties.subtitleColorStep1,
                        buttonColor = properties.buttonColorStep1,
                        buttonHoverColor = properties.buttonHoverColorStep1,
                        buttonText = properties.buttonTextStep1,
                        buttonTextSize = properties.buttonTextSizeStep1,
                        buttonTextColor = properties.buttonTextColorStep1,
                        progressBarColor = properties.progressBarColorStep1,
                        progressBarBackgroundColor = properties.progressBarBackgroundColorStep1,
                        progressTextColor = properties.progressTextColorStep1,
                    ),
                    CrossUpsellStepData(
                        image = properties.imageStep2,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeStep2) ?: ImageScaleType.FIT,
                        imageSizeType = ImageSizeType.fromString(properties.imageSizeTypeStep2) ?: ImageSizeType.MEDIUM,
                        title = properties.titleStep2,
                        titleSize = properties.titleSizeStep2,
                        titleColor = properties.titleColorStep2,
                        subtitle = properties.subtitleStep2,
                        subtitleSize = properties.subtitleSizeStep2,
                        subtitleColor = properties.subtitleColorStep2,
                        buttonColor = properties.buttonColorStep2,
                        buttonHoverColor = properties.buttonHoverColorStep2,
                        buttonText = properties.buttonTextStep2,
                        buttonTextSize = properties.buttonTextSizeStep2,
                        buttonTextColor = properties.buttonTextColorStep2,
                        progressBarColor = properties.progressBarColorStep2,
                        progressBarBackgroundColor = properties.progressBarBackgroundColorStep2,
                        progressTextColor = properties.progressTextColorStep2,
                    ),
                    CrossUpsellStepData(
                        image = properties.imageStep3,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleTypeStep3) ?: ImageScaleType.FIT,
                        imageSizeType = ImageSizeType.fromString(properties.imageSizeTypeStep3) ?: ImageSizeType.MEDIUM,
                        title = properties.titleStep3,
                        titleSize = properties.titleSizeStep3,
                        titleColor = properties.titleColorStep3,
                        subtitle = properties.subtitleStep3,
                        subtitleSize = properties.subtitleSizeStep3,
                        subtitleColor = properties.subtitleColorStep3,
                        buttonColor = properties.buttonColorStep3,
                        buttonHoverColor = properties.buttonHoverColorStep3,
                        buttonText = properties.buttonTextStep3,
                        buttonTextSize = properties.buttonTextSizeStep3,
                        buttonTextColor = properties.buttonTextColorStep3,
                        progressBarColor = properties.progressBarColorStep3,
                        progressBarBackgroundColor = properties.progressBarBackgroundColorStep3,
                        progressTextColor = properties.progressTextColorStep3,
                    )
                )

                crossUpsellDialogFragment.setCornerRadius(properties.cornerRadius.toFloat())
                crossUpsellDialogFragment.setBackgroundColor(properties.backgroundColor)
                crossUpsellDialogFragment.setCloseButtonColor(properties.closeButtonColor)
                crossUpsellDialogFragment.setSteps(steps)

                crossUpsellDialogFragment as? T
            }
            else -> null
        }
    }

    fun createIQuickAction(
        title: String,
        titleColor: String,
        titleSize: Int,
        image: String,
        imageScaleType: String,
        backgroundColor: String,
        backgroundHoverColor: String,
        borderColor: String,
        isFeaturedStr: String,
    ): IQuickActionData?  {
        if (title.isBlank() && image.isBlank()) return null

        return when (isFeaturedStr.toBooleanStrict()) {
            true -> FeaturedQuickActionData(
                title, titleColor, titleSize,
                image, ImageScaleType.fromString(imageScaleType) ?: ImageScaleType.FIT,
                backgroundColor, borderColor, 1, 16,
                backgroundHoverColor, borderColor, 1, 16,
                null
            )
            false -> QuickActionData(
                title, titleColor, titleSize,
                image, ImageScaleType.fromString(imageScaleType) ?: ImageScaleType.FIT,
                backgroundColor, borderColor, 1, 16,
                backgroundHoverColor, borderColor, 1, 16,
                null
            )
        }
    }

    fun createQuickAction(
        title: String,
        titleColor: String,
        titleSize: Int,
        image: String,
        imageScaleType: String,
        backgroundColor: String,
        backgroundHoverColor: String,
        borderColor: String,
    ): QuickActionData?  {
        if (title.isBlank() && image.isBlank()) return null

        return QuickActionData(
            title, titleColor, titleSize,
            image, ImageScaleType.fromString(imageScaleType) ?: ImageScaleType.FIT,
            backgroundColor, borderColor, 1, 16,
            backgroundHoverColor, borderColor, 1, 16,
            null
        )
    }
}