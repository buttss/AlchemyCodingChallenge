package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemService {
    fun getStory(id: Long): Single<Item>
    fun getAllStories(ids: List<Long>): Single<List<Item>>
}