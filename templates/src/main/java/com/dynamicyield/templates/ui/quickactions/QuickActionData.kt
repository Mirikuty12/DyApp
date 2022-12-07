package com.dynamicyield.templates.ui.quickactions

import android.view.View
import com.dynamicyield.templates.ui.base.data.ImageScaleType

interface IQuickActionData/* : DiffItem */{
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
    val clickListener: View.OnClickListener?

//    override fun isSameItem(newItem: DiffItem): Boolean {
//        if (this === newItem) return true
//        if (newItem::class != this::class) return false
//        if (newItem !is IQuickActionData) return false
//
//        return title == newItem.title && image == newItem.image
//    }
//
//    override fun isSameVisualContent(newItem: DiffItem): Boolean {
//        if (this === newItem) return true
//        if (newItem::class != this::class) return false
//        if (newItem !is IQuickActionData) return false
//
//        return title == newItem.title
//                && titleColor == newItem.titleColor
//                && titleSize == newItem.titleSize
//                && image == newItem.image
//                && imageScaleType == newItem.imageScaleType
//                && backgroundColor == newItem.backgroundColor
//                && borderColor == newItem.borderColor
//                && borderWidth == newItem.borderWidth
//                && cornerRadius == newItem.cornerRadius
//                && pressedBackgroundColor == newItem.pressedBackgroundColor
//                && pressedBorderColor == newItem.pressedBorderColor
//                && pressedBorderWidth == newItem.pressedBorderWidth
//                && pressedCornerRadius == newItem.pressedCornerRadius
//                && isFeatured == newItem.isFeatured
//                && clickListener == newItem.clickListener
//    }
}

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
    override val clickListener: View.OnClickListener?
) : IQuickActionData {
    override val isFeatured: Boolean = false
}

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
    override val clickListener: View.OnClickListener?
) : IQuickActionData {
    override val isFeatured: Boolean = true
}
