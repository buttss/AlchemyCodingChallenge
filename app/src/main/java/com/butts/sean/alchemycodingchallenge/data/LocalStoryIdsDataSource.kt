package com.butts.sean.alchemycodingchallenge.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import java.io.*
import java.lang.StringBuilder

class LocalStoryIdsDataSource(private val saveFile: File): StoryIdsDataSource {
    override fun fetchStoryIds(): Single<List<Long>> {
        return Single.just(readFromFile())
                        .subscribeOn(Schedulers.io())
    }

    override fun getStoryIds(): Single<List<Long>> {
        return Single.just(readFromFile())
                    .subscribeOn(Schedulers.io())
    }

    override fun saveStoryIds(storyIds: List<Long>): Single<List<Long>> {
        return Single.just(writeToFile(storyIds))
                    .subscribeOn(Schedulers.io())
    }

    private fun writeToFile(storyIds: List<Long>): List<Long> {
        val idsJsonArray = JSONArray().apply { storyIds.forEach { this.put(it) } }
        val jsonString = idsJsonArray.toString()
        saveFile.writeText(jsonString)

        return storyIds
    }

    private fun readFromFile(): List<Long> {
        val ids = mutableListOf<Long>()
        val fileString = saveFile.readText()
        val jsonArray = JSONArray(fileString)
        for (i in 0..jsonArray.length()) {
            ids.add(jsonArray.getLong(i))
        }
        return ids
    }
}