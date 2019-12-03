package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface StoryIdsService {
    fun getStoryIds(): Single<List<Long>>
}