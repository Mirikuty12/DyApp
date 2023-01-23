package com.dynamicyield.app.core

import android.content.Context
import android.util.Log
import com.dynamicyield.app.data.repository.CommonError
import com.dynamicyield.app.data.repository.DyResultWrapper
import com.dynamicyield.app.data.repository.widget.DyWidgetRepository
import com.dynamicyield.app.data.repository.widget.DyWidgetRepositoryImpl
import com.dynamicyield.app.data.source.remote.DyApiBuilder
import com.dynamicyield.app.data.source.remote.model.*
import com.dynamicyield.templates.ui.DyWidgetName
import com.dynamicyield.templates.ui.activation.ActivationDialogFragment
import com.dynamicyield.templates.ui.base.data.ImageScaleType
import com.dynamicyield.templates.ui.base.data.ImageSizeType
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionData
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionSliderView
import com.dynamicyield.templates.ui.cardpromotion.CardPromotionView
import com.dynamicyield.templates.ui.crossupsell.CrossUpsellDialogFragment
import com.dynamicyield.templates.ui.crossupsell.CrossUpsellStepData
import com.dynamicyield.templates.ui.offers.OfferData
import com.dynamicyield.templates.ui.offers.OfferView
import com.dynamicyield.templates.ui.offers.OffersDialogFragment
import com.dynamicyield.templates.ui.offers.OffersSliderView
import com.dynamicyield.templates.ui.quickactions.*
import com.dynamicyield.templates.ui.refinance.RefinanceData
import com.dynamicyield.templates.ui.refinance.RefinanceSliderView
import com.dynamicyield.templates.ui.refinance.RefinanceView
import com.dynamicyield.templates.ui.stimulation.StimulationView
import com.dynamicyield.templates.ui.stories.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

object DyWidgets {
    private val TAG = this::class.simpleName

    @OptIn(ExperimentalSerializationApi::class)
    private val dyApi = DyApiBuilder.buildDyApi()
    private val dyWidgetRepository: DyWidgetRepository =
        DyWidgetRepositoryImpl(dyApi, Dispatchers.IO)

    suspend fun chooseDyWidgets(
        vararg widgetNames: DyWidgetName
    ): DyResultWrapper<List<DyWidgetChoice>, CommonError> =
        dyWidgetRepository.chooseDyWidgets(*widgetNames)
            .also { Log.d(TAG, "resultWrapper = $it") }

