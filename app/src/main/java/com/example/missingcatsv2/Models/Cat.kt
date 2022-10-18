package com.example.missingcatsv2.Models

import java.io.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


data class Cat(
    val id: Int, val name: String, val description: String, val place: String,
    val reward: Int, val userId: String, val date: Long, val pictureUrl: String) : Serializable {
    // man ved jo aldrig
    override fun toString(): String {
        return "This is $name. It comes from $place. It has been missing since ${getDate()}."
    }
    fun toLongString(): String {
        return "This cat is number $id, is called $name, lives at $place and belongs to user $userId. " +
                "It is described as follows: $description, and has been missing since ${getDate()}." +
                "The reward for returning it is $reward."
    }
    private fun getDate(): String? {
        val timeStamp = Timestamp(this.date*1000)
        val simpleDate = SimpleDateFormat("dd/M/yyyy")
        val currentDate = simpleDate.format(timeStamp)
        return currentDate
    }
}