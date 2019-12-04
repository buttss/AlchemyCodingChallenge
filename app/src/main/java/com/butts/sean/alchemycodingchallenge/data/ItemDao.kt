package com.butts.sean.alchemycodingchallenge.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ItemDao {
    @Query("SELECT * FROM story")
    fun getAllStories(): Observable<List<Item>>

    @Query("SELECT * FROM story WHERE id = :id")
    fun getStory(id: Long): Observable<Item>

    @Query("SELECT * FROM story WHERE id = :id")
    fun getStorySync(id: Long): Item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStories(items: List<Item>): Single<List<Long>>

    @Query("DELETE FROM story")
    fun clearTable()
}
