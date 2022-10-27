package com.example.missingcatsv2.REST

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.missingcatsv2.Models.Cat
import com.example.missingcatsv2.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationRepository() {
    val userMutableLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    init {

    }

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