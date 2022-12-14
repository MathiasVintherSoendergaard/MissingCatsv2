package com.example.missingcatsv2.Models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.missingcatsv2.REST.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class AuthenticationViewModel(): ViewModel() {
    // Instantiation of AuthenticationRepository
    private val authenticationRepository: AuthenticationRepository = AuthenticationRepository()
    // Observable firebaseUser, wrapped in MutableLiveData. Used by fragments to check if a user is logged in
    val userMutableLiveData: MutableLiveData<FirebaseUser> = authenticationRepository.userMutableLiveData

    fun register(email: String, password: String) {
        authenticationRepository.register(email, password)
    }

    fun logIn(email: String, password: String) {
        authenticationRepository.logIn(email, password)
    }

    fun signOut(){
        authenticationRepository.signOut()
    }
}