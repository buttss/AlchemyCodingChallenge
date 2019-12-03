package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class RemoteStoryIdsDataSource(private val storyIdsService: StoryIdsService): StoryIdsDataSource {
    override fun fetchStoryIds(): Single<List<Long>> {
        return storyIdsService.getStoryIds()
    }

    override fun getStoryIds(): Single<List<Long>> {
        return storyIdsService.getStoryIds()
    }

    override fun saveStoryIds(storyIds: List<Long>): Single<List<Long>> {
        // no save remote service, pass through
        return Single.just(storyIds)
    }
}