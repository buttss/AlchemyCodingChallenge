package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemService {
    fun getItem(id: Long): Single<Item>
    fun getAllItems(ids: List<Long>): Single<List<Item>>
}