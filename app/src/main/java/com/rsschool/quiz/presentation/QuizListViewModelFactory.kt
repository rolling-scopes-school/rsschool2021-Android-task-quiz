package com.rsschool.quiz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsschool.quiz.data.providers.QuizLoader

class QuizListViewModelFactory(
    private val questionListLoader: QuizLoader
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizListViewModel(questionListLoader) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
