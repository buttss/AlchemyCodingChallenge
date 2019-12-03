package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface StoryService {
    fun getStory(id: Long): Single<Story>
    fun getAllStories(): Single<List<Story>>
}