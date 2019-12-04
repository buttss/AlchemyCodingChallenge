package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class RemoteItemDataSource(private val itemIdsService: ItemIdsService,
                           private val itemService: ItemService): ItemDataSource {
    override fun getItem(id: Long): Single<Item> {
        return itemService.getItem(id)
    }

    override fun getAllItems(): Single<List<Item>> {
        return itemIdsService.getStoryIds()
                              .flatMap {
                                  itemService.getAllItems(it)
                              }
    }

    override fun saveItems(items: List<Item>): Single<List<Item>> {
        return Single.just(items)
    }

    override fun getItemSync(id: Long): Item? {
        return itemService.getItemSync(id).body()
    }

}