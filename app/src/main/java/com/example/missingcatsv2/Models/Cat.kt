package com.example.missingcatsv2.Models

import java.io.Serializable
import java.sql.Timestamp
import java.text.SimpleDateFormat



data class Cat(
    val id: Int, val name: String, val description: String, val place: String,
    val reward: Int, val userId: String, val date: Long, val pictureUrl: String) : Serializable {

    // short toString for RecyclerView
    override fun toString(): String {
        return "This is $name. It comes from $place. It has been missing since ${getDate()}."
    }
    // long toString for single cat presentation
    fun toLongString(): String {
        return "This cat is number $id, is called $name, lives at $place and belongs to user $userId. " +
                "It is described as follows: $description, and has been missing since ${getDate()}." +
                "The reward for returning it is $reward."
    }
    // method for converting Unix timestamp to dd/m/yyyy data format
    private fun getDate(): String? {
        val timeStamp = Timestamp(this.date*1000)
        val simpleDate = SimpleDateFormat("dd/M/yyyy")
        val currentDate = simpleDate.format(timeStamp)
        return currentDate
    }
}