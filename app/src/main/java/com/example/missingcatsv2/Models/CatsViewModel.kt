package com.example.missingcatsv2.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.missingcatsv2.REST.CatsRepository

class CatsViewModel : ViewModel() {
    private val repository = CatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData

    init {
        getCats()
    }

    // get all cats
    fun getCats() {
        repository.getCats()
    }

    // get specific cat from cat's index
    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }

    fun add(cat: Cat) {
        repository.addCat(cat)
    }

    fun delete(id: Int) {
        repository.deleteCat(id)
    }

    fun sortByReward() {
        repository.sortByReward()
    }

    fun sortByRewardDescending() {
        repository.sortByRewardDescending()
    }

    fun sortByDate() {
        repository.sortByDate()
    }

    fun sortByDateDescending() {
        repository.sortByDateDescending()
    }

    // filtering methods
    fun filterByPlace(place: String) {
        repository.filterByPlace(place)
    }

    fun filterByReward(reward: Int) {
        repository.filterByReward(reward)
    }
}