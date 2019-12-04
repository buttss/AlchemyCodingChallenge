package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemDataSource {
    fun getStory(id: Long): Single<Item>
    fun getAllStories(): Single<List<Item>>
    fun saveStories(items: List<Item>): Single<List<Item>>
}