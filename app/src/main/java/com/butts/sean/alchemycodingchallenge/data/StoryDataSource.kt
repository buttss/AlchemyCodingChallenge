package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface StoryDataSource {
    fun getStory(id: Long): Single<Story>
    fun getAllStories(): Single<List<Story>>
    fun saveStories(stories: List<Story>): Single<List<Story>>
}