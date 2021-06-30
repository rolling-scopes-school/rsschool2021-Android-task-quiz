package com.rsschool.quiz

import java.io.Serializable

data class QuizObject(val question: String,
                      val answers: List<String>,
                      val numberQuestion: Int,
                      val numberCorrectAnswer: Int
) : Serializable