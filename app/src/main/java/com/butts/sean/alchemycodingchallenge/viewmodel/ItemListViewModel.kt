package com.butts.sean.alchemycodingchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.data.ItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class ItemListViewModel(private val itemRepository: ItemRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val storyList = MutableLiveData<List<Item>>()
    val error = MutableLiveData<Any>()

    fun refreshData() {
        val disposable = itemRepository
            .fetchAllItems()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                storyList.value = it
            }, Consumer {
                error.value = it
            })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}