package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class LocalStoryDataSource(private val storyDao: StoryDao): StoryDataSource {

    override fun getStory(id: Long): Single<Story> {
        return storyDao.getStory(id)
                        .singleOrError()
    }

    override fun getAllStories(): Single<List<Story>> {
        return storyDao.getAllStories()
                        .singleOrError()
    }

    override fun saveStories(stories: List<Story>): Single<List<Story>> {
        return storyDao.saveStories(stories)
                        .map { stories }
    }
}