package com.rsschool.quiz.data

// only for this app, don't blame
data class Question (
    val id: Int,
    val question: String,
    val answers: ArrayList<String>,
    val rightAnswer: String,
    var userChoice: String
)