package com.butts.sean.alchemycodingchallenge.data

import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LocalItemDataSource(private val itemDao: ItemDao): ItemDataSource {

    override fun getItem(id: Long): Single<Item> {
        return itemDao.getStory(id)
                        .firstOrError()
                        .subscribeOn(Schedulers.io())
    }

    override fun getAllItems(): Single<List<Item>> {
        return itemDao.getAllStories()
                        .singleOrError()
                        .subscribeOn(Schedulers.io())
    }

    override fun getItemSync(id: Long): Item {
        return itemDao.getStorySync(id)
    }

    override fun saveItems(items: List<Item>): Single<List<Item>> {
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