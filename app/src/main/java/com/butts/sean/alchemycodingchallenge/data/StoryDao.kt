package com.butts.sean.alchemycodingchallenge.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface StoryDao {
    @Query("SELECT * FROM story")
    fun getAllStories(): Observable<List<Story>>

    @Query("SELECT * FROM story WHERE id = (:id)")
    fun getStory(id: Long): Observable<Story>

    @Insert
    fun saveStories(stories: List<Story>): Single<List<Long>>
}
