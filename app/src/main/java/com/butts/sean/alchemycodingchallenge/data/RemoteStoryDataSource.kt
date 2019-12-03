package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class RemoteStoryDataSource(private val storyService: StoryService): StoryDataSource {
    override fun getStory(id: Long): Single<Story> {
        return storyService.getStory(id)
    }

    override fun getAllStories(): Single<List<Story>> {
        return storyService.getAllStories()
    }

    override fun saveStories(stories: List<Story>): Single<List<Story>> {
        return Single.just(stories)
    }

}