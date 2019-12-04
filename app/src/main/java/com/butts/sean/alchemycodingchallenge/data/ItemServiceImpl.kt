package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class ItemServiceImpl(retrofit: Retrofit): ItemService {
    private val service = retrofit.create(RetrofitItemService::class.java)
    override fun getItemSync(id: Long): Response<Item> {
        return service.getItemInline(id).execute()
    }

    override fun getItem(id: Long): Single<Item> {
        return service.getItem(id)
                        .onErrorReturn {
                            it.printStackTrace()
                            Item.EMPTY
                        }
                        .subscribeOn(Schedulers.io())
    }

    override fun getAllItems(ids: List<Long>): Single<List<Item>> {
        return Observable.fromIterable(ids.mapIndexed { index, id -> Pair(index, id) })
                        // using flatMap + sorting was faster than concatMap
                        // concatMap preserves order, but waits for the previous observable to finish
                        .flatMap { pair ->
                            val index = pair.first
                            val id = pair.second
                            service.getItem(id)
                                .subscribeOn(Schedulers.io())
                                .map {
                                    IndexedStory(index, it)
                                }
                                .onErrorResumeNext {
                                    it.printStackTrace()
                                    Single.just(IndexedStory(index, Item.EMPTY))
                                }
                                .toObservable()
                        }
                        .filter {
                            !it.isEmpty()
                        }
                        .sorted { story1, story2 ->
                            story1.index - story2.index
                        }
                        .map {
                            it.item
                        }
                        .toList()
                        .subscribeOn(Schedulers.io())
    }
}

private interface RetrofitItemService {
    @GET("v0/item/{id}.json")
    fun getItem(@Path("id") id: Long): Single<Item>

    @GET("v0/item/{id}.json")
    fun getItemInline(@Path("id") id: Long): Call<Item>
}