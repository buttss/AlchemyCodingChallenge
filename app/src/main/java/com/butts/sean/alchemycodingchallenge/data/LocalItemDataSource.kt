package com.butts.sean.alchemycodingchallenge.data

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalItemDataSource(private val itemDao: ItemDao): ItemDataSource {

    override fun getStory(id: Long): Single<Item> {
        return itemDao.getStory(id)
                        .firstOrError()
                        .subscribeOn(Schedulers.io())
    }

    override fun getAllStories(): Single<List<Item>> {
        return itemDao.getAllStories()
                        .singleOrError()
                        .subscribeOn(Schedulers.io())
    }

    override fun saveStories(items: List<Item>): Single<List<Item>> {
        return Single.fromCallable {
                        itemDao.clearTable()
                    }
                    .flatMap { itemDao.saveStories(items) }
                    .map {
                        Log.d("DebugDB", it.toString())
                        items
                    }
                    .subscribeOn(Schedulers.io())

    }
}