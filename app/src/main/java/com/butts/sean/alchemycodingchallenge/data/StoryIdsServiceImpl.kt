package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.Url

class StoryIdsServiceImpl(private val url: String,
                          retrofit: Retrofit): StoryIdsService {
    private val service = retrofit.create(RetrofitStoryIdService::class.java)

    override fun getStoryIds(): Single<List<Long>> {
        return service.getStoryIds(url)
                        .subscribeOn(Schedulers.io())
    }
}

interface RetrofitStoryIdService {
    fun getStoryIds(@Url url: String): Single<List<Long>>
}