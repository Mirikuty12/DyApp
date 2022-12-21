package com.dynamicyield.templates.ui.base.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Implementation of the Delegate Adapter pattern for RecyclerView
 */
class DelegateAdapter<I : Any>(
    diffUtilCallback: DiffUtil.ItemCallback<I>, vararg itemDelegate: ItemDelegate<out I, *>
) : ListAdapter<I, RecyclerView.ViewHolder>(diffUtilCallback) {

    private val delegates: List<ItemDelegate<I, RecyclerView.ViewHolder>>
    private val delegateIndexMap: Map<Class<*>, Int>

    constructor(vararg itemDelegate: ItemDelegate<out I, *>) : this(DefaultDiffCallback<I>(), *itemDelegate)

    init {
        val map = mutableMapOf<Class<*>, Int>()
        itemDelegate.forEachIndexed { index, delegate ->
            if (map.put(delegate.itemType(), index) != null) {
                throw IllegalArgumentException("More than one delegate found for the same item type!")
            }
        }
        if (map.isEmpty()) throw IllegalArgumentException("No delegate found!")

        delegateIndexMap = map
        delegates = itemDelegate.map { d -> d as ItemDelegate<I, RecyclerView.ViewHolder> }
    }

    override fun getItemViewType(position: Int): Int {
        val type = currentList[position]::class.java
        return delegateIndexMap[type]
            ?: throw IllegalStateException("No delegate found for item with type: $type")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].bindViewHolder(position, currentList[position], holder)
    }

//    override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        super.onBindViewHolder(holder, position, payloads)
//    }

    class DefaultDiffCallback<I : Any> : DiffUtil.ItemCallback<I>() {
        override fun areItemsTheSame(oldItem: I, newItem: I): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: I, newItem: I): Boolean = false
    }
}