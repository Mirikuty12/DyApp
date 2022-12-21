package com.dynamicyield.templates.ui.base.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Represents a delegate for a single element type in the Delegate Adapter pattern.
 */
interface ItemDelegate<I, in H : RecyclerView.ViewHolder> {
    fun itemType(): Class<out I>
    fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(position: Int, item: I, holder: H)
//    fun bindViewHolder(position: Int, item: I, holder: H, payloads: List<Any>) {}
}