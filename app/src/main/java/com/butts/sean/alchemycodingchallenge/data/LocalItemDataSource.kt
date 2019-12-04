package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single

class LocalItemDataSource(private val itemDao: ItemDao): ItemDataSource {

    override fun getStory(id: Long): Single<Item> {
        return itemDao.getStory(id)
                        .singleOrError()
    }

    override fun getAllStories(): Single<List<Item>> {
        return itemDao.getAllStories()
                        .singleOrError()
    }

    override fun saveStories(items: List<Item>): Single<List<Item>> {
        return Single.fromCallable {
                        itemDao.clearTable()
                        itemDao.saveStories(items)
                    }
                    .map { items }

    }
}