    inline fun <reified T : com.dynamicyield.templates.ui.DyWidget> createDyWidgetFromChoice(
        context: Context,
        choice: DyWidgetChoice
    ): T? {
        return when (choice.name) {
            DyWidgetName.CreditCardPromotion.selector -> {
                CardPromotionView(context).apply {
                    val bannerChoice = (choice as? DyCardPromotionChoice) ?: return@apply
                    val variation = bannerChoice.variations.firstOrNull() ?: return@apply
                    val properties = variation.payload.properties

                    // set UI properties
                    setBackgroundGradient(
                        properties.topGradientColor ?: return null,
                        properties.bottomGradientColor ?: return null
                    )
                    setImage(
                        properties.image,
                        ImageScaleType.fromString(properties.imageScaleType) ?: ImageScaleType.FIT
                    )
                    setBottomPanelColor(properties.bottomPanelColor ?: return null)
                    setBottomPanelText(properties.bottomPanelText)
                    setBottomPanelTextColor(properties.bottomPanelTextColor ?: return null)
                    setBottomPanelTextSize(properties.bottomPanelTextSize?.toFloat() ?: return null)
                    setBottomPanelButtonText(properties.bottomPanelButtonText)
                    setBottomPanelButtonTextSize(properties.bottomPanelButtonTextSize?.toFloat() ?: return null)
                    setBottomPanelButtonTextColor(properties.bottomPanelButtonTextColor ?: return null)
                    setupBottomPanelButtonBackground(
                        backgroundColor = properties.bottomPanelButtonColor,
                        pressedBackgroundColor = properties.bottomPanelButtonHoverColor,
                    )
                } as? T
            }
            DyWidgetName.CreditCardPromotionSlider.selector -> {
                val cardPromotionSliderView = CardPromotionSliderView(context)
                val bannerChoice = (choice as? DyCardPromotionSliderChoice) ?: return null
                val variation = bannerChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val cardPromotionDataList = listOfNotNull(
                    createCardPromotionData(properties.card1),
                    createCardPromotionData(properties.card2),
                    createCardPromotionData(properties.card3),
                    createCardPromotionData(properties.card4),
                    createCardPromotionData(properties.card5),
                    createCardPromotionData(properties.card6),
                    createCardPromotionData(properties.card7),
                    createCardPromotionData(properties.card8),
                    createCardPromotionData(properties.card9),
                    createCardPromotionData(properties.card10),
                )

                cardPromotionSliderView.setBannerDataList(cardPromotionDataList)
                cardPromotionSliderView as? T
            }
            DyWidgetName.QuickActions.selector -> {
                val quickActionsView = QuickActionsView(context)
                val quickActionsChoice = (choice as? DyQuickActionsChoice) ?: return null
                val variation = quickActionsChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val quickActions = listOfNotNull(
                    createIQuickAction(properties.action1),
                    createIQuickAction(properties.action2),
                    createIQuickAction(properties.action3),
                    createIQuickAction(properties.action4),
                    createIQuickAction(properties.action5),
                    createIQuickAction(properties.action6),
                    createIQuickAction(properties.action7),
                    createIQuickAction(properties.action8),
                    createIQuickAction(properties.action9),
                    createIQuickAction(properties.action10),
                )

                quickActionsView.setQuickActions(quickActions)
                quickActionsView as? T
            }
            DyWidgetName.QuickActionsSlider.selector -> {
                val quickActionsSliderView = QuickActionsSliderView(context)
                val quickActionsSliderChoice = (choice as? DyQuickActionsChoice) ?: return null
                val variation = quickActionsSliderChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val quickActions = listOfNotNull(
                    createQuickAction(properties.action1),
                    createQuickAction(properties.action2),
                    createQuickAction(properties.action3),
                    createQuickAction(properties.action4),
                    createQuickAction(properties.action5),
                    createQuickAction(properties.action6),
                    createQuickAction(properties.action7),
                    createQuickAction(properties.action8),
                    createQuickAction(properties.action9),
                    createQuickAction(properties.action10),
                )

                quickActionsSliderView.setQuickActions(quickActions)
                quickActionsSliderView as? T
            }
            DyWidgetName.CrossUpsell.selector -> {
                val crossUpsellDialogFragment = CrossUpsellDialogFragment()
                val bannerChoice = (choice as? DyCrossUpsellChoice) ?: return null
                val variation = bannerChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                val steps = listOfNotNull(
                    createCrossUpsellStepData(properties.step1),
                    createCrossUpsellStepData(properties.step2),
                    createCrossUpsellStepData(properties.step3),
                    createCrossUpsellStepData(properties.step4),
                    createCrossUpsellStepData(properties.step5),
                    createCrossUpsellStepData(properties.step6),
                    createCrossUpsellStepData(properties.step7),
                    createCrossUpsellStepData(properties.step8),
                    createCrossUpsellStepData(properties.step9),
                    createCrossUpsellStepData(properties.step10),
                )

                crossUpsellDialogFragment.setCornerRadius(properties.cornerRadius?.toFloat() ?: 0f)
                crossUpsellDialogFragment.setBackgroundColor(properties.backgroundColor ?: "#00FFFFFF")
                crossUpsellDialogFragment.setCloseButtonColor(properties.closeButtonColor ?: "#FF0000")
                crossUpsellDialogFragment.setSteps(steps)

                crossUpsellDialogFragment as? T
            }
            DyWidgetName.Activation.selector -> {
                val activationDialogFragment = ActivationDialogFragment()
                val activationChoice = (choice as? DyActivationChoice) ?: return null
                val variation = activationChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties

                activationDialogFragment.apply {
                    setTopHandleColor(color = properties.topHandleColor ?: "#00FFFFFF")
                    setBackgroundProps(
                        backgroundColor = properties.backgroundColor ?: "#FFFFFF",
                        topCornerRadius = properties.topCornerRadius?.toFloat() ?: 0f
                    )
                    setImageProps(
                        imageUrl = properties.image,
                        imageScaleType = ImageScaleType.fromString(properties.imageScaleType)
                            ?: ImageScaleType.FIT,
                        imageSizeType = ImageSizeType.fromString(properties.imageSizeType)
                            ?: ImageSizeType.MEDIUM
                    )
                    setTitleProps(
                        titleText = properties.title,
                        titleSizeSp = properties.titleSize ?: return null,
                        titleColorString = properties.titleColor ?: return null
                    )
                    setSubtitleProps(
                        subtitleText = properties.subtitle,
                        subtitleSizeSp = properties.subtitleSize ?: return null,
                        subtitleColorString = properties.subtitleColor ?: return null
                    )
                    setButtonProps(
                        buttonText = properties.buttonText,
                        buttonTextSizeSp = properties.buttonTextSize ?: return null,
                        buttonTextColorString = properties.buttonTextColor ?: return null,
                        pressedButtonTextColorString = properties.pressedButtonTextColor ?: return null,
                        buttonBackgroundColorString = properties.buttonBackgroundColor ?: return null,
                        pressedButtonBackgroundColorString = properties.pressedButtonBackgroundColor ?: return null,
                        buttonStrokeColorString = properties.buttonBorderColor ?: "#00FFFFFF",
                        buttonStrokeWidth = properties.buttonBorderWidth?.toFloat() ?: 0f,
                    )
                }
                activationDialogFragment as? T
            }
            DyWidgetName.Offers.selector -> {
                val offersChoice = (choice as? DyOffersChoice) ?: return null
                val variation = offersChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val offersDialogFragment = OffersDialogFragment().apply {
                    setBackgroundProps(
                        backgroundColor = properties.backgroundColor,
                        topCornerRadius = properties.topCornerRadius.toFloat()
                    )
                    setTopHandleColor(properties.topHandleColor)
                    setTitleProps(
                        titleText = properties.title,
                        titleSizeSp = properties.titleSize,
                        titleColorString = properties.titleColor
                    )
                    setSubtitleProps(
                        subtitleText = properties.subtitle,
                        subtitleSizeSp = properties.subtitleSize,
                        subtitleColorString = properties.subtitleColor
                    )
                    setOfferViewMode(
                        OfferView.OfferViewMode.fromString(properties.offerViewMode)
                            ?: OfferView.OfferViewMode.RECTANGLE
                    )
                    setOffers(
                        listOfNotNull(
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer1,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer1,
                                logoImage = properties.logoImageOffer1,
                                logoImageScaleType = properties.logoImageScaleTypeOffer1,
                                labelText = properties.labelOffer1,
                                labelTextColor = properties.labelTextColorOffer1,
                                labelTextSize = properties.labelTextSizeOffer1,
                                labelBackgroundColor = properties.labelBackgroundColorOffer1,
                                titleText = properties.titleOffer1,
                                titleTextColor = properties.titleTextColorOffer1,
                                titleTextSize = properties.titleTextSizeOffer1,
                            ),
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer2,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer2,
                                logoImage = properties.logoImageOffer2,
                                logoImageScaleType = properties.logoImageScaleTypeOffer2,
                                labelText = properties.labelOffer2,
                                labelTextColor = properties.labelTextColorOffer2,
                                labelTextSize = properties.labelTextSizeOffer2,
                                labelBackgroundColor = properties.labelBackgroundColorOffer2,
                                titleText = properties.titleOffer2,
                                titleTextColor = properties.titleTextColorOffer2,
                                titleTextSize = properties.titleTextSizeOffer2,
                            ),
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer3,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer3,
                                logoImage = properties.logoImageOffer3,
                                logoImageScaleType = properties.logoImageScaleTypeOffer3,
                                labelText = properties.labelOffer3,
                                labelTextColor = properties.labelTextColorOffer3,
                                labelTextSize = properties.labelTextSizeOffer3,
                                labelBackgroundColor = properties.labelBackgroundColorOffer3,
                                titleText = properties.titleOffer3,
                                titleTextColor = properties.titleTextColorOffer3,
                                titleTextSize = properties.titleTextSizeOffer3,
                            ),
                        )
                    )
                }
                offersDialogFragment as? T
            }
            DyWidgetName.OffersSlider.selector -> {
                val offersSliderChoice = (choice as? DyOffersSliderChoice) ?: return null
                val variation = offersSliderChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val offersSliderView = OffersSliderView(context).apply {
                    setOfferDataList(
                        listOfNotNull(
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer1,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer1,
                                logoImage = properties.logoImageOffer1,
                                logoImageScaleType = properties.logoImageScaleTypeOffer1,
                                labelText = properties.labelOffer1,
                                labelTextColor = properties.labelTextColorOffer1,
                                labelTextSize = properties.labelTextSizeOffer1,
                                labelBackgroundColor = properties.labelBackgroundColorOffer1,
                                titleText = properties.titleOffer1,
                                titleTextColor = properties.titleTextColorOffer1,
                                titleTextSize = properties.titleTextSizeOffer1,
                            ),
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer2,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer2,
                                logoImage = properties.logoImageOffer2,
                                logoImageScaleType = properties.logoImageScaleTypeOffer2,
                                labelText = properties.labelOffer2,
                                labelTextColor = properties.labelTextColorOffer2,
                                labelTextSize = properties.labelTextSizeOffer2,
                                labelBackgroundColor = properties.labelBackgroundColorOffer2,
                                titleText = properties.titleOffer2,
                                titleTextColor = properties.titleTextColorOffer2,
                                titleTextSize = properties.titleTextSizeOffer2,
                            ),
                            createOfferData(
                                backgroundImage = properties.backgroundImageOffer3,
                                backgroundImageScaleType = properties.backgroundImageScaleTypeOffer3,
                                logoImage = properties.logoImageOffer3,
                                logoImageScaleType = properties.logoImageScaleTypeOffer3,
                                labelText = properties.labelOffer3,
                                labelTextColor = properties.labelTextColorOffer3,
                                labelTextSize = properties.labelTextSizeOffer3,
                                labelBackgroundColor = properties.labelBackgroundColorOffer3,
                                titleText = properties.titleOffer3,
                                titleTextColor = properties.titleTextColorOffer3,
                                titleTextSize = properties.titleTextSizeOffer3,
                            ),
                        )
                    )
                }
                offersSliderView as? T
            }
            DyWidgetName.Refinance.selector -> {
                val refinanceChoice = (choice as? DyRefinanceChoice) ?: return null
                val variation = refinanceChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val refinanceView = RefinanceView(context).apply {
                    setCornerRadius(properties.cornerRadius.toFloat())
                    setBackgroundColorStr(properties.backgroundColor)
                    setImage(
                        url = properties.image,
                        scaleType = ImageScaleType.fromString(properties.imageScaleType)
                            ?: ImageScaleType.FILL,
                    )
                    setTitle(
                        text = properties.title,
                        textColor = properties.titleTextColor,
                        textSize = properties.titleTextSize.toFloat(),
                    )
                    setSubtitle(
                        text = properties.subtitle,
                        textColor = properties.subtitleTextColor,
                        textSize = properties.subtitleTextSize.toFloat(),
                    )
                    setCtaButton1(
                        buttonText = properties.ctaButton1Text,
                        buttonTextSizeSp = properties.ctaButton1TextSize.toFloat(),
                        buttonTextColorString = properties.ctaButton1TextColor,
                        pressedButtonTextColorString = properties.ctaButton1PressedTextColor,
                        buttonBackgroundColorString = properties.ctaButton1BackgroundColor,
                        pressedButtonBackgroundColorString = properties.ctaButton1PressedBackgroundColor,
                        buttonStrokeColorString = properties.ctaButton1StrokeColor,
                        buttonStrokeWidth = properties.ctaButton1StrokeWidth,
                    )
                    setCtaButton2(
                        buttonText = properties.ctaButton2Text,
                        buttonTextSizeSp = properties.ctaButton2TextSize.toFloat(),
                        buttonTextColorString = properties.ctaButton2TextColor,
                        pressedButtonTextColorString = properties.ctaButton2PressedTextColor,
                        buttonBackgroundColorString = properties.ctaButton2BackgroundColor,
                        pressedButtonBackgroundColorString = properties.ctaButton2PressedBackgroundColor,
                        buttonStrokeColorString = properties.ctaButton2StrokeColor,
                        buttonStrokeWidth = properties.ctaButton2StrokeWidth,
                    )
                }
                refinanceView as? T
            }
            DyWidgetName.RefinanceSlider.selector -> {
                val refinanceSliderChoice = (choice as? DyRefinanceSliderChoice) ?: return null
                val variation = refinanceSliderChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val refinanceSliderView = RefinanceSliderView(context).apply {
                    setRefinanceDataList(
                        listOfNotNull(
                            createRefinanceData(
                                backgroundColor = properties.backgroundColorItem1,
                                cornerRadius = properties.cornerRadiusItem1,
                                image = properties.imageItem1,
                                imageScaleType = properties.imageScaleTypeItem1,
                                title = properties.titleItem1,
                                titleTextColor = properties.titleTextColorItem1,
                                titleTextSize = properties.titleTextSizeItem1,
                                subtitle = properties.subtitleItem1,
                                subtitleTextColor = properties.subtitleTextColorItem1,
                                subtitleTextSize = properties.subtitleTextSizeItem1,
                                ctaButton1Text = properties.ctaButton1TextItem1,
                                ctaButton1TextSize = properties.ctaButton1TextSizeItem1,
                                ctaButton1TextColor = properties.ctaButton1TextColorItem1,
                                ctaButton1PressedTextColor = properties.ctaButton1PressedTextColorItem1,
                                ctaButton1BackgroundColor = properties.ctaButton1BackgroundColorItem1,
                                ctaButton1PressedBackgroundColor = properties.ctaButton1PressedBackgroundColorItem1,
                                ctaButton1StrokeColor = properties.ctaButton1StrokeColorItem1,
                                ctaButton1StrokeWidth = properties.ctaButton1StrokeWidthItem1,
                                ctaButton2Text = properties.ctaButton2TextItem1,
                                ctaButton2TextSize = properties.ctaButton2TextSizeItem1,
                                ctaButton2TextColor = properties.ctaButton2TextColorItem1,
                                ctaButton2PressedTextColor = properties.ctaButton2PressedTextColorItem1,
                                ctaButton2BackgroundColor = properties.ctaButton2BackgroundColorItem1,
                                ctaButton2PressedBackgroundColor = properties.ctaButton2PressedBackgroundColorItem1,
                                ctaButton2StrokeColor = properties.ctaButton2StrokeColorItem1,
                                ctaButton2StrokeWidth = properties.ctaButton2StrokeWidthItem1,
                            ),
                            createRefinanceData(
                                backgroundColor = properties.backgroundColorItem2,
                                cornerRadius = properties.cornerRadiusItem2,
                                image = properties.imageItem2,
                                imageScaleType = properties.imageScaleTypeItem2,
                                title = properties.titleItem2,
                                titleTextColor = properties.titleTextColorItem2,
                                titleTextSize = properties.titleTextSizeItem2,
                                subtitle = properties.subtitleItem2,
                                subtitleTextColor = properties.subtitleTextColorItem2,
                                subtitleTextSize = properties.subtitleTextSizeItem2,
                                ctaButton1Text = properties.ctaButton1TextItem2,
                                ctaButton1TextSize = properties.ctaButton1TextSizeItem2,
                                ctaButton1TextColor = properties.ctaButton1TextColorItem2,
                                ctaButton1PressedTextColor = properties.ctaButton1PressedTextColorItem2,
                                ctaButton1BackgroundColor = properties.ctaButton1BackgroundColorItem2,
                                ctaButton1PressedBackgroundColor = properties.ctaButton1PressedBackgroundColorItem2,
                                ctaButton1StrokeColor = properties.ctaButton1StrokeColorItem2,
                                ctaButton1StrokeWidth = properties.ctaButton1StrokeWidthItem2,
                                ctaButton2Text = properties.ctaButton2TextItem2,
                                ctaButton2TextSize = properties.ctaButton2TextSizeItem2,
                                ctaButton2TextColor = properties.ctaButton2TextColorItem2,
                                ctaButton2PressedTextColor = properties.ctaButton2PressedTextColorItem2,
                                ctaButton2BackgroundColor = properties.ctaButton2BackgroundColorItem2,
                                ctaButton2PressedBackgroundColor = properties.ctaButton2PressedBackgroundColorItem2,
                                ctaButton2StrokeColor = properties.ctaButton2StrokeColorItem2,
                                ctaButton2StrokeWidth = properties.ctaButton2StrokeWidthItem2,
                            ),
                            createRefinanceData(
                                backgroundColor = properties.backgroundColorItem3,
                                cornerRadius = properties.cornerRadiusItem3,
                                image = properties.imageItem3,
                                imageScaleType = properties.imageScaleTypeItem3,
                                title = properties.titleItem3,
                                titleTextColor = properties.titleTextColorItem3,
                                titleTextSize = properties.titleTextSizeItem3,
                                subtitle = properties.subtitleItem3,
                                subtitleTextColor = properties.subtitleTextColorItem3,
                                subtitleTextSize = properties.subtitleTextSizeItem3,
                                ctaButton1Text = properties.ctaButton1TextItem3,
                                ctaButton1TextSize = properties.ctaButton1TextSizeItem3,
                                ctaButton1TextColor = properties.ctaButton1TextColorItem3,
                                ctaButton1PressedTextColor = properties.ctaButton1PressedTextColorItem3,
                                ctaButton1BackgroundColor = properties.ctaButton1BackgroundColorItem3,
                                ctaButton1PressedBackgroundColor = properties.ctaButton1PressedBackgroundColorItem3,
                                ctaButton1StrokeColor = properties.ctaButton1StrokeColorItem3,
                                ctaButton1StrokeWidth = properties.ctaButton1StrokeWidthItem3,
                                ctaButton2Text = properties.ctaButton2TextItem3,
                                ctaButton2TextSize = properties.ctaButton2TextSizeItem3,
                                ctaButton2TextColor = properties.ctaButton2TextColorItem3,
                                ctaButton2PressedTextColor = properties.ctaButton2PressedTextColorItem3,
                                ctaButton2BackgroundColor = properties.ctaButton2BackgroundColorItem3,
                                ctaButton2PressedBackgroundColor = properties.ctaButton2PressedBackgroundColorItem3,
                                ctaButton2StrokeColor = properties.ctaButton2StrokeColorItem3,
                                ctaButton2StrokeWidth = properties.ctaButton2StrokeWidthItem3,
                            )
                        )
                    )
                }
                refinanceSliderView as? T
            }
            DyWidgetName.Stimulation.selector -> {
                val stimulationChoice = (choice as? DyStimulationChoice) ?: return null
                val variation = stimulationChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val stimulationView = StimulationView(context).apply {
                    setBackgroundColor(properties.backgroundColor)
                    setTitle(
                        text = properties.title,
                        textColor = properties.titleTextColor,
                        textSize = properties.titleTextSize.toFloat()
                    )
                    setExpirationTimer(
                        expirationTimestampSec = properties.expirationTimestampSec,
                        textColor = properties.expirationTextColor,
                        textSize = properties.expirationTextSize.toFloat()
                    )
                    setButton(
                        buttonText = properties.buttonText,
                        buttonTextSizeSp = properties.buttonTextSize.toFloat(),
                        buttonTextColorString = properties.buttonTextColor,
                        buttonBackgroundColorString = properties.buttonBackgroundColor,
                        pressedButtonBackgroundColorString = properties.buttonPressedBackgroundColor
                    )
                    setCloseButtonColor(properties.closeButtonColor)
                }
                stimulationView as? T
            }
            DyWidgetName.Stories.selector -> {
                val storiesChoice = (choice as? DyStoriesChoice) ?: return null
                val variation = storiesChoice.variations.firstOrNull() ?: return null
                val properties = variation.payload.properties
                val storyView = StoriesView(context).apply {
                    setStoryDataList(
                        listOfNotNull(
                            createStoryData(properties.story1),
                            createStoryData(properties.story2),
                            createStoryData(properties.story3),
                            createStoryData(properties.story4),
                            createStoryData(properties.story5),
                            createStoryData(properties.story6),
                            createStoryData(properties.story7),
                            createStoryData(properties.story8),
                            createStoryData(properties.story9),
                            createStoryData(properties.story10),
                        )
                    )
                }
                storyView as? T
            }
            else -> null
        }
    }

    fun createCardPromotionData(
        cardProperties: DyCreditCardPromotionProperties?
    ): CardPromotionData? {
        cardProperties ?: return null
        if (cardProperties.image.isNullOrBlank() &&
            cardProperties.bottomPanelText.isNullOrBlank()) return null

        return CardPromotionData(
            topGradientColor = cardProperties.topGradientColor ?: return null,
            bottomGradientColor = cardProperties.bottomGradientColor ?: return null,
            image = cardProperties.image,
            imageScaleType = ImageScaleType.fromString(cardProperties.imageScaleType) ?: ImageScaleType.FIT,
            bottomPanelColor = cardProperties.bottomPanelColor ?: return null,
            bottomPanelText = cardProperties.bottomPanelText,
            bottomPanelTextColor = cardProperties.bottomPanelTextColor ?: return null,
            bottomPanelTextSize = cardProperties.bottomPanelTextSize ?: return null,
            bottomPanelButtonText = cardProperties.bottomPanelButtonText,
            bottomPanelButtonTextSize = cardProperties.bottomPanelButtonTextSize ?: return null,
            bottomPanelButtonTextColor = cardProperties.bottomPanelButtonTextColor ?: return null,
            bottomPanelButtonColor = cardProperties.bottomPanelButtonColor ?: return null,
            bottomPanelButtonHoverColor = cardProperties.bottomPanelButtonHoverColor ?: return null,
        )
    }

    fun createIQuickAction(quickActionProperties: QuickActionProperties?): IQuickActionData? {
        quickActionProperties ?: return null
        if (quickActionProperties.title.isNullOrBlank() &&
            quickActionProperties.image.isNullOrBlank()) return null

        val title = quickActionProperties.title
        val titleColor = quickActionProperties.titleColor ?: return null
        val titleSize = quickActionProperties.titleSize ?: return null
        val image = quickActionProperties.image
        val imageScaleType = ImageScaleType.fromString(quickActionProperties.imageScaleType) ?: ImageScaleType.FIT
        val backgroundColor = quickActionProperties.backgroundColor
        val borderColor = quickActionProperties.borderColor
        val borderWidth = 1
        val cornerRadius = 16
        val pressedBackgroundColor = quickActionProperties.backgroundHoverColor
        val pressedBorderColor = quickActionProperties.borderColor
        val pressedBorderWidth = 1
        val pressedCornerRadius = 16

        return when (quickActionProperties.isFeatured?.toBooleanStrict() == true) {
            true -> FeaturedQuickActionData(
                title = title,
                titleColor = titleColor,
                titleSize = titleSize,
                image = image,
                imageScaleType = imageScaleType,
                backgroundColor = backgroundColor,
                borderColor = borderColor,
                borderWidth = borderWidth,
                cornerRadius = cornerRadius,
                pressedBackgroundColor = pressedBackgroundColor,
                pressedBorderColor = pressedBorderColor,
                pressedBorderWidth = pressedBorderWidth,
                pressedCornerRadius = pressedCornerRadius,
            )
            false -> QuickActionData(
                title = title,
                titleColor = titleColor,
                titleSize = titleSize,
                image = image,
                imageScaleType = imageScaleType,
                backgroundColor = backgroundColor,
                borderColor = borderColor,
                borderWidth = borderWidth,
                cornerRadius = cornerRadius,
                pressedBackgroundColor = pressedBackgroundColor,
                pressedBorderColor = pressedBorderColor,
                pressedBorderWidth = pressedBorderWidth,
                pressedCornerRadius = pressedCornerRadius,
            )
        }
    }

    fun createQuickAction(quickActionProperties: QuickActionProperties?): QuickActionData? {
        quickActionProperties ?: return null
        if (quickActionProperties.title.isNullOrBlank() &&
            quickActionProperties.image.isNullOrBlank()) return null

        return QuickActionData(
            title = quickActionProperties.title,
            titleColor = quickActionProperties.titleColor ?: return null,
            titleSize = quickActionProperties.titleSize ?: return null,
            image = quickActionProperties.image,
            imageScaleType = ImageScaleType.fromString(quickActionProperties.imageScaleType) ?: ImageScaleType.FIT,
            backgroundColor = quickActionProperties.backgroundColor,
            borderColor = quickActionProperties.borderColor,
            borderWidth = 1,
            cornerRadius = 16,
            pressedBackgroundColor = quickActionProperties.backgroundHoverColor,
            pressedBorderColor = quickActionProperties.borderColor,
            pressedBorderWidth = 1,
            pressedCornerRadius = 16,
        )
    }

    fun createCrossUpsellStepData(crossUpsellStep: CrossUpsellStep?): CrossUpsellStepData? {
        crossUpsellStep ?: return null
        if (crossUpsellStep.title.isNullOrBlank() &&
            crossUpsellStep.image.isNullOrBlank()) return null

        return CrossUpsellStepData(
            image = crossUpsellStep.image,
            imageScaleType = ImageScaleType.fromString(crossUpsellStep.imageScaleType) ?: ImageScaleType.FIT,
            imageSizeType = ImageSizeType.fromString(crossUpsellStep.imageSizeType) ?: ImageSizeType.MEDIUM,
            title = crossUpsellStep.title,
            titleSize = crossUpsellStep.titleSize ?: return null,
            titleColor = crossUpsellStep.titleColor ?: return null,
            subtitle = crossUpsellStep.subtitle,
            subtitleSize = crossUpsellStep.subtitleSize ?: return null,
            subtitleColor = crossUpsellStep.subtitleColor ?: return null,
            buttonColor = crossUpsellStep.buttonColor ?: return null,
            buttonHoverColor = crossUpsellStep.buttonHoverColor ?: return null,
            buttonText = crossUpsellStep.buttonText,
            buttonTextSize = crossUpsellStep.buttonTextSize ?: return null,
            buttonTextColor = crossUpsellStep.buttonTextColor ?: return null,
            progressBarColor = crossUpsellStep.progressBarColor ?: return null,
            progressBarBackgroundColor = crossUpsellStep.progressBarBackgroundColor ?: return null,
            progressTextColor = crossUpsellStep.progressTextColor ?: return null,
            previousTextColor = crossUpsellStep.previousTextColor ?: return null,
        )
    }

    fun createOfferData(
        backgroundImage: String?,
        backgroundImageScaleType: String,
        logoImage: String?,
        logoImageScaleType: String,
        labelText: String?,
        labelTextColor: String,
        labelTextSize: Int,
        labelBackgroundColor: String,
        titleText: String?,
        titleTextColor: String,
        titleTextSize: Int,
    ): OfferData? {
        if (titleText.isNullOrBlank() && backgroundImage.isNullOrBlank()) return null

        return OfferData(
            backgroundImage = backgroundImage,
            backgroundImageScaleType = ImageScaleType.fromString(backgroundImageScaleType)
                ?: ImageScaleType.FIT,
            logoImage = logoImage,
            logoImageScaleType = ImageScaleType.fromString(logoImageScaleType)
                ?: ImageScaleType.FIT,
            labelText = labelText,
            labelTextColor = labelTextColor,
            labelTextSize = labelTextSize,
            labelBackgroundColor = labelBackgroundColor,
            titleText = titleText,
            titleTextColor = titleTextColor,
            titleTextSize = titleTextSize,
        )
    }

    fun createRefinanceData(
        backgroundColor: String,
        cornerRadius: Int,
        image: String?,
        imageScaleType: String,
        title: String?,
        titleTextColor: String,
        titleTextSize: Int,
        subtitle: String?,
        subtitleTextColor: String,
        subtitleTextSize: Int,
        ctaButton1Text: String?,
        ctaButton1TextSize: Int,
        ctaButton1TextColor: String,
        ctaButton1PressedTextColor: String,
        ctaButton1BackgroundColor: String,
        ctaButton1PressedBackgroundColor: String,
        ctaButton1StrokeColor: String,
        ctaButton1StrokeWidth: Float,
        ctaButton2Text: String?,
        ctaButton2TextSize: Int,
        ctaButton2TextColor: String,
        ctaButton2PressedTextColor: String,
        ctaButton2BackgroundColor: String,
        ctaButton2PressedBackgroundColor: String,
        ctaButton2StrokeColor: String,
        ctaButton2StrokeWidth: Float,
    ): RefinanceData? {
        if (title.isNullOrBlank() && image.isNullOrBlank()) return null

        return RefinanceData(
            backgroundColor = backgroundColor,
            cornerRadius = cornerRadius,

            image = image,
            imageScaleType = ImageScaleType.fromString(imageScaleType) ?: ImageScaleType.FILL,

            title = title,
            titleTextColor = titleTextColor,
            titleTextSize = titleTextSize,

            subtitle = subtitle,
            subtitleTextColor = subtitleTextColor,
            subtitleTextSize = subtitleTextSize,

            ctaButton1Text = ctaButton1Text,
            ctaButton1TextSize = ctaButton1TextSize,
            ctaButton1TextColor = ctaButton1TextColor,
            ctaButton1PressedTextColor = ctaButton1PressedTextColor,
            ctaButton1BackgroundColor = ctaButton1BackgroundColor,
            ctaButton1PressedBackgroundColor = ctaButton1PressedBackgroundColor,
            ctaButton1StrokeColor = ctaButton1StrokeColor,
            ctaButton1StrokeWidth = ctaButton1StrokeWidth,

            ctaButton2Text = ctaButton2Text,
            ctaButton2TextSize = ctaButton2TextSize,
            ctaButton2TextColor = ctaButton2TextColor,
            ctaButton2PressedTextColor = ctaButton2PressedTextColor,
            ctaButton2BackgroundColor = ctaButton2BackgroundColor,
            ctaButton2PressedBackgroundColor = ctaButton2PressedBackgroundColor,
            ctaButton2StrokeColor = ctaButton2StrokeColor,
            ctaButton2StrokeWidth = ctaButton2StrokeWidth,
        )
    }

    fun createStoryData(storyProperties: StoryProperties?): StoryData? {
        storyProperties ?: return null

        val previewProperties = storyProperties.previewProperties ?: return null
        if (previewProperties.titleText.isNullOrBlank() && previewProperties.image.isNullOrBlank()) return null

        val previewStoryData = PreviewStoryData(
            titleText = previewProperties.titleText,
            titleTextColor = previewProperties.titleTextColor ?: return null,
            titleTextSize = previewProperties.titleTextSize ?: return null,
            image = previewProperties.image,
            imageScaleType = previewProperties.imageScaleType?.let { ImageScaleType.fromString(it) }
                ?: ImageScaleType.FILL,
            imageBorderColor = previewProperties.imageBorderColor,
            imageBorderWidth = previewProperties.imageBorderWidth ?: 0,
        )

        val fullscreenProperties = storyProperties.fullscreenProperties ?: return null

        val fullscreenStoryData = FullscreenStoryData(
            backgroundColor = fullscreenProperties.backgroundColor ?: return null,
            closeButtonColor = fullscreenProperties.closeButtonColor ?: return null,
            timelineBackgroundColor = fullscreenProperties.timelineBackgroundColor ?: return null,
            timelineProgressColor = fullscreenProperties.timelineProgressColor ?: return null,
            buttonText = fullscreenProperties.buttonText,
            buttonTextSizeSp = fullscreenProperties.buttonTextSize ?: return null,
            buttonTextColorString = fullscreenProperties.buttonTextColor ?: return null,
            pressedButtonTextColorString = fullscreenProperties.pressedButtonTextColor
                ?: return null,
            buttonBackgroundColorString = fullscreenProperties.buttonBackgroundColor ?: return null,
            pressedButtonBackgroundColorString = fullscreenProperties.pressedButtonBackgroundColor
                ?: return null,
            buttonStrokeColorString = fullscreenProperties.buttonStrokeColor ?: return null,
            buttonStrokeWidth = fullscreenProperties.buttonStrokeWidth ?: 0f,
            slides = listOfNotNull(
                createSlideData(fullscreenProperties.slides?.slide1),
                createSlideData(fullscreenProperties.slides?.slide2),
                createSlideData(fullscreenProperties.slides?.slide3),
                createSlideData(fullscreenProperties.slides?.slide4),
                createSlideData(fullscreenProperties.slides?.slide5),
                createSlideData(fullscreenProperties.slides?.slide6),
                createSlideData(fullscreenProperties.slides?.slide7),
                createSlideData(fullscreenProperties.slides?.slide8),
                createSlideData(fullscreenProperties.slides?.slide9),
                createSlideData(fullscreenProperties.slides?.slide10),
            ),
        )

        if (fullscreenStoryData.slides.isEmpty()) return null

        return StoryData(
            previewData = previewStoryData,
            fullscreenData = fullscreenStoryData
        )
    }

    fun createSlideData(slideProperties: SlideProperties?): SlideData? {
        slideProperties ?: return null

        if (slideProperties.backgroundImage.isNullOrBlank() &&
            slideProperties.titleText.isNullOrBlank() &&
            slideProperties.subtitleText.isNullOrBlank()
        ) return null

        return SlideData(
            backgroundImage = slideProperties.backgroundImage,
            backgroundImageScaleType = ImageScaleType.fromString(slideProperties.backgroundImageScaleType)
                ?: ImageScaleType.FILL,
            contentOffset = slideProperties.contentOffset ?: 0,
            logoImage = slideProperties.logoImage,
            logoImageScaleType = ImageScaleType.fromString(slideProperties.logoImageScaleType)
                ?: ImageScaleType.FILL,
            titleText = slideProperties.titleText,
            titleTextColor = slideProperties.titleTextColor ?: return null,
            titleTextSize = slideProperties.titleTextSize ?: return null,
            titleTextBackgroundColor = slideProperties.titleTextBackgroundColor,
            subtitleText = slideProperties.subtitleText,
            subtitleTextColor = slideProperties.subtitleTextColor ?: return null,
            subtitleTextSize = slideProperties.subtitleTextSize ?: return null,
            subtitleTextBackgroundColor = slideProperties.subtitleTextBackgroundColor,
            durationMillis = slideProperties.durationMillis ?: return null,
            overlayColor = slideProperties.overlayColor,
        )
    }
}