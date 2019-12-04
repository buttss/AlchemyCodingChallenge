package com.butts.sean.alchemycodingchallenge.views

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.butts.sean.alchemycodingchallenge.data.Item
import kotlinx.android.synthetic.main.view_holder_story.view.*
import java.text.SimpleDateFormat
import java.util.*

class ItemViewHolder(itemView: View, private val onClickListener: ItemViewHolderOnClickListener) : RecyclerView.ViewHolder(itemView) {
    interface ItemViewHolderOnClickListener {
        fun onStoryViewHolderClicked(itemViewHolder: ItemViewHolder, adapterPosition: Int)
    }

    private val titleTextView = itemView.titleTextView
    private val scoreTextView = itemView.scoreTextView
    private val postedByTextView = itemView.postedByTextView
    private val sourceTextView = itemView.sourceTextView
    private val innerOnClick = View.OnClickListener {
        onClickListener.onStoryViewHolderClicked(this, adapterPosition)
    }

    fun bind(item: Item) {
        itemView.setOnClickListener(innerOnClick)
        titleTextView.text = item.title
        scoreTextView.text = item.score.toString()
        postedByTextView.text = postedByString(item)
        sourceTextView.text = getSourceString(item)
    }

    private fun getSourceString(item: Item): String {
        val hostString = if (item.uri != null && item.uri != Uri.EMPTY) {
            "${item.uri.host} - "
        } else {
            ""
        }
        return "$hostString${item.type}"
    }

    private fun postedByString(item: Item): String {
        val pattern = "yyyy-MM-dd hh:mm a"
        val formatter = SimpleDateFormat(pattern)
        val timeInMillis = item.time * 1000
        val timeString = formatter.format(Date(timeInMillis))
        return "Posted by ${item.by} at $timeString"
    }
}