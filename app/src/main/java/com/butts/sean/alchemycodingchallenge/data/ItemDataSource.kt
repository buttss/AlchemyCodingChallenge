package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemDataSource {
    fun getItem(id: Long): Single<Item>
    fun getAllItems(): Single<List<Item>>
    fun saveItems(items: List<Item>): Single<List<Item>>
    fun getItemSync(id: Long): Item?
}