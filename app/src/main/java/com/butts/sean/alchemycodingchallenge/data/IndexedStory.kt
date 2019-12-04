package com.butts.sean.alchemycodingchallenge.data

data class IndexedStory(val index: Int,
                        val item: Item) {
    fun isEmpty() = item.isEmpty()
}