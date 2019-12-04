package com.butts.sean.alchemycodingchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.butts.sean.alchemycodingchallenge.R
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.views.ItemViewHolder

class ItemListAdapter: ListAdapter<Item, ItemViewHolder>(StoryDiffCallback()) {
    interface Listener {
        fun onStoryClicked(item: Item, itemViewHolder: ItemViewHolder)
    }

    private val storyViewHolderOnClick = object: ItemViewHolder.ItemViewHolderOnClickListener {
        override fun onStoryViewHolderClicked(
            itemViewHolder: ItemViewHolder,
            adapterPosition: Int
        ) {
            val story = getItem(adapterPosition)
            listener?.onStoryClicked(story, itemViewHolder)
        }
    }

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item, parent, false)
        return ItemViewHolder(view, storyViewHolderOnClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class StoryDiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

    }
}