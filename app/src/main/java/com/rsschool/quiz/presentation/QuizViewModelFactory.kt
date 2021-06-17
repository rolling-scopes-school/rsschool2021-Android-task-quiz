package com.rsschool.quiz.presentation

import QuizLoader
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizViewModelFactory (
    private val questionListLoader: QuizLoader
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(questionListLoader) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}