package com.butts.sean.alchemycodingchallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.butts.sean.alchemycodingchallenge.R
import com.butts.sean.alchemycodingchallenge.data.Story
import com.butts.sean.alchemycodingchallenge.views.StoryViewHolder

class StoryListAdapter: ListAdapter<Story, StoryViewHolder>(StoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class StoryDiffCallback: DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

    }
}