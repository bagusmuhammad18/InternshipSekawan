package com.example.storyapp.util

import com.example.storyapp.api.ListStoryItem

object DataDummy {
    fun getDummyStory(): List<ListStoryItem> {
        return (0..100).map {
            ListStoryItem(
                "Photo $it",
                "Created at $it",
                "Name $it",
                "Desc $it",
                null,
                "$it",
                null,
            )
        }
    }

    fun getDummyToken(): String {
        return "Bearer token123"
    }
}