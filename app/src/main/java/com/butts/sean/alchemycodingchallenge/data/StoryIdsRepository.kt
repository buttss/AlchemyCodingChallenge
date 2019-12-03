package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class StoryIdsRepository(private val remoteDataSource: StoryIdsDataSource,
                         private val localDataSource: StoryIdsDataSource): StoryIdsDataSource {

    override fun fetchStoryIds(): Single<List<Long>> {
        return remoteDataSource.fetchStoryIds()
                                .flatMap {
                                    localDataSource.saveStoryIds(it)
                                }
    }

    override fun getStoryIds(): Single<List<Long>> {
        val gets = listOf(localDataSource.getStoryIds(),
                                                remoteDataSource.getStoryIds())
        return Single.concatEager(gets)
                        .filter { it.isNotEmpty() }
                        .firstOrError()
    }

    override fun saveStoryIds(storyIds: List<Long>): Single<List<Long>> {
        return localDataSource.saveStoryIds(storyIds)
    }

}