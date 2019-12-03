package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface StoryIdsDataSource {
    fun fetchStoryIds(): Single<List<Long>>
    fun getStoryIds(): Single<List<Long>>
    fun saveStoryIds(storyIds: List<Long>): Single<List<Long>>
}