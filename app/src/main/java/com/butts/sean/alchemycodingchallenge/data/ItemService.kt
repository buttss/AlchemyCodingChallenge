package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single
import retrofit2.Response

interface ItemService {
    fun getItemSync(id: Long): Response<Item>
    fun getItem(id: Long): Single<Item>
    fun getAllItems(ids: List<Long>): Single<List<Item>>
}