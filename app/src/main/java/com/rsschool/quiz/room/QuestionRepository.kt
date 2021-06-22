package com.rsschool.quiz.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDAO: QuestionDAO) {

    val allQuestions: Flow<List<Question>> = questionDAO.getAllQuestion()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert( question: Question) {
        questionDAO.insert( question)
    }
}