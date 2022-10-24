package com.example.missingcatsv2.Models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingcatsv2.REST.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel(): ViewModel() {
    private val authenticationRepository: AuthenticationRepository
    private val userMutableLiveData: MutableLiveData<FirebaseUser>
    init {
        authenticationRepository = AuthenticationRepository()
        userMutableLiveData = authenticationRepository.getUserMutableLiveData()
    }

    fun register(email: String, password: String) {
        authenticationRepository.register(email, password)
    }

    fun getUserMutableLiveData(): MutableLiveData<FirebaseUser> {
        return userMutableLiveData
    }

}