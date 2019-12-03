package com.butts.sean.alchemycodingchallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.butts.sean.alchemycodingchallenge.adapters.StoryListAdapter
import com.butts.sean.alchemycodingchallenge.data.Story
import com.butts.sean.alchemycodingchallenge.viewmodel.StoryListViewModel
import kotlinx.android.synthetic.main.fragment_story_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryListFragment: Fragment() {
    private val viewModel: StoryListViewModel by viewModel()
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val storyListAdapter = StoryListAdapter()

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        // refresh data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_story_list, container, false)
        storyRecyclerView = view.storyRecyclerView
        storyRecyclerView.adapter = storyListAdapter

        swipeRefreshLayout = view.storySwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener)

        return view
    }

    fun setStories(stories: List<Story>) {
        storyListAdapter.submitList(stories)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }


}