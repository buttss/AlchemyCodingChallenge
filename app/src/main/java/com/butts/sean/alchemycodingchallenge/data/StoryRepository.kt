package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface StoryRepository {
    fun getStory(id: Long): Single<Story>
    fun fetchAllStories(): Single<List<Story>>
    fun getAllStories(): Single<List<Story>>
}