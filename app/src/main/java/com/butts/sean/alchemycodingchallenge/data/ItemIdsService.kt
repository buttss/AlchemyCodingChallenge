package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

interface ItemIdsService {
    fun getStoryIds(): Single<List<Long>>
}