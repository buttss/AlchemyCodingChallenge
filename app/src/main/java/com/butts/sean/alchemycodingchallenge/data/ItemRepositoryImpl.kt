package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class ItemRepositoryImpl(private val remoteItemDataSource: ItemDataSource,
                         private val localItemDataSource: ItemDataSource): ItemRepository {
    override fun getStory(id: Long): Single<Item> {
        val getStory = listOf(localItemDataSource.getStory(id), remoteItemDataSource.getStory(id))
        return Single.concatEager(getStory)
                        .firstOrError()
    }

    override fun fetchAllStories(): Single<List<Item>> {
        return remoteItemDataSource.getAllStories()
                                    .flatMap {
                                        localItemDataSource.saveStories(it)
                                    }
    }

    override fun getAllStories(): Single<List<Item>> {
        val getAllStories = listOf(localItemDataSource.getAllStories(), fetchAllStories())
        return Single.concatEager(getAllStories)
                    .singleOrError()
    }

}