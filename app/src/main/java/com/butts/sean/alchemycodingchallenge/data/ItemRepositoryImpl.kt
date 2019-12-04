package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class ItemRepositoryImpl(private val remoteItemDataSource: ItemDataSource,
                         private val localItemDataSource: ItemDataSource): ItemRepository {
    override fun getItem(id: Long): Single<Item> {
        val getStory = listOf(localItemDataSource.getItem(id),
                                remoteItemDataSource.getItem(id))
        return Single.concatEager(getStory)
                    .firstOrError()
    }

    override fun fetchAllItems(): Single<List<Item>> {
        return remoteItemDataSource.getAllItems()
                                    .flatMap {
                                        localItemDataSource.saveItems(it)
                                    }
                                    .subscribeOn(Schedulers.io())
    }

    override fun getAllItems(): Single<List<Item>> {
        val getAllStories = listOf(localItemDataSource.getAllItems(), fetchAllItems())
        return Single.concatEager(getAllStories)
                    .singleOrError()
                    .subscribeOn(Schedulers.io())
    }

    override fun populateComments(parentItem: Item) {
        if (parentItem.hasKids()) {
            val comments = mutableListOf<Item>()
            for (id in parentItem.kids) {
                val child: Item? = remoteItemDataSource.getItemSync(id)
                child?.let {
                    comments.add(it)
                    populateComments(it)
                }
            }

            parentItem.comments = comments
        }
    }
}