package com.rsschool.quiz

import java.io.Serializable

data class User(val email: String,
                val resultMessage: String,
                val resultQuiz: String
) : Serializable