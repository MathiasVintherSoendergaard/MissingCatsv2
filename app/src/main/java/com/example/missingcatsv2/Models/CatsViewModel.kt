package com.example.missingcatsv2.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.missingcatsv2.REST.CatsRepository

class CatsViewModel : ViewModel() {
    private val repository = CatsRepository()
    val catsLiveData: LiveData<List<Cat>> = repository.catsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getCats()
    }

    operator fun get(index: Int): Cat? {
        return catsLiveData.value?.get(index)
    }

    fun add(cat: Cat) {
        repository.addCat(cat)
    }

}