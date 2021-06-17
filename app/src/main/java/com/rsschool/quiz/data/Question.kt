package com.rsschool.quiz.data

import kotlinx.serialization.SerialName

class Question(
    val id: Int,
    val text: String,
    val choose: List<String>,
    val answer: Int,
    var selection: Int,
)