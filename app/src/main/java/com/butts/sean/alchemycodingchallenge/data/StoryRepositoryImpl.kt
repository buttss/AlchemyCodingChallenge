package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class StoryRepositoryImpl(private val remoteStoryDataSource: StoryDataSource,
                          private val localStoryDataSource: StoryDataSource): StoryRepository {
    override fun getStory(id: Long): Single<Story> {
        val getStory = listOf(localStoryDataSource.getStory(id), remoteStoryDataSource.getStory(id))
        return Single.concatEager(getStory)
                        .firstOrError()
    }

    override fun fetchAllStories(): Single<List<Story>> {
        return remoteStoryDataSource.getAllStories()
            .flatMap { localStoryDataSource.saveStories(it) }
    }

    override fun getAllStories(): Single<List<Story>> {
        return localStoryDataSource.getAllStories()
    }

}