package com.butts.sean.alchemycodingchallenge

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.butts.sean.alchemycodingchallenge.adapters.ItemListAdapter
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemListViewModel
import com.butts.sean.alchemycodingchallenge.views.ItemViewHolder
import kotlinx.android.synthetic.main.fragment_story_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemListFragment: Fragment() {
    interface Listener {
        fun onStoryClicked(item: Item, itemViewHolder: ItemViewHolder)
    }

    private val viewModel: ItemListViewModel by viewModel()
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val storyListAdapter = ItemListAdapter()
    private lateinit var listener: Listener
    private var needsRefresh = true

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        // refresh data
        viewModel.refreshData()
    }

    private val storyListAdapterListener = object: ItemListAdapter.Listener {
        override fun onStoryClicked(item: Item, itemViewHolder: ItemViewHolder) {
            listener.onStoryClicked(item, itemViewHolder)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_story_list, container, false)
        storyRecyclerView = view.storyRecyclerView
        storyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        storyListAdapter.listener = storyListAdapterListener
        storyRecyclerView.adapter = storyListAdapter

        val context = view.context
        swipeRefreshLayout = view.storySwipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.vibrant),
                                                ContextCompat.getColor(context, R.color.darkVibrant))
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.storyList.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            storyListAdapter.submitList(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false

            // show error
        })
    }

    override fun onStart() {
        super.onStart()

        if (needsRefresh) refreshData()
    }

    override fun onPause() {
        super.onPause()
        needsRefresh = false
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.refreshData()
    }
}