package com.butts.sean.alchemycodingchallenge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemDetailViewModel
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemListViewModel
import kotlinx.android.synthetic.main.fragment_item_detail.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment: Fragment() {
    interface Listener {
        fun onStoryFetchError()
        fun onOpenedStory(item: Item)
        fun onClosedStory(item: Item)
    }

    companion object {
        private const val INVALID_ID = -1L
        private const val ID = "id"

        fun create(id: Long): ItemDetailFragment {
            val args = Bundle().apply { putLong(ID, id) }
            val fragment = ItemDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var listener: Listener? = null
    private val viewModel: ItemDetailViewModel by viewModel()
    private lateinit var contentWebView: WebView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    private var id: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getLong(ID) ?: -1L
    }

    private fun loadStory(item: Item) {
        listener?.onOpenedStory(item)

        val text = item.text
        contentWebView.loadData(text, "text/html", "UTF-8")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)
        contentWebView = view.contentWebview
        contentWebView.settings.javaScriptEnabled = true

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.item.observe(viewLifecycleOwner, Observer {
            loadStory(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            listener?.onStoryFetchError()
        })

        if (id == INVALID_ID) {
            listener?.onStoryFetchError()
        } else {
            viewModel.loadItem(id)
        }
    }


}
