package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemRepository {
    fun getStory(id: Long): Single<Item>
    fun fetchAllStories(): Single<List<Item>>
    fun getAllStories(): Single<List<Item>>
}