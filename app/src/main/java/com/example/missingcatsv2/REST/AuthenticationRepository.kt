package com.example.missingcatsv2.REST

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationRepository() {
    // MutableLiveData with FireBaseUser, holds current user
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    // MutableLiveData for an error message
    private val errorMessageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    // Function for registering/signing up, which calls on auth-object
    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userMutableLiveData.postValue(auth.currentUser)
            } else {
                errorMessageLiveData.postValue(task.exception?.message)
            }
        }
    }
    // Function for logging in, which calls on auth-object
    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userMutableLiveData.postValue(auth.currentUser)
            } else {
                errorMessageLiveData.postValue(task.exception?.message)
            }
        }
    }
    // Function for signing/logging out, which calls on auth-object
    fun signOut() {
        auth.signOut()
        userMutableLiveData.postValue(auth.currentUser)
    }
}