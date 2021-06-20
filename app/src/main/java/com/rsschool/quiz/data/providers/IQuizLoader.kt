package com.rsschool.quiz.data.providers

import com.rsschool.quiz.data.Question

interface IQuizLoader {
    suspend fun getQuesitons(): List<Question>
}