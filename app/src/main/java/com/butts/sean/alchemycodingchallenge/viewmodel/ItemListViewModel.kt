package com.butts.sean.alchemycodingchallenge.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.data.ItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class ItemListViewModel(private val itemRepository: ItemRepository): ViewModel() {
    val storyList = MutableLiveData<List<Item>>()
    val error = MutableLiveData<Any>()

    fun refreshData() {
        itemRepository
            .fetchAllStories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                storyList.value = it
            }, Consumer {
                error.value = it
            })
    }
}