package com.rsschool.quiz.data

data class Question (
    val id: Int,
    val question: String,
    val answers: ArrayList<String>,
    val rightAnswer: String,
    var userChoice: String
)