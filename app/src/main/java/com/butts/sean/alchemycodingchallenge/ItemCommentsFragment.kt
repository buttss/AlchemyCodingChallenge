package com.butts.sean.alchemycodingchallenge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemCommentsViewModel
import com.butts.sean.alchemycodingchallenge.viewmodel.ItemDetailViewModel
import kotlinx.android.synthetic.main.fragment_item_comments.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemCommentsFragment: Fragment() {
    interface Listener {
        fun onCommentsFetchError()
        fun onOpenedComments(item: Item)
    }

    companion object {
        private const val INVALID_ID = -1L
        private const val ID = "id"

        fun create(id: Long): ItemCommentsFragment {
            val args = Bundle().apply { putLong(ID, id) }
            val fragment = ItemCommentsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var listener: Listener? = null
    private val viewModel: ItemCommentsViewModel by viewModel()
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

    private lateinit var commentsWebView: WebView
    private lateinit var progressContainer: FrameLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_comments, container, false)
        commentsWebView = view.commentsWebview
        commentsWebView.settings.javaScriptEnabled = true

        progressContainer = view.progressContainer
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.item.observe(viewLifecycleOwner, Observer {
            listener?.onOpenedComments(it)
            viewModel.loadComments(it)
        })

        viewModel.html.observe(viewLifecycleOwner, Observer {
            progressContainer.visibility = View.GONE
            commentsWebView.loadData(it, "text/html", "UTF-8")
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            listener?.onCommentsFetchError()
        })

        if (id == INVALID_ID) {
            listener?.onCommentsFetchError()
        } else {
            viewModel.loadItem(id)
        }
    }
}