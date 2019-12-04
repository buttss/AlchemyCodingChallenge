package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class ItemRepositoryImpl(private val remoteItemDataSource: ItemDataSource,
                         private val localItemDataSource: ItemDataSource): ItemRepository {
    override fun getItem(id: Long): Single<Item> {
        val getStory = listOf(localItemDataSource.getStory(id),
                                remoteItemDataSource.getStory(id))
        return Single.concatEager(getStory)
                    .firstOrError()
    }

    override fun fetchAllItems(): Single<List<Item>> {
        return remoteItemDataSource.getAllStories()
                                    .flatMap {
                                        localItemDataSource.saveStories(it)
                                    }
                                    .subscribeOn(Schedulers.io())
    }

    override fun getAllItems(): Single<List<Item>> {
        val getAllStories = listOf(localItemDataSource.getAllStories(), fetchAllItems())
        return Single.concatEager(getAllStories)
                    .singleOrError()
                    .subscribeOn(Schedulers.io())
    }

    private fun getItems(ids: List<Long>): Observable<List<Item>> {
        return Observable.fromIterable(ids)
            .concatMap {
                getItem(it)
                    .toObservable()
            }
            .toList()
            .toObservable()
    }

}