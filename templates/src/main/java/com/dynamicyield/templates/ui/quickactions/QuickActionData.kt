package com.dynamicyield.templates.ui.quickactions

import com.dynamicyield.templates.ui.base.data.ImageScaleType

/**
 * Common interface that represents a Quick Action item
 * in the QuickActionsView and QuickActionsSliderView
 */
interface IQuickActionData {
    val title: String
    val titleColor: String
    val titleSize: Int
    val image: String?
    val imageScaleType: ImageScaleType
    val backgroundColor: String?
    val borderColor: String?
    val borderWidth: Int
    val cornerRadius: Int
    val pressedBackgroundColor: String?
    val pressedBorderColor: String?
    val pressedBorderWidth: Int
    val pressedCornerRadius: Int
    val isFeatured: Boolean
}

/**
 * The data class that represents a Quick Action item
 * in the QuickActionsView and QuickActionsSliderView
 */
data class QuickActionData(
    override val title: String,
    override val titleColor: String,
    override val titleSize: Int,
    override val image: String?,
    override val imageScaleType: ImageScaleType,
    override val backgroundColor: String?,
    override val borderColor: String?,
    override val borderWidth: Int,
    override val cornerRadius: Int,
    override val pressedBackgroundColor: String?,
    override val pressedBorderColor: String?,
    override val pressedBorderWidth: Int,
    override val pressedCornerRadius: Int,
) : IQuickActionData {
    override val isFeatured: Boolean = false
}

/**
 * The data class that represents a Featured Quick Action item
 * in the QuickActionsView and QuickActionsSliderView
 */
data class FeaturedQuickActionData(
    override val title: String,
    override val titleColor: String,
    override val titleSize: Int,
    override val image: String?,
    override val imageScaleType: ImageScaleType,
    override val backgroundColor: String?,
    override val borderColor: String?,
    override val borderWidth: Int,
    override val cornerRadius: Int,
    override val pressedBackgroundColor: String?,
    override val pressedBorderColor: String?,
    override val pressedBorderWidth: Int,
    override val pressedCornerRadius: Int,
) : IQuickActionData {
    override val isFeatured: Boolean = true
}
