package com.butts.sean.alchemycodingchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.data.ItemRepository
import com.butts.sean.alchemycodingchallenge.di.viewModelModule
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class ItemCommentsViewModel(private val itemRepository: ItemRepository): ViewModel() {
    val item = MutableLiveData<Item>()
    val html = MutableLiveData<String>()
    val error = MutableLiveData<Any>()

    private val compositeDisposable = CompositeDisposable()
    private val lightBlueColors = listOf("#B7E9F7", "#DBF3FA", "#D8E2FA", "#92DFF3")

    fun loadItem(id: Long) {
        val disposable = itemRepository.getItem(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                item.value = it
            }, Consumer {
                error.value = it
            })
        compositeDisposable.add(disposable)
    }

    fun loadComments(item: Item) {
        val disposable = Single.just(item)
        .map {
            itemRepository.populateComments(it)
            it
        }
        .map {
            val commentList = mutableListOf<Item>()
            createCommentList(commentList, it)
            // remove the post item, only comments are needed
            commentList.remove(it)
            commentList
        }
        .map {items ->
            val htmlBuilder = StringBuilder("<!DOCTYPE html><html><head></head>")
            htmlBuilder.append("<body style=\"background-color:#F5FCFF;max-width: 100%\">")
            for (item in items) {
                // since indents start at 1, I want the margin to be 0 for top level comments
                val indentMultiplier = item.indent - 1
                val color = lightBlueColors[indentMultiplier % lightBlueColors.size]
                val margin = 10 * indentMultiplier
                val div = """
                    <div class="comment" style="margin-left:${margin}px;background-color:${color};overflow:auto">
                      <h5>${postedByString(item)}</h5>
                      <p>${item.text}</p>
                    </div>
                """.trimIndent()
                htmlBuilder.append(div)
            }
            htmlBuilder.append("</body>")
            htmlBuilder.append("</html>")
            htmlBuilder.toString()
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(Consumer {
            html.value = it
        }, Consumer {
            error.value = it
        })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    private fun postedByString(item: Item): String {
        val pattern = "yyyy-MM-dd hh:mm a"
        val formatter = SimpleDateFormat(pattern)
        val timeInMillis = item.time * 1000
        val timeString = formatter.format(Date(timeInMillis))
        return "Posted by ${item.by} at $timeString"
    }

    private fun createCommentList(itemList: MutableList<Item>, item: Item): List<Item> {
        itemList.add(item)
        if (item.hasComments()) {
            for (comment in item.comments) {
                comment.indent = item.indent + 1
                createCommentList(itemList, comment)
            }
        }

        return itemList
    }
}