package com.rsschool.quiz.data.providers

import IQuizProvider
import android.content.Context
import com.rsschool.quiz.data.Question

class QuizProvider(private val context: Context) : IQuizProvider {

    override suspend fun loadQuestions(): List<Question> =
        com.rsschool.quiz.data.loadQuestions(context)

}