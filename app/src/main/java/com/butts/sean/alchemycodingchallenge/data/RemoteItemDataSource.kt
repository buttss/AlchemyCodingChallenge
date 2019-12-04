package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class RemoteItemDataSource(private val itemIdsService: ItemIdsService,
                           private val itemService: ItemService): ItemDataSource {
    override fun getStory(id: Long): Single<Item> {
        return itemService.getStory(id)
    }

    override fun getAllStories(): Single<List<Item>> {
        return itemIdsService.getStoryIds()
                              .flatMap {
                                  itemService.getAllStories(it)
                              }
    }

    override fun saveStories(items: List<Item>): Single<List<Item>> {
        return Single.just(items)
    }

}