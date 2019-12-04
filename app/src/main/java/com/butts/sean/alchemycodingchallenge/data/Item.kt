package com.butts.sean.alchemycodingchallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/*
{
  "by" : "dhouston",
  "descendants" : 71,
  "id" : 8863,
  "kids" : [ 8952, 9224, 8917, 8884, 8887, 8943, 8869, 8958, 9005, 9671, 8940, 9067, 8908, 9055, 8865, 8881, 8872, 8873, 8955, 10403, 8903, 8928, 9125, 8998, 8901, 8902, 8907, 8894, 8878, 8870, 8980, 8934, 8876 ],
  "score" : 111,
  "time" : 1175714200,
  "title" : "My YC app: Dropbox - Throw away your USB drive",
  "type" : "story",
  "url" : "http://www.getdropbox.com/u/2/screencast.html"
}
 */

@Entity(tableName = "story")
@JsonClass(generateAdapter = true)
data class Item(@PrimaryKey
                 val id: Long = -1L,
                val text: String = "",
                val kids: List<Long> = listOf(),
                val by: String = "",
                val descendants: Int = 0,
                val score: Long = 0L,
                val time: Long = -1L,
                val title: String = "",
                val type: String = "",
                val url: String = "") {
    companion object {
        val EMPTY = Item(-1L, "", listOf(), "", -1, -1L, -1L, "", "", "")
    }

    fun hasText() = text.isNotEmpty()

    fun isEmpty(): Boolean {
        return id == -1L
                && text.isEmpty()
                && kids.isEmpty()
                && by.isEmpty()
                && descendants == -1
                && score == -1L
                && time == -1L
                && title.isEmpty()
                && type.isEmpty()
                && url.isEmpty()
    }

    override fun toString(): String {
        return """
            id: $id,
            kids: $kids,
            by: $by,
            descendants: $descendants,
            score: $score,
            time: $time,
            title: $title,
            type: $type,
            url: $url,
            text: $text
        """.trimIndent()
    }
}
