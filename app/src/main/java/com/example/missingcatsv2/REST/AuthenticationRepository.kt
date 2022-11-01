package com.example.missingcatsv2.REST

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthenticationRepository() {
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val errorMessageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userMutableLiveData.postValue(auth.currentUser)
            } else {
                errorMessageLiveData.postValue(task.exception?.message)
            }
        }
    }

    fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userMutableLiveData.postValue(auth.currentUser)
            } else {
                errorMessageLiveData.postValue(task.exception?.message)
            }
        }
    }

    fun signOut() {
        auth.signOut()
        userMutableLiveData.postValue(auth.currentUser)
    }



}