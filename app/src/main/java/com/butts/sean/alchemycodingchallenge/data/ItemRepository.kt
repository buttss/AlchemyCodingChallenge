package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemRepository {
    fun getItem(id: Long): Single<Item>
    fun fetchAllItems(): Single<List<Item>>
    fun getAllItems(): Single<List<Item>>
}