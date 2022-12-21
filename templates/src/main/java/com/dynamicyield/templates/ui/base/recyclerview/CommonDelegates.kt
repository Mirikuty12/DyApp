package com.dynamicyield.templates.ui.base.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Common delegates for different element types in the Delegate Adapter pattern.
 */

class LayoutItemDelegate<I>(
    private val type: Class<out I>,
    @LayoutRes private val layoutId: Int
) : ItemDelegate<I, RecyclerView.ViewHolder> {
    private var viewFactory: ((Context) -> View)? = null

    constructor(type: Class<out I>, viewFactory: (Context) -> View) : this(type, -1) {
        this.viewFactory = viewFactory
    }

    override fun itemType(): Class<out I> = type

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return viewFactory?.let {
            BaseViewHolder(it.invoke(parent.context))
        } ?: run {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            BaseViewHolder(view)
        }
    }

    override fun bindViewHolder(position: Int, item: I, holder: RecyclerView.ViewHolder) {}
}

class CreateItemDelegate<I>(
    private val origin: ItemDelegate<I, RecyclerView.ViewHolder>,
    private val createBlock: RecyclerView.ViewHolder.(parent: ViewGroup) -> Unit
) : ItemDelegate<I, RecyclerView.ViewHolder> by origin {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return origin.createViewHolder(parent).apply { createBlock(parent) }
    }
}

class BindItemDelegate<I>(
    private val origin: ItemDelegate<I, RecyclerView.ViewHolder>,
    private val bindBlock: RecyclerView.ViewHolder.(Int, I) -> Unit
) : ItemDelegate<I, RecyclerView.ViewHolder> by origin {

    override fun bindViewHolder(position: Int, item: I, holder: RecyclerView.ViewHolder) {
        origin.bindViewHolder(position, item, holder)
        holder.bindBlock(position, item)
    }
}

/**
 * Helper functions for creating delegates
 */

inline fun <reified I> itemDelegate(
    @LayoutRes layoutId: Int
): ItemDelegate<I, RecyclerView.ViewHolder> = LayoutItemDelegate(I::class.java, layoutId)

inline fun <reified I> itemDelegate(
    noinline viewFactory: (Context) -> View
): ItemDelegate<I, RecyclerView.ViewHolder> = LayoutItemDelegate(I::class.java, viewFactory)

fun <I> ItemDelegate<I, RecyclerView.ViewHolder>.create(
    block: RecyclerView.ViewHolder.(parent: ViewGroup) -> Unit
): ItemDelegate<I, RecyclerView.ViewHolder> = CreateItemDelegate(this, block)

fun <I> ItemDelegate<I, RecyclerView.ViewHolder>.bind(
    block: RecyclerView.ViewHolder.(Int, I) -> Unit
): ItemDelegate<I, RecyclerView.ViewHolder> = BindItemDelegate(this, block)