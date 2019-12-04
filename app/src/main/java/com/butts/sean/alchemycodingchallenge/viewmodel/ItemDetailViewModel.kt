package com.butts.sean.alchemycodingchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.data.ItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class ItemDetailViewModel(private val itemRepository: ItemRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val item = MutableLiveData<Item>()
    val error = MutableLiveData<Any>()

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}