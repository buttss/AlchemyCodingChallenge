package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class StoryServiceImpl(retrofit: Retrofit): StoryService {
    private val service = retrofit.create(RetrofitStoryService::class.java)
    override fun getStory(id: Long): Single<Story> {
        return service.getStory(id)
                        .observeOn(Schedulers.io())
    }

    override fun getAllStories(): Single<List<Story>> {
        return service.getAllStories()
                        .observeOn(Schedulers.io())
    }
}

private interface RetrofitStoryService {
    fun getStory(id: Long): Single<Story>
    fun getAllStories(): Single<List<Story>>
}