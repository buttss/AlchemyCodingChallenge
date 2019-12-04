package com.butts.sean.alchemycodingchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.butts.sean.alchemycodingchallenge.data.Item
import com.butts.sean.alchemycodingchallenge.data.ItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class ItemDetailViewModel(private val itemRepository: ItemRepository): ViewModel() {
    val item = MutableLiveData<Item>()
    val error = MutableLiveData<Any>()

    fun loadItem(id: Long) {
        itemRepository.getItem(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                item.value = it
            }, Consumer {
                error.value = it
            })
    }
}