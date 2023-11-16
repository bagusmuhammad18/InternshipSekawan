package com.example.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.repository.StoryRepository

class StoryViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory(token).cachedIn(viewModelScope)
//
//    // Deklarasikan variabel yang ingin Anda simpan di sini
//    private var stories: List<MainModel> = emptyList()
//
//    fun setStories(stories: List<MainModel>) {
//        this.stories = stories
//    }
//
//    fun getStories(): List<MainModel> {
//        return stories
//    }
}
