package com.example.missingcatsv2.Models

import java.io.Serializable

data class Cat(
    val id: Int, val name: String, val description: String, val place: String,
    val reward: Int, val userId: String, val date: Long, val pictureUrl: String) : Serializable {
    // man ved jo aldrig
    override fun toString(): String {
        return "This cat is number $id, is called $name, lives at $place and belongs to user $userId. " +
                "The reward for returning it is $reward."
    }
}