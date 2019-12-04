package com.butts.sean.alchemycodingchallenge.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

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
    @Ignore
    val uri: Uri? = Uri.parse(url)

    @Ignore
    var indent = 0

    @Ignore
    var comments = mutableListOf<Item>()

    fun hasComments() = comments.isNotEmpty()

    fun hasKids() = kids.isNotEmpty()

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

    companion object {
        val EMPTY = Item(-1L, "", listOf(), "", -1, -1L, -1L, "", "", "")
    }
}
